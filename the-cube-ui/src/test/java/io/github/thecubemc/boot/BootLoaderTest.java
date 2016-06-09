package io.github.thecubemc.boot;

import org.junit.Test;

public class BootLoaderTest {
  @Test
  public void testRunBootSequence()
  throws Exception {
    BootLoader.runBootSequence(4);
  }
}