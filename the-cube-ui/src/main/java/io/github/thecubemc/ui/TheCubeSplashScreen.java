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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

@Singleton
public final class TheCubeSplashScreen
extends JWindow
implements PropertyChangeListener{
  private final BufferedImage background;
  private final String version;
  private final Font font;

  private int progress = 0;
  private String message = "Loading theCube";

  @Inject
  private TheCubeSplashScreen(@TheCubeResource("splash") BufferedImage bg, @TheCubeResource("font") Font font, @Named("theCube-version") String version){
    this.background = bg;
    this.version = version;
    this.font = font;
    this.setSize(bg.getWidth(), bg.getHeight());
    this.setLocationRelativeTo(null);
    this.addPropertyChangeListener(this);
  }

  public void addProgress(int value){
    this.firePropertyChange("progress", this.progress, this.progress + value);
  }

  public void setMessage(String value){
    this.firePropertyChange("message", this.message, value);
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
    g2.setColor(Color.CYAN);
    float width = ((float) this.progress) / 100;
    g2.fillRect(0, this.getHeight() - 108, (int) (this.getWidth() * width), 8);
    g2.drawString(this.message, 12, this.getHeight() - 108 - g2.getFontMetrics().getHeight());
  }

  @Override
  public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
    if(propertyChangeEvent.getPropertyName().equals("progress")){
      this.progress = ((int) propertyChangeEvent.getNewValue());
      this.repaint();
    } else if(propertyChangeEvent.getPropertyName().equals("message")){
      this.message = ((String) propertyChangeEvent.getNewValue());
      this.repaint();
    }
  }
}