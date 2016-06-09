package io.github.thecubemc.model.mojang.api;

import com.google.gson.Gson;

import javax.xml.bind.DatatypeConverter;
import java.util.Map;

public final class UserProperty{
  public final String name;
  private final String value;

  private transient UserPropertyRaw raw;

  private UserProperty(String name, String value) {
    this.name = name;
    this.value = value;
  }

  public UserPropertyRaw get(Gson gson){
    if(this.raw == null){
      String json = new String(DatatypeConverter.parseBase64Binary(this.value));
      this.raw = gson.fromJson(json, UserPropertyRaw.class);
    }
    return this.raw;
  }

  public static final class UserPropertyRaw{
    public final boolean isPublic;
    public final String profileName;
    public final String profileId;

    private final Map<String, ProfileTexture> textures;

    private UserPropertyRaw(boolean isPublic, String profileName, String profileId, Map<String, ProfileTexture> textures) {
      this.isPublic = isPublic;
      this.profileName = profileName;
      this.profileId = profileId;
      this.textures = textures;
    }
  }

  public static final class ProfileTexture{
    public final String url;

    private ProfileTexture(String url){
      this.url = url;
    }
  }
}