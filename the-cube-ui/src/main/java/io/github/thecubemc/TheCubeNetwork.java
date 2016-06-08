package io.github.thecubemc;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import io.github.thecubemc.http.Network;

@Singleton
final class TheCubeNetwork
extends Network {
  private final String userAgent;

  @Inject
  private TheCubeNetwork(@Named("theCube-version") String version){
    this.userAgent = "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.72 Safari/537.36 theCube/" + version;
  }

  @Override
  public String userAgent() {
    return this.userAgent;
  }
}