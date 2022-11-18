import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.IOException;

public class SwitchButton extends JComponent {
    private static final int WIDTH = 110;
    private static final int HEIGHT = 50;
    private static final int SIZE_OF_CYCLE = 40;
    private static final int ARC = 50;
    private final int startPosition = (HEIGHT - SIZE_OF_CYCLE) / 2;
    private final int endPosition = WIDTH - SIZE_OF_CYCLE - startPosition;
    private float currentPosition = startPosition;
    private static final float speed = 0.2f;
    private final Timer timer;
    private static final Image moonIcon;
    private static final Image sunIcon;

    private int rectangleX, rectangleY;

    static {
        try {
            moonIcon = ImageIO.read(new File("images/moon.png")).getScaledInstance(SIZE_OF_CYCLE, SIZE_OF_CYCLE, Image.SCALE_SMOOTH);
            sunIcon = ImageIO.read(new File("images/sun.png")).getScaledInstance(SIZE_OF_CYCLE, SIZE_OF_CYCLE, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public SwitchButton() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Palette.DARK_SWITCH_BUTTON_BACKGROUND);
        this.setForeground(Palette.LIGHT_SWITCH_BUTTON_BACKGROUND);
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int rectangleX = (getWidth() - WIDTH) / 2, rectangleY = (getHeight() - HEIGHT) / 2;
                if (new Rectangle(rectangleX, rectangleY, WIDTH, HEIGHT).contains(e.getX(), e.getY())) {
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    Window.darkModeEnabled = !Window.darkModeEnabled;
                    Window.menuPanel.changeTheme();
                    Window.mainPanel.paintImmediately(0, 0, Window.mainPanel.getWidth(), Window.mainPanel.getHeight());
                    timer.start();
                }
            }
        });
        timer = new Timer(0, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Window.darkModeEnabled) {
                    if (currentPosition < endPosition) {
                        currentPosition += speed;
                        repaint();
                    } else {
                        currentPosition = endPosition;
                        timer.stop();
                        setBackground(Palette.LIGHT_SWITCH_BUTTON_BACKGROUND);
                    }
                } else {
                    setBackground(Palette.DARK_SWITCH_BUTTON_BACKGROUND);
                    if (currentPosition > startPosition) {
                        currentPosition -= speed;
                        repaint();
                    } else {
                        currentPosition = startPosition;
                        timer.stop();
                    }
                }
            }
        });
    }

//    private boolean mouseInsideButton(int x, int y){
//        Ellipse2D buttonArea = new Ellipse2D.Float(this.)
//        return false;
//    }

    @Override
    public void paint(Graphics g) {
        this.rectangleX = (this.getWidth() - WIDTH) / 2;
        this.rectangleY = (this.getHeight() - HEIGHT) / 2;
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setColor(Palette.getMenuPanelBackground());
        graphics2D.fillRect(0, 0, this.getWidth(), this.getHeight());
        graphics2D.setColor(Palette.DARK_SWITCH_BUTTON_BACKGROUND);
        graphics2D.fillRoundRect(rectangleX, rectangleY, WIDTH, HEIGHT, ARC, ARC);
        graphics2D.setColor(Palette.LIGHT_SWITCH_BUTTON_BACKGROUND);
        graphics2D.setStroke(new BasicStroke(2));
        graphics2D.drawRoundRect(rectangleX, rectangleY, WIDTH, HEIGHT, ARC, ARC);
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, getAlpha()));
        graphics2D.setColor(Palette.LIGHT_SWITCH_BUTTON_BACKGROUND);
        graphics2D.fillRoundRect(rectangleX, rectangleY, WIDTH, HEIGHT, ARC, ARC);
        graphics2D.setColor(Palette.DARK_SWITCH_BUTTON_BACKGROUND);
        graphics2D.drawRoundRect(rectangleX, rectangleY, WIDTH, HEIGHT, ARC, ARC);
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        graphics2D.setColor(new Color(255, 255, 255, 255));
        graphics2D.fillOval((int) (rectangleX + currentPosition), rectangleY + (HEIGHT - SIZE_OF_CYCLE) / 2, SIZE_OF_CYCLE, SIZE_OF_CYCLE);
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, getAlpha()));
        graphics2D.drawImage(moonIcon, (int) (rectangleX + currentPosition), rectangleY + (HEIGHT - SIZE_OF_CYCLE) / 2, null);
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1 - getAlpha()));
        graphics2D.drawImage(sunIcon, (int) (rectangleX + currentPosition), rectangleY + (HEIGHT - SIZE_OF_CYCLE) / 2, null);

    }

    private float getAlpha() {
        float alpha = (currentPosition - startPosition) / (endPosition - startPosition);
        return Math.max(0, Math.min(1, alpha));
    }
}
