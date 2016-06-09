package io.github.thecubemc.event;

import com.google.inject.Injector;
import io.github.thecubemc.account.AccountStub;

public final class CompleteLoginEvent{
  public final Injector injector;
  public final AccountStub from;
  public final AccountStub to;

  public CompleteLoginEvent(Injector injector, AccountStub from, AccountStub to) {
    this.injector = injector;
    this.from = from;
    this.to = to;
  }
}