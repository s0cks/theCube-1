package io.github.thecubemc.feed;

public final class FeedArticle{
  public final String title;
  public final String source;
  public final String link;
  public final String body;

  protected FeedArticle(String title, String source, String link, String body) {
    this.title = title;
    this.source = source;
    this.link = link;
    this.body = body;
  }
}