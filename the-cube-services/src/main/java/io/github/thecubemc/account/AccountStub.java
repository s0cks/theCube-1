package io.github.thecubemc.account;

public final class AccountStub{
  public final String minecraftUsername;

  private final Account owner;

  protected AccountStub(Account owner, String minecraftUsername){
    this.owner = owner;
    this.minecraftUsername = minecraftUsername;
  }

  public String getUsername(){
    return this.owner == null ? "Default" : this.owner.getUsername();
  }
}
