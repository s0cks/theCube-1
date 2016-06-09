package io.github.thecubemc;

import com.google.common.eventbus.EventBus;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import io.github.thecubemc.boot.BootLoader;
import io.github.thecubemc.fs.FileSystem;
import io.github.thecubemc.http.Network;
import io.github.thecubemc.ui.TheCubeFrame;
import io.github.thecubemc.ui.TheCubeSplashScreen;
import io.github.thecubemc.ui.dialog.DialogEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.SwingUtilities;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

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
    return TheCube.injector.getInstance(TheCube.class);
  }

  public void start()
  throws Exception{
    this.logger.info("Starting TheCube");
    SwingUtilities.invokeLater(()->{
      TheCube.injector.getInstance(TheCubeSplashScreen.class).setVisible(true);
    });
    BootLoader.runBootSequence(Runtime.getRuntime().availableProcessors() / 2);
    this.logger.info("Showing main frame");
    SwingUtilities.invokeLater(()->{
      TheCube.injector.getInstance(TheCubeFrame.class).setVisible(true);
    });
  }

  public void bindEventListeners(){
    this.logger.info("Binding Event Listeners");
    this.events.register(TheCube.injector.getInstance(DialogEventHandler.class));
  }

  public static void main(String... args)
  throws Exception{
    TheCube.instance().bindEventListeners();
    TheCube.instance().start();
  }
}