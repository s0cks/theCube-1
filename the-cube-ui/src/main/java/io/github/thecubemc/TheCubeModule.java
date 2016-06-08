package io.github.thecubemc;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import io.github.thecubemc.fs.FileSystem;
import io.github.thecubemc.http.Network;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ThreadFactory;

final class TheCubeModule
extends AbstractModule {
  @Override
  protected void configure() {
    this.bind(String.class)
        .annotatedWith(Names.named("theCube-version"))
        .toInstance("1.0.0.0");
    this.bind(Path.class)
        .annotatedWith(Names.named("theCube-home"))
        .toInstance(Paths.get(System.getProperty("user.dir"), ".theCube"));
    this.bind(Network.class)
        .to(TheCubeNetwork.class);
    this.bind(FileSystem.class)
        .to(TheCubeFileSystem.class);
    this.bind(ThreadFactory.class)
        .to(TheCubeThreadFactory.class);
  }
}