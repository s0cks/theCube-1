package io.github.thecubemc.fs;

import java.nio.file.Files;
import java.nio.file.Path;

public abstract class FileSystem{
  protected final Path home;

  protected FileSystem(Path home){
    this.home = home;
  }

  public final void initialize()
  throws Exception{
    Files.createDirectories(this.home.resolve("images"));
    Files.createDirectories(this.home.resolve("profiles"));
    Files.createDirectories(this.home.resolve("instances"));
    Files.createDirectories(this.home.resolve("services"));
    Files.createDirectories(this.home.resolve(".data"));
    Files.createDirectories(this.home.resolve(".session"));
  }

  public final Path getLocal(String path){
    return this.home.resolve(path);
  }
}