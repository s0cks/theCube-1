package io.github.thecubemc.model.response;

public final class MojangProfileResponse{
  public final String name;
  public final String id;
  public final boolean legacy;
  public final boolean demo;

  private MojangProfileResponse(String name, String id, boolean legacy, boolean demo) {
    this.name = name;
    this.id = id;
    this.legacy = legacy;
    this.demo = demo;
  }
}