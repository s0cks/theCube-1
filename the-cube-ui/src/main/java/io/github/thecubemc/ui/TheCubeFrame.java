package io.github.thecubemc.ui;

import com.google.inject.Singleton;
import com.google.inject.name.Named;

import javax.inject.Inject;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

@Singleton
public final class TheCubeFrame
extends JFrame
implements MouseMotionListener,
           MouseListener{
  private int dX;
  private int dY;

  @Inject
  private TheCubeFrame(@Named("theCube-version") String version){
    super("theCube - v" + version);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(new Dimension(1000, 640));
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