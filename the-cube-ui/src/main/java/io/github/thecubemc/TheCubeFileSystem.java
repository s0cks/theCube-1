package io.github.thecubemc;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import io.github.thecubemc.fs.FileSystem;

import java.nio.file.Path;

@Singleton
final class TheCubeFileSystem
extends FileSystem{
  @Inject
  private TheCubeFileSystem(@Named("theCube-home")Path home){
    super(home);
  }
}