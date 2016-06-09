package io.github.thecubemc.account;

import com.google.common.eventbus.EventBus;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import io.github.thecubemc.event.LoginEvent;

import java.util.HashMap;
import java.util.Map;

@Singleton
public final class AccountFactory
implements Provider<AccountStub> {
  private static final AccountStub defaultStub = new AccountStub(null, "Default");
  private final MojangAuthentication auth = new MojangAuthentication();

  private final Map<String, Account> accounts = new HashMap<>();
  private Account current;

  public AccountStub login(Injector injector, String name) {
    if (this.accounts.containsKey(name)) return this.accounts.get(name)
                                                             .getStub(null);

    System.out.println("Logging in");

    EventBus eventbus = injector.getInstance(EventBus.class);
    LoginEvent event = new LoginEvent(injector);
    eventbus.post(event);

    if (event.isCanceled()) {
      System.out.println("Canceled");
      this.current = null;
      return this.get();
    }

    System.out.println("Attempting Login: " + event.getUsername());
    System.out.println("Got authentication");
    Account acc = this.auth.login(new Account(event.getUsername(), event.getPassword(), true));
    System.out.println(">>: " + acc.getStub(null).minecraftUsername);
    this.accounts.put(name, acc);
    this.current = acc;
    return this.get();
  }

  @Override
  public AccountStub get() {
    return this.current == null
           ? defaultStub
           : this.current.getStub(null);
  }
}