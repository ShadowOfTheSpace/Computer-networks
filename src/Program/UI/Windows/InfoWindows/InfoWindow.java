package Program.UI.Windows.InfoWindows;

import Program.UI.Elements.Borders.RoundedCornerBorderForPanel;
import Program.UI.Elements.Panels.SubMenuPanel;
import Program.UI.Elements.TopBar.TopBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InfoWindow extends JFrame {
    private Point location;
    protected final TopBar topBar;
    private static final int topBarHeight = 35;
    private SubMenuPanel infoPanel;

    public InfoWindow(int width, int height, String title, Component relativeToComponent, Point startLocation) {
        this.location = startLocation;
        this.setUndecorated(true);
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.setAlwaysOnTop(true);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setLayout(new GridBagLayout());
        topBar = new TopBar(width,topBarHeight, title);
        SubMenuPanel windowPanel = new SubMenuPanel(null);
        windowPanel.setPreferredSize(new Dimension(width + 4, height + 55));
        windowPanel.setBorder(new RoundedCornerBorderForPanel());
        topBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                location = e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                location = e.getPoint();
            }
        });
        topBar.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int dx = e.getX() - location.x, dy = e.getY() - location.y;
                Point oldLocation = getLocation();
                setLocation(oldLocation.x + dx, oldLocation.y + dy);
            }
        });
        JPanel mainPanel = new JPanel(null);
        mainPanel.setOpaque(false);
        topBar.setBounds(0, 0, width + 4, topBarHeight);
        mainPanel.setBounds(0, topBarHeight, width + 4, height - topBarHeight + 55);

        SwingUtilities.invokeLater(() -> {
            infoPanel = getInfoPanel();
            infoPanel.setBounds(2, 0, width, height);
            mainPanel.add(infoPanel);
            this.setVisible(true);
        });

        windowPanel.add(topBar);
        windowPanel.add(mainPanel);
        this.add(windowPanel);


        this.pack();
        this.setLocationRelativeTo(relativeToComponent);
        this.setLocation(this.location);


        SwingUtilities.invokeLater(() -> getComponentForFocusListener().addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                topBar.setAlpha(1f);
                topBar.repaint();
                infoPanel.repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                topBar.setAlpha(0.5f);
                topBar.repaint();
                infoPanel.repaint();
            }
        }));

    }

    public Component getComponentForFocusListener() {
        return this;
    }

    public SubMenuPanel getInfoPanel() {
        return new SubMenuPanel(true) {
            @Override
            public float getAlpha() {
                return topBar.getAlpha();
            }
        };
    }

    public void closeWindow() {
        this.dispose();
    }

    public void changeTheme() {
        topBar.changeTheme();
        repaint();
    }
}
