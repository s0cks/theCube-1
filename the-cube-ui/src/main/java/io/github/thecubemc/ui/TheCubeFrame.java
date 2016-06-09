package io.github.thecubemc.ui;

import com.google.inject.Singleton;
import com.google.inject.name.Named;
import io.github.thecubemc.TheCube;
import io.github.thecubemc.account.AccountFactory;

import javax.inject.Inject;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.io.IOException;

@Singleton
public final class TheCubeFrame
extends JFrame
implements MouseMotionListener,
           MouseListener{
  private final AccountFactory accounts;
  private final String version;
  private int dX;
  private int dY;

  @Inject
  private TheCubeFrame(@Named("theCube-version") String version, AccountFactory factory)
  throws IOException {
    super("theCube - v" + version);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(new Dimension(1000, 640));
    this.accounts = factory;
    this.version = version;
  }

  public void setStatusTitle(String status){
    this.setTitle("theCube - v" + this.version + " - " + status);
  }

  @Override
  public void paint(Graphics graphics) {
    Graphics2D g2 = ((Graphics2D) graphics);
    AffineTransform t = g2.getTransform();
    AffineTransform rotate = new AffineTransform();
    rotate.setToRotation(Math.toRadians(270), this.getWidth() - 10, ((this.getHeight() - g2.getFontMetrics().getAscent()) / 2) - 2);
    g2.setTransform(rotate);
    g2.setColor(Color.BLACK);
    try {
      g2.drawString(this.accounts.get().getMinecraftUsername(TheCube.injector), 25, 25);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    g2.setTransform(t);
  }

  @Override
  public void mouseDragged(MouseEvent mouseEvent) {
    if((mouseEvent.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) != 0){
      this.setLocation(mouseEvent.getXOnScreen() - this.dX, mouseEvent.getYOnScreen() - this.dY);
    }
  }

  @Override
  public void mousePressed(MouseEvent mouseEvent) {
    if(mouseEvent.getButton() == MouseEvent.BUTTON1){
      this.dX = mouseEvent.getX();
      this.dY = mouseEvent.getY();
    }
  }

  @Override public void mouseMoved(MouseEvent mouseEvent) {}
  @Override public void mouseReleased(MouseEvent mouseEvent) {}
  @Override public void mouseEntered(MouseEvent mouseEvent) {}
  @Override public void mouseExited(MouseEvent mouseEvent) {}
  @Override public void mouseClicked(MouseEvent mouseEvent) {}
}