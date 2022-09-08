import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class SwitchButton extends JComponent {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 50;
    private static final int SIZE_OF_CYCLE = 40;
    private static final int ARC = 50;
    private int startPosition = (HEIGHT - SIZE_OF_CYCLE) / 2;
    private int endPosition = WIDTH - SIZE_OF_CYCLE - startPosition;
    private int currentPosition = startPosition;
    private int speed = 1;
    private Timer timer;
    private static final Image moonIcon;
    private static final Image sunIcon;

    static {
        try {
            moonIcon = ImageIO.read(new File("images/moon.png")).getScaledInstance(SIZE_OF_CYCLE, SIZE_OF_CYCLE, Image.SCALE_SMOOTH);
            sunIcon = ImageIO.read(new File("images/sun.png")).getScaledInstance(SIZE_OF_CYCLE, SIZE_OF_CYCLE, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public SwitchButton() {
        this.setBackground(Color.decode("#FFAA1D"));
        this.setForeground(Color.decode("#27173A"));
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
                        setBackground(Color.decode("#27173A"));
                    }
                } else {
                    setBackground(Color.decode("#FFAA1D"));
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

    @Override
    public void paint(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setColor(getBackground());

        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int rectangleX = (this.getWidth() - WIDTH) / 2, rectangleY = (this.getHeight() - HEIGHT) / 2;
        graphics2D.fillRoundRect(rectangleX, rectangleY, WIDTH, HEIGHT, ARC, ARC);
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, getAlpha()));
        graphics2D.setColor(this.getForeground());
        graphics2D.fillRoundRect(rectangleX, rectangleY, WIDTH, HEIGHT, ARC, ARC);
//        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
//        graphics2D.setColor(new Color(255, 255, 255, 25));
//        graphics2D.fillOval(rectangleX + currentPosition, rectangleY + (HEIGHT - SIZE_OF_CYCLE) / 2, SIZE_OF_CYCLE, SIZE_OF_CYCLE);
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, getAlpha()));
        graphics2D.drawImage(moonIcon, rectangleX + currentPosition, rectangleY + (HEIGHT - SIZE_OF_CYCLE) / 2, null);
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1 - getAlpha()));
        graphics2D.drawImage(sunIcon, rectangleX + currentPosition, rectangleY + (HEIGHT - SIZE_OF_CYCLE) / 2, null);
    }

    private float getAlpha() {
        return (currentPosition - startPosition) / (float) (endPosition - startPosition);
    }
}
