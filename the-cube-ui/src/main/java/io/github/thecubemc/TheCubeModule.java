package io.github.thecubemc;

import com.google.common.eventbus.EventBus;
import com.google.gson.Gson;
import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.name.Names;
import io.github.thecubemc.account.AccountFactory;
import io.github.thecubemc.account.AccountStub;
import io.github.thecubemc.annotations.GitHubFeed;
import io.github.thecubemc.feed.Feed;
import io.github.thecubemc.fs.FileSystem;
import io.github.thecubemc.http.Network;

import javax.imageio.ImageIO;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ThreadFactory;

final class TheCubeModule
extends AbstractModule {
  private final Gson gson = new Gson();

  @Override
  protected void configure() {
    // Default Values
    this.bind(String.class)
        .annotatedWith(Names.named("theCube-version"))
        .toInstance("1.0.0.0");
    this.bind(Path.class)
        .annotatedWith(Names.named("theCube-home"))
        .toInstance(Paths.get(System.getProperty("user.dir"), ".theCube"));

    // Default Implementations
    this.bind(Network.class)
        .to(TheCubeNetwork.class);
    this.bind(FileSystem.class)
        .to(TheCubeFileSystem.class);
    this.bind(ThreadFactory.class)
        .to(TheCubeThreadFactory.class);
    this.bind(Gson.class)
        .toInstance(this.gson);
    this.bind(AccountStub.class)
        .toProvider(AccountFactory.class);
    this.bind(EventBus.class)
        .toProvider(() -> TheCube.instance().events);
    this.bind(Feed.class).annotatedWith(GitHubFeed.class).to(io.github.thecubemc.feed.GitHubFeed.class);

    // Resources
    this.bind(BufferedImage.class)
        .annotatedWith(TheCubeResources.resource("splash"))
        .toProvider(new ImageResourceProvider("splash.png"));
    this.bind(BufferedImage.class)
        .annotatedWith(TheCubeResources.resource("login"))
        .toProvider(new ImageResourceProvider("login.png"));
    this.bind(Font.class)
        .annotatedWith(TheCubeResources.resource("font"))
        .toProvider(new FontResourceProvider("font.ttf"));
  }

  private static final class FontResourceProvider
  implements Provider<Font> {
    private final String path;

    private FontResourceProvider(String path) {
      this.path = path;
    }

    @Override
    public Font get() {
      try (InputStream in = System.class.getResourceAsStream("/assets/theCube/" + this.path)) {
        return Font.createFont(Font.TRUETYPE_FONT, in)
                   .deriveFont(12.0F);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }

  private static final class ImageResourceProvider
  implements Provider<BufferedImage> {
    private final String path;

    private ImageResourceProvider(String path) {
      this.path = path;
    }

    @Override
    public BufferedImage get() {
      try (InputStream in = System.class.getResourceAsStream("/assets/theCube/" + this.path)) {
        return ImageIO.read(in);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }
}