package Program.UI.Elements.Buttons;

import Program.UI.Colors.Palette;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Button extends JButton implements MouseListener {
    protected Color currentBackgroundColor = Palette.getButtonBackground();
    private boolean mouseIsInsideButton = false;
    protected boolean transparency;

    public void changeTheme() {
        this.currentBackgroundColor = Palette.getButtonBackground();
        repaint();
    }

    public Button(String text, boolean transparency) {
        super(text);
        this.setFont(new Font("Arial", Font.BOLD, 24));
        this.setFocusable(false);
        this.setContentAreaFilled(false);
        this.setBorder(null);
        this.setOpaque(true);
        this.addMouseListener(this);
        this.transparency = transparency;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g.create();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setColor(Palette.getMenuPanelBackground());
        graphics2D.fillRect(0, 0, getWidth(), getHeight());
        Color color = currentBackgroundColor;
        if (transparency) {
            int red = color.getRed();
            int green = color.getGreen();
            int blue = color.getBlue();
            color = new Color(red, green, blue, (int) (255 * getAlpha()));
        }
        graphics2D.setColor(color);
        graphics2D.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        drawText(graphics2D);
    }

    protected void drawText(Graphics2D graphics2D) {
        graphics2D.setFont(new Font("Arial", Font.BOLD, 24));
        graphics2D.setColor(Palette.getFontColor());
        FontMetrics fm = getFontMetrics(getFont());
        int textX = ((getWidth() - fm.stringWidth(getText())) / 2);
        int textY = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
        graphics2D.drawString(getText(), textX, textY);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        currentBackgroundColor = Palette.getButtonPressBackground();
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (mouseIsInsideButton) {
            currentBackgroundColor = Palette.getButtonHoverBackground();
        } else {
            currentBackgroundColor = Palette.getButtonBackground();
        }
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        mouseIsInsideButton = true;
        currentBackgroundColor = Palette.getButtonHoverBackground();
        repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        mouseIsInsideButton = false;
        currentBackgroundColor = Palette.getButtonBackground();
        repaint();
    }


    public float getAlpha() {
        return 1f;
    }
}
