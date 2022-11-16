import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.basic.*;
import javax.swing.plaf.metal.MetalComboBoxButton;
import javax.swing.plaf.metal.MetalComboBoxIcon;
import javax.swing.plaf.metal.MetalComboBoxUI;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Objects;

public class ComboBox<E> extends JComboBox<E> {

    public ComboBox(E[] items) {
        super(items);
        this.setPreferredSize(new Dimension(250, 60));
        this.setFont(new Font("Arial", Font.BOLD, 24));
        this.setBorder(null);
        this.setFocusable(false);
        this.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainPanel.mode = (Modes) getSelectedItem();
            }
        });
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                repaint();
            }
        });
        this.setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                return null;
            }
            @Override
            protected ItemListener createItemListener(){
                return e -> System.out.println("sdfsdf");
            }
            @Override
            protected ComboPopup createPopup() {

                BasicComboPopup basicComboPopup = new BasicComboPopup(comboBox);
                basicComboPopup.setBorder(new RoundedCornerBorder());
                basicComboPopup.setBackground(Color.GREEN);
                return basicComboPopup;
            }
        });

//        Object o = this.getAccessibleContext().getAccessibleChild(1);
//        if (o instanceof JComponent) {
//            JComponent c = (JComponent) o;
//            c.setBorder(new RoundedCornerBorder());
//        }
        this.setRenderer(new DefaultListCellRenderer() {
            @Override
            public void paint(Graphics g) {
                Graphics2D graphics2D= (Graphics2D) g;
                this.setHorizontalAlignment(JLabel.CENTER);
                this.setBackground(MainPanel.getBackgroundColor());
                this.setForeground(Window.darkModeEnabled ? Color.WHITE : Color.BLACK);
//                graphics2D.setColor(new Color(12,223,234,0));
//                graphics2D.fillRoundRect(0,0,getWidth(),getHeight(),10,10);
                super.paint(g);
            }
        });

//        this.
//        ((JLabel) this.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setColor(MainPanel.getBackgroundColor());
        graphics2D.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        graphics2D.setColor(Window.darkModeEnabled ? Color.WHITE : Color.BLACK);
        graphics2D.setFont(new Font("Arial", Font.BOLD, 24));
        FontMetrics fm = getFontMetrics(graphics2D.getFont());
        int x = ((getWidth() - fm.stringWidth(Objects.requireNonNull(getSelectedItem()).toString())) / 2);
        int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
        graphics2D.drawString(getSelectedItem().toString(), x, y);
        int[] pointsX = {getWidth() - 30, getWidth() - 20, getWidth() - 20};
        int[] pointsY = {getHeight() / 2, getHeight() / 2 + 8, getHeight() / 2 - 8};
        Polygon polygon = new Polygon(pointsX, pointsY, 3);
        int center_x = Arrays.stream(polygon.xpoints).sum() / 3, center_y = Arrays.stream(polygon.ypoints).sum() / 3;
        if (isPopupVisible()) {
            graphics2D.rotate(Math.toRadians(-90), center_x, center_y);
        }
        graphics2D.fill(polygon);
        graphics2D.dispose();
    }
}
