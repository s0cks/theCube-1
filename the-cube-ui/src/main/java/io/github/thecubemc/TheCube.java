package io.github.thecubemc;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.github.thecubemc.http.Network;

public final class TheCube{
  public static final Injector injector = Guice.createInjector(new TheCubeModule());

  public static void main(String... args){
    Network net = injector.getInstance(Network.class);
    System.out.println(net.userAgent());
  }
}