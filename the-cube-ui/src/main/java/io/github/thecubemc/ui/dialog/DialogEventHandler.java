package io.github.thecubemc.ui.dialog;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github.thecubemc.event.ErrorEvent;
import io.github.thecubemc.event.LoginEvent;

import javax.swing.SwingUtilities;
import java.lang.reflect.InvocationTargetException;

@Singleton
public final class DialogEventHandler{
  @Inject private DialogEventHandler(){}

  @Subscribe
  public void onLoginEvent(LoginEvent e){
    try {
      System.out.println("Spawning LoginDialog");
      LoginDialog dialog = e.injector.getInstance(LoginDialog.class);
      SwingUtilities.invokeAndWait(()->{
        dialog.setEvent(e);
        dialog.setVisible(true);
      });
      System.out.println("Closed LoginDialog");
    } catch (InterruptedException | InvocationTargetException e1) {
      EventBus bus = e.injector.getInstance(EventBus.class);
      bus.post(new ErrorEvent(e.injector, e1.getMessage()));
      System.err.println(e1.getMessage());
    }
  }

  @Subscribe
  public void onErrorEvent(ErrorEvent e){
    System.out.println(e.message);
  }
}
