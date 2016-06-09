package io.github.thecubemc.model.mojang;

import com.google.gson.annotations.SerializedName;

public final class MojangVersion {
  public final String id;
  public final String mainClass;
  public final String assets;
  @SerializedName("minecraftArguments")
  public final String arguments;
  @SerializedName("assetIndex")
  public final MojangAssetIndex index;

  private MojangVersion(String id, String mainClass, String assets, String arguments, MojangAssetIndex index) {
    this.id = id;
    this.mainClass = mainClass;
    this.assets = assets;
    this.arguments = arguments;
    this.index = index;
  }

  public String getAssets(){
    return this.assets == null ? "legacy" : this.assets;
  }
}