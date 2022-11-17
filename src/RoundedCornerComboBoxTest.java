import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import javax.accessibility.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.*;

class RoundedCornerBorder extends AbstractBorder {
    protected static final int ARC = 45;

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int r = ARC;

        Area round = new Area(new RoundRectangle2D.Float(x, y, width, height, r, r));
        Area corner = new Area(new Rectangle2D.Float(x, y, width / 2.0f, height));
        corner.subtract(round);
        g2.setColor(Palette.getMainPanelBackground());
        g2.fill(corner);

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