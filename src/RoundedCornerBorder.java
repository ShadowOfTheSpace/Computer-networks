import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

class RoundedCornerBorder extends AbstractBorder {
    protected static final int ANGLE = 40;

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Area round = new Area(new RoundRectangle2D.Float(x, y + 1, width + 20, height - 3, ANGLE, ANGLE));
        Area corner = new Area(new Rectangle2D.Float(x, y, width / 2.0f, height));
        corner.subtract(round);
        g2.setColor(Palette.getMainPanelBackground());
        g2.fill(corner);
        g2.setColor(Palette.getFontColor());
        g2.setStroke(new BasicStroke(3));
        g2.drawLine(x + width - 1, 2, x + width - 1, y + height - 3);
        g2.setStroke(new BasicStroke(2));
        g2.draw(round);
        g2.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(8, 8, 8, 8);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.set(8, 8, 8, 8);
        return insets;
    }
}