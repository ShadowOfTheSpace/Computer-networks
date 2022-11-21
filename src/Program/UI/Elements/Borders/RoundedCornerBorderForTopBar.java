package Program.UI.Elements.Borders;

import Program.UI.Colors.Palette;

import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class RoundedCornerBorderForTopBar extends AbstractBorder {
    protected static final int ANGLE = 40;


    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Area round = new Area(new RoundRectangle2D.Float(x, y, width, height, ANGLE, ANGLE));
        Area corner = new Area(new Rectangle2D.Float(x, y, width, height / 2f));
        corner.subtract(round);
        g2.setColor(new Color(0, 0, 0, 0));
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN));
        g2.fill(corner);
        g2.setColor(Palette.getFontColor());
        g2.setStroke(new BasicStroke(3));
        round = new Area(new RoundRectangle2D.Float(x, y, width - 0.5f, height - 2, ANGLE, ANGLE));
        corner = new Area(new Rectangle2D.Float(x, y + height / 2f - 1, width - 1, height / 2f));
        round.add(corner);
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