package io.github.thecubemc.ui.panel;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicPasswordFieldUI;
import javax.swing.plaf.basic.BasicTextFieldUI;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public final class MinecraftLoginPanel
extends JPanel {
  private final JButton loginButton = new JButton("Login"){{
    this.setUI(new ButtonSkin());
  }};
  private final JButton cancelButton = new JButton("Cancel"){{
    this.setUI(new ButtonSkin());
  }};
  private final JTextField usernameField = new JTextField(16){{
    this.setForeground(Color.WHITE);
    this.setUI(new TextFieldSkin());
  }};
  private final JPasswordField passwordField = new JPasswordField(16){{
    this.setForeground(Color.WHITE);
    this.setUI(new PasswordFieldSkin());
    this.setEchoChar('*');
  }};

  public MinecraftLoginPanel(){
    super(new GridBagLayout());
    this.setOpaque(false);
    this.setPreferredSize(new Dimension(350, 400));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = gbc.gridy = 1;
    gbc.gridwidth = 1;
    gbc.insets.set(5, 2, 5, 2);
    gbc.anchor = GridBagConstraints.WEST;
    this.add(new JLabel("Username: ", JLabel.LEFT){{
      this.setForeground(Color.WHITE);
    }}, gbc);
    gbc.gridy++;
    this.add(this.usernameField, gbc);
    gbc.gridy++;
    this.add(new JLabel("Password: ", JLabel.LEFT){{
      this.setForeground(Color.WHITE);
    }}, gbc);
    gbc.gridy++;
    this.add(this.passwordField, gbc);
    gbc.gridy++;
    gbc.fill = GridBagConstraints.NONE;
    JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    buttonsPanel.add(this.cancelButton);
    buttonsPanel.add(this.loginButton);
    buttonsPanel.setOpaque(false);
    this.setBackground(Color.BLACK);
    this.add(buttonsPanel, gbc);
  }

  @Override
  public void paint(Graphics graphics) {
    Graphics2D g2 = ((Graphics2D) graphics);
    Composite composite = g2.getComposite();
    g2.setComposite(AlphaComposite.SrcOver.derive(0.5F));
    g2.setColor(Color.BLACK);
    g2.fillRect(0, 0, this.getWidth(), this.getHeight());
    g2.setComposite(composite);
    super.paint(graphics);
  }

  public String getUsername(){
    return this.usernameField.getText();
  }

  public String getPassword(){
    return new String(this.passwordField.getPassword());
  }

  private static final class TextFieldSkin
  extends BasicTextFieldUI{
    @Override
    public void installUI(JComponent jComponent) {
      super.installUI(jComponent);
      jComponent.setOpaque(false);
    }

    @Override protected void paintBackground(Graphics graphics) {}
  }

  private static final class PasswordFieldSkin
  extends BasicPasswordFieldUI{
    @Override
    public void installUI(JComponent jComponent) {
      super.installUI(jComponent);
      jComponent.setOpaque(false);
    }

    @Override protected void paintBackground(Graphics graphics) {}
  }

  private static final class ButtonSkin
  extends BasicButtonUI {
    @Override
    public void installUI(JComponent jComponent) {
      super.installUI(jComponent);
      JButton button = ((JButton) jComponent);
      button.setFocusPainted(false);
      button.setBorderPainted(false);
    }

    @Override
    public void paint(Graphics graphics, JComponent jComponent) {
      JButton button = ((JButton) jComponent);
      Graphics2D g2 = ((Graphics2D) graphics);
      g2.setColor(button.getModel().isPressed() ? Color.BLACK : Color.WHITE);
      g2.fillRect(0, 0, button.getWidth(), button.getHeight());
      int x = ((button.getWidth() - g2.getFontMetrics().stringWidth(button.getText())) / 2);
      int y = ((button.getHeight() - g2.getFontMetrics().getHeight()) / 2) + g2.getFontMetrics().getAscent();
      g2.setColor(button.getModel().isPressed() ? Color.WHITE : Color.BLACK);
      g2.drawString(button.getText(), x, y);
    }
  }
}