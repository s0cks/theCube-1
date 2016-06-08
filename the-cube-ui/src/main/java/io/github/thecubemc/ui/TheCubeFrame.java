package io.github.thecubemc.ui;

import com.google.inject.Singleton;
import com.google.inject.name.Named;

import javax.inject.Inject;
import javax.swing.JFrame;
import java.awt.Dimension;

@Singleton
public final class TheCubeFrame
extends JFrame {
  @Inject
  private TheCubeFrame(@Named("theCube-version") String version){
    super("theCube - v" + version);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(new Dimension(1000, 640));
  }
}