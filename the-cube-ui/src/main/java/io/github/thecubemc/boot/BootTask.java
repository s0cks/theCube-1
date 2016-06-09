package io.github.thecubemc.boot;

import io.github.thecubemc.TheCube;

import java.lang.reflect.Method;
import java.util.Arrays;

final class BootTask{
  public final String name;
  public final BootFunction task;
  public final BootTask[] parents;

  BootTask(String name, BootFunction task, BootTask[] parents){
    this.name = name;
    this.task = task;
    this.parents = parents;
  }

  @Override
  public String toString() {
    return this.name;
  }

  @Override
  public int hashCode() {
    return 127 * name.hashCode();
  }

  @Override
  public boolean equals(Object obj){
    if(!(obj instanceof BootTask)) return false;
    BootTask task = ((BootTask) obj);
    return task.name.equals(this.name) && Arrays.equals(task.parents, this.parents);
  }

  protected static BootTask from(Class<?> c){
    String[] dependencies;
    if(c.isAnnotationPresent(BootDependency.class)){
      dependencies = c.getAnnotation(BootDependency.class).value().split(":");
    } else{
      dependencies = new String[0];
    }

    BootTask[] dependencyTasks = new BootTask[dependencies.length];
    for(int i = 0; i < dependencyTasks.length; i++){
      dependencyTasks[i] = BootLoader.find(dependencies[i]);
    }

    return new BootTask(c.getSimpleName().substring(0, c.getSimpleName().length() - "Task".length()), () ->{
      Object instance = TheCube.injector.getInstance(c);
      Method method = c.getDeclaredMethod("doTask");
      method.setAccessible(true);
      return ((BootResult) method.invoke(instance));
    }, (dependencyTasks.length > 0) ? dependencyTasks : null);
  }
}
