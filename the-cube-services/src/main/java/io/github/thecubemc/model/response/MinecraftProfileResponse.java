package io.github.thecubemc.model.response;

import io.github.thecubemc.model.mojang.api.UserProperty;

import java.util.List;

public final class MinecraftProfileResponse{
  public final String id;
  public final String name;

  private final List<UserProperty> properties;

  public MinecraftProfileResponse(String id, String name, List<UserProperty> properties) {
    this.id = id;
    this.name = name;
    this.properties = properties;
  }

  public UserProperty getUserProperty(String name){
    for(UserProperty property : this.properties){
      if(property.name.equals(name)){
        return property;
      }
    }

    return null;
  }
}