import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class SwitchButton extends JComponent {
    public static final int WIDTH = 110;
    public static final int HEIGHT = 50;
    public static final int SIZE_OF_CYCLE = 40;
    private static final int ARC = 50;
    private final int startPosition = (HEIGHT - SIZE_OF_CYCLE) / 2;
    private final int endPosition = WIDTH - SIZE_OF_CYCLE - startPosition;
    private float currentPosition = startPosition;
    private static final float speed = 0.2f;
    private final Timer timer;
    private int rectangleX, rectangleY;
    private final int alpha;



    public SwitchButton(Color background, Color foreground, int alpha, boolean state) {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(background);
        this.setForeground(foreground);
        this.alpha = alpha;
        if (state) {
            currentPosition = endPosition;
        }
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
                    buttonPressed();
                    timer.start();
                }
            }
        });
        timer = new Timer(0, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkCondition()) {
                    if (currentPosition < endPosition) {
                        currentPosition += speed;
                        repaint();
                    } else {
                        currentPosition = endPosition;
                        timer.stop();
                        setBackground(Palette.DARK_SWITCH_THEME_BUTTON_BACKGROUND);
                    }
                } else {
                    setBackground(Palette.LIGHT_SWITCH_THEME_BUTTON_BACKGROUND);
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

    abstract public boolean checkCondition();

    abstract public void buttonPressed();
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
        graphics2D.setColor(Palette.getSwitchOffBackground());
        graphics2D.fillRoundRect(rectangleX, rectangleY, WIDTH, HEIGHT, ARC, ARC);
//        graphics2D.setColor(Palette.getButtonBorderColor());
//        graphics2D.setStroke(new BasicStroke(2));
        graphics2D.drawRoundRect(rectangleX, rectangleY, WIDTH, HEIGHT, ARC, ARC);
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, getAlpha()));
        graphics2D.setColor(Palette.getSwitchOnBackground());
        graphics2D.fillRoundRect(rectangleX, rectangleY, WIDTH, HEIGHT, ARC, ARC);
//        graphics2D.setColor(Palette.getButtonBorderColor());
//        graphics2D.drawRoundRect(rectangleX, rectangleY, WIDTH, HEIGHT, ARC, ARC);
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        graphics2D.setColor(new Color(255, 255, 255, alpha));
        graphics2D.fillOval((int) (rectangleX + currentPosition), rectangleY + (HEIGHT - SIZE_OF_CYCLE) / 2, SIZE_OF_CYCLE, SIZE_OF_CYCLE);
    }

    public float getCurrentPosition() {
        return currentPosition;
    }

    public int getRectangleX() {
        return rectangleX;
    }

    public int getRectangleY() {
        return rectangleY;
    }

    public float getAlpha() {
        float alpha = (currentPosition - startPosition) / (endPosition - startPosition);
        return Math.max(0, Math.min(1, alpha));
    }
}
