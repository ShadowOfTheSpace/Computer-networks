import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import javax.accessibility.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.*;

public class RoundedCornerComboBoxTest {
    private static final Color BACKGROUND = Color.BLACK;
    private static final Color FOREGROUND = Color.BLACK;
    private static final Color SELECTIONFOREGROUND = Color.ORANGE;

    public JComponent makeUI() {
        UIManager.put("ComboBox.foreground", FOREGROUND);
        UIManager.put("ComboBox.background", BACKGROUND);
        UIManager.put("ComboBox.selectionForeground", SELECTIONFOREGROUND);
        UIManager.put("ComboBox.selectionBackground", BACKGROUND);

        UIManager.put("ComboBox.buttonDarkShadow", BACKGROUND);
        UIManager.put("ComboBox.buttonBackground", FOREGROUND);
        UIManager.put("ComboBox.buttonHighlight", FOREGROUND);
        UIManager.put("ComboBox.buttonShadow", FOREGROUND);

        UIManager.put("ComboBox.border", new RoundedCornerBorder());
        JComboBox<String> combo1 = new JComboBox<>(makeModel());
        combo1.setUI(new BasicComboBoxUI());
        Object o = combo1.getAccessibleContext().getAccessibleChild(0);
        if (o instanceof JComponent) {
            JComponent c = (JComponent) o;
            c.setBorder(new RoundedCornerBorder());
            c.setForeground(FOREGROUND);
            c.setBackground(BACKGROUND);
        }

        return combo1;
    }

    private static DefaultComboBoxModel<String> makeModel() {
        DefaultComboBoxModel<String> m = new DefaultComboBoxModel<>();
        m.addElement("1234");
        m.addElement("5555555555555555555555");
        m.addElement("6789000000000");
        return m;
    }
}


class RoundedCornerBorder extends AbstractBorder {
    protected static final int ARC = 10;

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int r = ARC;
        int w = width - 1;
        int h = height - 1;

        Area round = new Area(new RoundRectangle2D.Float(x, y, w, h, r, r));
        if (c instanceof JPopupMenu) {
            g2.setPaint(MainPanel.getBackgroundColor());
            g2.fill(round);
        } else {
            Container parent = c.getParent();
            if (Objects.nonNull(parent)) {
                Area corner = new Area(new Rectangle2D.Float(x, y, width, height));
                corner.subtract(round);
                g2.fill(corner);
            }
        }

        g2.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(4, 8, 4, 8);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.set(4, 8, 4, 8);
        return insets;
    }
}