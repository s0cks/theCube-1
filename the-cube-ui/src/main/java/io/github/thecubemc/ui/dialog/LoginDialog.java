package io.github.thecubemc.ui.dialog;

import io.github.thecubemc.account.AccountStub;
import io.github.thecubemc.annotations.TheCubeResource;
import io.github.thecubemc.event.RequestLoginEvent;
import io.github.thecubemc.ui.TheCubeFrame;

import javax.inject.Inject;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;

public final class LoginDialog
extends JDialog{
  private final BufferedImage background;
  private final JTextField usernameField;
  private final JPasswordField passwordField;
  private final JButton closeButton;
  private final JButton loginButton;
  private RequestLoginEvent event;

  @Inject
  private LoginDialog(TheCubeFrame frame, AccountStub acc, @TheCubeResource("login") BufferedImage background){
    super(frame, "Login", ModalityType.APPLICATION_MODAL);
    this.setUndecorated(true);
    this.setLayout(new BorderLayout());
    this.setSize(new Dimension(background.getWidth(), background.getHeight()));
    this.setLocationRelativeTo(null);

    this.usernameField = new JTextField(16);
    this.passwordField = new JPasswordField(16);
    this.closeButton = new JButton("Close");
    this.loginButton = new JButton("Login");
    this.background = background;

    this.usernameField.setText(acc.getUsername());
    this.closeButton.addActionListener((e)->{
      event.cancel();
      this.dispose();
    });
    this.loginButton.addActionListener((e)->{
      this.dispose();
    });

    JPanel panel = new JPanel(new GridLayout(2, 3));
    panel.add(new JLabel("Username "));
    panel.add(this.usernameField);
    panel.add(new JLabel("Password "));
    panel.add(this.passwordField);
    JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    buttons.add(this.closeButton);
    buttons.add(this.loginButton);
    panel.add(Box.createHorizontalBox());
    panel.add(buttons);
    this.add(panel, BorderLayout.CENTER);
  }

  protected void setEvent(RequestLoginEvent e){
    this.event = e;
  }

  @Override
  public void dispose() {
    this.event.setPassword(new String(this.passwordField.getPassword()));
    this.event.setUsername(this.usernameField.getText());
    super.dispose();
  }

  @Override
  public void paint(Graphics graphics) {
    Graphics2D g2 = ((Graphics2D) graphics);
    g2.drawImage(this.background, 0, 0, this.getWidth(), this.getHeight(), null);
    super.paint(graphics);
  }
}