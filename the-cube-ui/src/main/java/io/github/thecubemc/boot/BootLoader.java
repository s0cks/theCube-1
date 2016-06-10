package io.github.thecubemc.boot;

import com.google.common.reflect.ClassPath;
import io.github.thecubemc.TheCube;
import io.github.thecubemc.ui.TheCubeSplashScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.SwingUtilities;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public final class BootLoader {
  private static final List<BootTask> registeredTasks = new LinkedList<>();
  private static final Set<String> ranTasks = new HashSet<>();
  private static final Logger logger = LoggerFactory.getLogger(BootLoader.class);

  static {
    try {
      Set<ClassPath.ClassInfo> sequences = ClassPath.from(ClassLoader.getSystemClassLoader())
                                                    .getTopLevelClasses("io.github.thecubemc.boot.sequences");
      sequences.forEach((info) ->{
        BootTask task = BootTask.from(info.load());
        if(!registeredTasks.contains(task)){
          registeredTasks.add(task);
        }
      });
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static void runBootSequence(int forks)
  throws Exception{
    ExecutorService executor = Executors.newFixedThreadPool(forks);
    do{
      BootTask task = findNextAvailableTask();
      if(task == null) break;
      executor.submit(() ->{
        try{
          logger.info("Executing Task: " + task.name);
          if(task.task.doBoot() == BootResult.FAIL){
            System.exit(-1);
          }
        } catch(Exception e){
          throw new RuntimeException(e);
        } finally{
          ranTasks.add(task.name);
        }
      });
    } while(!registeredTasks.isEmpty());
    executor.shutdown();
    executor.awaitTermination(30, TimeUnit.SECONDS);
    System.out.println("Done With Boot Sequence");
    SwingUtilities.invokeLater(()->{
      TheCube.injector.getInstance(TheCubeSplashScreen.class).dispose();
    });
  }

  private static BootTask findNextAvailableTask(){
    for(BootTask task : registeredTasks){
      if(task.parents == null){
        registeredTasks.remove(task);
        return task;
      } else if(task.parents.length > 0 && hasRanParents(task.parents)) {
        registeredTasks.remove(task);
        return task;
      }
    }

    return registeredTasks.remove(0);
  }

  private static boolean hasRanParents(BootTask[] parents){
    if(parents == null) return true;
    for(BootTask task : parents){
      if(task.parents == null && ranTasks.contains(task.name)){
        return true;
      } else if(hasRanParents(task.parents) && ranTasks.contains(task.name)){
        return true;
      }
    }

    return false;
  }

  public static BootTask find(String name) {
    for (BootTask task : registeredTasks) {
      if (task.name.equals(name)) {
        return task;
      }
    }

    try {
      Class<?> c = Class.forName("io.github.thecubemc.boot.sequences." + name + "Task");
      BootTask task = BootTask.from(c);
      registeredTasks.add(task);
      return task;
    } catch (ClassNotFoundException e) {
      throw new RuntimeException("Unknown boot sequence: " + name, e);
    }
  }
}