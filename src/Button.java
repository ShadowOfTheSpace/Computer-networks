
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class Button extends JButton {
    private Color currentBackgroundColor = Palette.getMainPanelBackground();


    private static Color effectColor = Color.WHITE;
    private boolean mouseIsInsideButton = false;
    private int x, y;

    public void changeTheme() {
        this.currentBackgroundColor = Palette.getMainPanelBackground();
        repaint();
    }

    public Button(String text) {
        super(text);
        this.setPreferredSize(new Dimension(250,60));
//        this.setMaximumSize(new Dimension(250,60));
        this.setFont(new Font("Arial", Font.BOLD, 24));
        this.setFocusable(false);
        this.setContentAreaFilled(false);
        this.setBorder(null);
        this.setOpaque(true);
        this.addMouseListener(new MouseAdapter() {
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
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                x = e.getX();
                y = e.getY();
                repaint();
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

//        graphics2D.setComposite(AlphaComposite.SrcOut);
//        Shape mouseOver = new Area(new Ellipse2D.Double(x - 5, y - 5, 10, 10));
//
//
//        if (mouseIsInsideButton) {
//            graphics2D.setPaint(new RadialGradientPaint(new Point(x, y), 10, new float[]{0.0f, 0.5f, 1.0f}, getEffectColor(Color.WHITE)));
//            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, 0.3f));
//
//
//        }
//        graphics2D.setColor(Color.WHITE);
//        graphics2D.fill(mouseOver);
//        graphics2D.setColor(currentBackgroundColor);
//        graphics2D.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);


        graphics2D.setColor(MenuPanel.getBackgroundColor());
        graphics2D.fillRect(0, 0, getWidth(), getHeight());
        graphics2D.setColor(currentBackgroundColor);
        graphics2D.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        graphics2D.setFont(new Font("Arial", Font.BOLD, 24));
        graphics2D.setColor(Palette.getFontColor());
        FontMetrics fm = getFontMetrics(getFont());
        int textX = ((getWidth() - fm.stringWidth(getText())) / 2);
        int textY = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
        graphics2D.drawString(getText(), textX, textY);
    }

    public Color[] getEffectColor(Color effectColor) {
        Button.effectColor = effectColor;
        int red = effectColor.getRed();
        int green = effectColor.getGreen();
        int blue = effectColor.getBlue();
        return new Color[]{new Color(red, green, blue, 70), new Color(red, green, blue, 20), new Color(red, green, blue, 0)};
    }
}
