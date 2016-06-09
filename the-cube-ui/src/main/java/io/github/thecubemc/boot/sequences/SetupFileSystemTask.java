package io.github.thecubemc.boot.sequences;

import com.google.inject.Inject;
import io.github.thecubemc.boot.BootResult;
import io.github.thecubemc.fs.FileSystem;
import io.github.thecubemc.ui.TheCubeSplashScreen;

import java.nio.file.Files;

final class SetupFileSystemTask{
  private final FileSystem fileSystem;
  private final TheCubeSplashScreen splash;

  private final String[] resolves = new String[]{
    "images",
    "profiles",
    "instances",
    "services",
    ".data",
    ".session"
  };

  @Inject
  private SetupFileSystemTask(FileSystem fileSystem, TheCubeSplashScreen splash){
    this.fileSystem = fileSystem;
    this.splash = splash;
  }

  public BootResult doTask(){
    try {
      for(String resolve : resolves){
        this.splash.setMessage("Creating Directory - ./" + resolve);
        Files.createDirectories(this.fileSystem.getLocal(resolve));
      }
      return BootResult.PASS;
    } catch (Exception e) {
      return BootResult.FAIL;
    }
  }
}