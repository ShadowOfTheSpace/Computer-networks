import javafx.scene.effect.Effect;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class Button extends JButton {
    private Color currentBackgroundColor = MainPanel.getBackgroundColor();
    private Color currentBorderColor;

    private Color darkHoverColor = Color.BLACK;
    private Color darkPressedColor = Color.WHITE;
    private Color lightHoverColor = Color.BLUE;
    private Color lightPressedColor = Color.CYAN;
    private boolean mouseIsInsideButton = false;


    public void ChangeTheme() {
        this.currentBackgroundColor = MainPanel.getBackgroundColor();
    }

    public Button(String text) {
        super(text);
        this.setFont(new Font("Arial", Font.BOLD, 24));
        this.setPreferredSize(new Dimension(250, 60));
        this.setFocusable(false);
        this.setContentAreaFilled(false);
        this.setBorder(null);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                currentBackgroundColor = Window.darkModeEnabled ? darkPressedColor : lightPressedColor;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (mouseIsInsideButton) {
                    currentBackgroundColor = Window.darkModeEnabled ? darkHoverColor : lightHoverColor;
                } else {
                    currentBackgroundColor = MainPanel.getBackgroundColor();
                }
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                mouseIsInsideButton = true;
                currentBackgroundColor = Window.darkModeEnabled ? darkHoverColor : lightHoverColor;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                mouseIsInsideButton = false;
                currentBackgroundColor = MainPanel.getBackgroundColor();
                repaint();
            }
        });
    }

    @Override
    public void paint(Graphics g) {

        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setColor(currentBackgroundColor);
        graphics2D.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

//        graphics2D.setColor(MenuPanel.getBackgroundColor().darker());
//        graphics2D.fillRoundRect(3, 3, getWidth() - 6, getHeight() - 6, 10, 10);


        graphics2D.setColor(Window.darkModeEnabled ? Color.WHITE : Color.BLACK);
        FontMetrics fm = getFontMetrics(getFont());
        int x = ((getWidth() - fm.stringWidth(getText())) / 2);
        int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
        graphics2D.drawString(getText(), x, y);

    }
}
