package program.ui.windows.panels;

import program.ui.colors.Palette;
import program.ui.elements.borders.RoundedCornerBorderForMenuPanel;
import program.ui.windows.main.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuPanel extends JPanel {
    public MenuPanel() {
        this.setOpaque(true);
        changeTheme();
        this.setBorder(new RoundedCornerBorderForMenuPanel());
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Window.getMainPanel().requestFocus();
            }
        });
    }

    public static Color getBackgroundColor() {
        return Palette.getMenuPanelBackground();
    }

    public void changeTheme() {
        this.setBackground(Palette.getMenuPanelBackground());
        Window.getClearButton().changeTheme();
        Window.getModeChooser().changeTheme();
        Window.getMetricChooser().changeTheme();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setColor(Palette.getMainPanelBackground());
        graphics2D.fillRoundRect(getWidth()/2, 0, getWidth(), getHeight(),10,10);
        graphics2D.fillRect(getWidth()/2, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}
