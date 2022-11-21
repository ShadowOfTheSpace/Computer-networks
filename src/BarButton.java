import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class BarButton extends Button {
    private final boolean isRounded;

    public BarButton(String text, boolean isRounded) {
        super(text, true);
        this.isRounded = isRounded;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, getAlpha()));
        graphics2D.setColor(currentBackgroundColor);
        Area area = new Area(new Rectangle2D.Float(0, 0, getWidth(), getHeight()));
        if (this.isRounded) {
            area = new Area(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 40, 40));
            area.add(new Area(new Rectangle2D.Float(0, 0, getWidth() / 2f, getHeight())));
            area.add(new Area(new Rectangle2D.Float(getWidth() / 2f, getHeight() / 2f, getWidth() / 2f, getHeight() / 2f)));
        }
        graphics2D.fill(area);
        drawText(graphics2D);
    }
}
