package io.github.thecubemc.model.mojang;

public final class MojangAssetIndex{
  public final String id;
  public final String sha1;
  public final String url;
  public final long size;
  public final long totalSize;

  private MojangAssetIndex(String id, String sha1, String url, long size, long totalSize) {
    this.id = id;
    this.sha1 = sha1;
    this.url = url;
    this.size = size;
    this.totalSize = totalSize;
  }
}