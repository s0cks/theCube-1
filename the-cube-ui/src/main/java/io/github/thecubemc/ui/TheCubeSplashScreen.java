package io.github.thecubemc.ui;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import io.github.thecubemc.annotations.TheCubeResource;

import javax.swing.JWindow;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

@Singleton
public final class TheCubeSplashScreen
extends JWindow {
  private final BufferedImage background;
  private final String version;
  private final Font font;

  private int progress = 0;

  @Inject
  private TheCubeSplashScreen(@TheCubeResource("splash") BufferedImage bg, @TheCubeResource("font") Font font, @Named("theCube-version") String version){
    this.background = bg;
    this.version = version;
    this.font = font;
    this.setSize(bg.getWidth(), bg.getHeight());
    this.setLocationRelativeTo(null);
    this.setAlwaysOnTop(true);
  }

  public void addProgress(int value){
    if(this.progress >= 100) return;
    this.progress += value;
    System.out.println(this.progress);
  }

  public int getProgress(){
    return this.progress;
  }

  @Override
  public void paint(Graphics graphics) {
    Graphics2D g2 = ((Graphics2D) graphics);
    g2.drawImage(this.background, 0, 0, this.getWidth(), this.getHeight(), null);
    g2.setColor(Color.WHITE);
    g2.setFont(this.font.deriveFont(24.0F));
    g2.drawString("theCube", 25, 49);
    float height = g2.getFontMetrics().getHeight();
    g2.setFont(this.font.deriveFont(12.0F));
    g2.drawString("v" + this.version, 65, 40 + height);
    g2.setFont(this.font.deriveFont(12.0F));

    System.out.println((this.progress * 100) / 10000);

    g2.setColor(Color.CYAN);
    g2.fillRect(0, this.getHeight() - 108, this.getWidth() * ((this.progress * 100) / 10000), 8);
  }
}