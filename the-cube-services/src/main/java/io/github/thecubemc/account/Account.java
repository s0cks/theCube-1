package io.github.thecubemc.account;

import com.google.inject.Injector;

final class Account{
  private final boolean real;
  private final String password;
  private final String username;
  private String accessToken;
  private String uuid;
  private AccountStub stub;

  Account(String username, String password, boolean real){
    this.real = real;
    this.password = password;
    this.username = username;
  }

  public AccountStub getStub(Injector injector){
    if(this.stub == null){
      this.stub = new AccountStub(this);
    }

    return this.stub;
  }

  public Account setAccessToken(String accessToken) {
    this.accessToken = accessToken;
    return this;
  }

  public Account setUUID(String uuid) {
    this.uuid = uuid;
    return this;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getUUID() {
    return uuid;
  }
}