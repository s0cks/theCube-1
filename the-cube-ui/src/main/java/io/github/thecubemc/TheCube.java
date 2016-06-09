package io.github.thecubemc;

import com.google.common.eventbus.EventBus;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import io.github.thecubemc.fs.FileSystem;
import io.github.thecubemc.http.Network;
import io.github.thecubemc.ui.TheCubeSplashScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.SwingUtilities;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

@Singleton
public final class TheCube{
  public static final Injector injector = Guice.createInjector(new TheCubeModule());

  public final Network network;
  public final FileSystem fileSystem;
  public final Logger logger;
  public final EventBus events;
  public final ExecutorService executor;

  @Inject
  private TheCube(Network net, FileSystem fs, ThreadFactory threadFactory){
    this.network = net;
    this.fileSystem = fs;
    this.logger = LoggerFactory.getLogger(TheCube.class);
    this.events = new EventBus("TheCube-EventBus");
    this.executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() / 2, threadFactory);
  }

  public static TheCube instance(){
    return injector.getInstance(TheCube.class);
  }

  public void start()
  throws Exception{
    this.logger.info("Starting TheCube");
    SwingUtilities.invokeLater(() ->{
      TheCube.injector.getInstance(TheCubeSplashScreen.class).setVisible(true);
    });

    TheCubeSplashScreen splash = TheCube.injector.getInstance(TheCubeSplashScreen.class);
    while(true){
      Thread.sleep(TimeUnit.SECONDS.toMillis(1));
      splash.addProgress(10);
      if(splash.getProgress() == 100){
        break;
      }
    }
  }

  public static void main(String... args)
  throws Exception{
    TheCube.instance().start();
  }
}