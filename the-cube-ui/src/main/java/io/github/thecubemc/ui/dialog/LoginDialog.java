package io.github.thecubemc.ui.dialog;

import io.github.thecubemc.TheCube;
import io.github.thecubemc.annotations.TheCubeResource;
import io.github.thecubemc.event.RequestLoginEvent;
import io.github.thecubemc.ui.TheCubeFrame;
import io.github.thecubemc.ui.panel.MinecraftLoginPanel;
import io.github.thecubemc.ui.panel.TheCubeFeedPanel;

import javax.inject.Inject;
import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public final class LoginDialog
extends JDialog{
  private final BufferedImage background;
  private final MinecraftLoginPanel login;
  private RequestLoginEvent event;

  @Inject
  private LoginDialog(TheCubeFrame frame, @TheCubeResource("login") BufferedImage background){
    super(frame, "Login", ModalityType.APPLICATION_MODAL);
    this.setUndecorated(true);
    this.setContentPane(new JPanel(){
      {
        this.setOpaque(false);
      }

      @Override
      public void paint(Graphics graphics) {
        Graphics2D g2 = ((Graphics2D) graphics);
        g2.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), null);
        super.paint(graphics);
      }
    });
    this.setLayout(new BorderLayout());
    this.setSize(new Dimension(background.getWidth(), background.getHeight()));
    this.setLocationRelativeTo(null);
    this.background = background;
    this.add((this.login = TheCube.injector.getInstance(MinecraftLoginPanel.class)), BorderLayout.WEST);
    this.add(new TheCubeFeedPanel(), BorderLayout.CENTER);
  }

  protected void setEvent(RequestLoginEvent e){
    this.event = e;
  }

  @Override
  public void dispose() {
    this.event.setPassword(this.login.getPassword());
    this.event.setUsername(this.login.getUsername());
    super.dispose();
  }
}