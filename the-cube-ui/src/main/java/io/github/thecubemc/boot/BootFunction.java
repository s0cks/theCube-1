package io.github.thecubemc.boot;

@FunctionalInterface
public interface BootFunction{
  public BootResult doBoot() throws Exception;
}