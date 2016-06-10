package io.github.thecubemc.ui.panel;

import com.google.inject.Inject;
import io.github.thecubemc.TheCube;
import io.github.thecubemc.annotations.GitHubFeed;
import io.github.thecubemc.annotations.TheCubeResource;
import io.github.thecubemc.feed.Feed;
import io.github.thecubemc.feed.FeedArticle;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public final class TheCubeFeedPanel
extends JPanel{
  private final GridBagConstraints gbc = new GridBagConstraints();

  public TheCubeFeedPanel(){
    super(new GridBagLayout());
    this.setPreferredSize(new Dimension(350, 400));
    this.setOpaque(false);
    this.gbc.fill = GridBagConstraints.BOTH;
    this.gbc.weightx = 1.0;
    this.gbc.weighty = 1.0;
    this.gbc.gridx = 0;
    this.gbc.gridy = 0;
    this.gbc.insets.set(35, 35, 2, 35);
    this.add(TheCube.injector.getInstance(TheCubeGitHubFeedCard.class), this.gbc);
    this.gbc.insets.set(2, 35, 2, 35);
    this.gbc.gridy++;
    this.add(TheCube.injector.getInstance(TheCubeGitHubFeedCard.class), this.gbc);
  }

  private static final class TheCubeGitHubFeedCard
  extends JPanel {
    private final FeedArticle article;
    private final Font font;

    @Inject
    private TheCubeGitHubFeedCard(@GitHubFeed Feed feed, @TheCubeResource("font") Font font){
      this.setOpaque(false);
      this.setPreferredSize(new Dimension(128, 128));
      this.font = font;
      try {
        this.article = feed.newest();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    @Override
    public void paint(Graphics graphics) {
      Graphics2D g2 = ((Graphics2D) graphics);
      g2.setColor(Color.WHITE);
      g2.fillRect(0, 0, this.getWidth(), this.getHeight());
      g2.setColor(Color.BLACK);
      g2.setFont(this.font.deriveFont(16.0F));
      g2.drawString(this.article.title, 10, 2 + g2.getFontMetrics().getHeight());
      int y = 2 + g2.getFontMetrics().getHeight() + 25;
      g2.setFont(this.font.deriveFont(12.0F));
      g2.drawString("- " + this.article.source, 5, y);

      String[] lines = this.wrap(this.article.body, 45);
      System.out.println(lines.length);
      for(int i = 0; i < lines.length; i++){
        g2.drawString(lines[i], (i == 0 ? 5 : 10), y += g2.getFontMetrics().getHeight() + 1);
      }
    }

    private String[] wrap(String text, int len){
      StringBuilder builder = new StringBuilder(text);
      int i = 0;
      while (i + len < builder.length() && (i = builder.lastIndexOf(" ", i + len)) != -1) {
        builder.replace(i, i + 1, "\n");
      }
      return builder.toString().split("\n");
    }
  }
}