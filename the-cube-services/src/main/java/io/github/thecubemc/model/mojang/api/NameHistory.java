package io.github.thecubemc.model.mojang.api;

public final class NameHistory{
  public final String name;
  public final long changedToAt;

  public NameHistory(String name, long changedToAt) {
    this.name = name;
    this.changedToAt = changedToAt;
  }

  public boolean isChange(){
    return this.changedToAt == 0;
  }
}