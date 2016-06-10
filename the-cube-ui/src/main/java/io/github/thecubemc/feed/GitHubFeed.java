package io.github.thecubemc.feed;

import com.google.inject.Singleton;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.net.URL;
import java.util.stream.Collectors;

@Singleton
public final class GitHubFeed
implements Feed{
  @Override
  public FeedArticle newest()
  throws Exception{
    try{
      URL feedUrl = new URL("https://github.com/theCubeMC/theCube/commits/master.atom");
      SyndFeedInput input = new SyndFeedInput();
      SyndFeed feed = input.build(new XmlReader(feedUrl));
      SyndEntry entry = feed.getEntries().get(0);
      return new FeedArticle(entry.getTitle(), entry.getAuthor(), entry.getLink(), entry.getContents().stream().map(SyndContent::getValue).collect(Collectors.joining(" ")).replaceAll("<.*?>","").replaceAll("&.*?;","").trim());
    } catch(Exception e){
      e.printStackTrace(System.err);
      return new FeedArticle("Error", "JVM", "", e.getMessage());
    }
  }
}