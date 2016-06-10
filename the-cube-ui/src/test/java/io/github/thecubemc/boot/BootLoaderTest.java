package io.github.thecubemc.boot;

import io.github.thecubemc.feed.GitHubFeed;
import org.junit.Test;

public class BootLoaderTest {
  @Test
  public void testRunBootSequence()
  throws Exception {
    GitHubFeed feed = new GitHubFeed();
    System.out.println(feed.newest().body.replaceAll("<.*?>","").replaceAll("&.*?;","").trim());
  }
}