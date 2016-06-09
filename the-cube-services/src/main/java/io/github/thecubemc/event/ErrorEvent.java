package io.github.thecubemc.event;

import com.google.inject.Injector;

public final class ErrorEvent{
  public final Injector injector;
  public final String message;

  public ErrorEvent(Injector injector, String message){
    this.injector = injector;
    this.message = message;
  }
}
