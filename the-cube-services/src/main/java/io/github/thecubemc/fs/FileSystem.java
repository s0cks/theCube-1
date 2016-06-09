package io.github.thecubemc.fs;

import java.nio.file.Path;

public abstract class FileSystem{
  protected final Path home;

  protected FileSystem(Path home){
    this.home = home;
  }

  public final Path getLocal(String path){
    return this.home.resolve(path);
  }
}