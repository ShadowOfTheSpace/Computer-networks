package Program.UI.Windows.InfoWindows;

import Program.Elements.Line;
import Program.Modes.Metric;
import Program.UI.Elements.Borders.RoundedCornerBorderForPanel;
import Program.UI.Elements.Buttons.Button;
import Program.UI.Elements.TextField.TextField;
import Program.UI.Colors.Palette;
import Program.UI.Elements.Label.MenuPanelLabel;
import Program.UI.Elements.Panels.SubMenuPanel;
import Program.UI.Elements.TopBar.TopBar;
import Program.UI.Windows.MainWindow.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LineInfoWindow extends JFrame {
    int x, y;
    int WIDTH = 300, HEIGHT = 200;
    TopBar topPanel;
    Button okButton;
    TextField lengthTextField;

    public LineInfoWindow(Line line, Metric metric) {
        this.setUndecorated(true);
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.setAlwaysOnTop(true);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setLayout(new GridBagLayout());
        topPanel = new TopBar(WIDTH, "Line info");
        SubMenuPanel windowPanel = new SubMenuPanel(new GridBagLayout());

        windowPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        windowPanel.setBorder(new RoundedCornerBorderForPanel());
        topPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                x = e.getX();
                y = e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                x = e.getX();
                y = e.getY();
            }
        });
        topPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int dx = e.getX() - x, dy = e.getY() - y;
                Point oldLocation = getLocation();
                setLocation(oldLocation.x + dx, oldLocation.y + dy);
            }
        });
        JPanel mainPanel = new JPanel(null);
        mainPanel.setOpaque(false);
        JPanel infoPanel = new JPanel(new GridLayout(3, 1)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D graphics2D = (Graphics2D) g;
                graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, topPanel.getAlpha()));
                graphics2D.setColor(Palette.getMenuPanelBackground());
                graphics2D.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        SubMenuPanel upperPanel = new SubMenuPanel(new GridBagLayout());
        SubMenuPanel middlePanel = new SubMenuPanel(new GridBagLayout());
        SubMenuPanel lowerPanel = new SubMenuPanel(new GridBagLayout());
        okButton = new Button("OK", false) {
            @Override
            public void paint(Graphics g) {
                Graphics2D graphics2D = (Graphics2D) g;
                graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, topPanel.getAlpha()));
                super.paint(g);
            }

            @Override
            public float getAlpha() {
                return topPanel.getAlpha();
            }
        };
        okButton.addActionListener(e -> {
            if (!lengthTextField.getText().isEmpty()) {
                int newLength = Integer.parseInt(lengthTextField.getText());
                if (newLength > 0 && newLength <= Line.MAX_LENGTH && lengthTextField.getText().charAt(0) != '0') {
                    line.setLength(newLength);
                    closeWindow();
                }
            }
        });
        okButton.setPreferredSize(new Dimension(100, 40));
        lowerPanel.add(okButton);

        upperPanel.add(new MenuPanelLabel("Length " + line.getElementName(), JLabel.CENTER, JLabel.CENTER));
        lengthTextField = new TextField("" + line.getLength(), metric);
        middlePanel.add(lengthTextField);

        infoPanel.setBounds(2, 0, WIDTH - 4, (int) (1.5f * HEIGHT / 2));
        mainPanel.add(infoPanel);
        infoPanel.add(upperPanel);
        infoPanel.add(middlePanel);
        infoPanel.add(lowerPanel);
        windowPanel.add(topPanel, Window.getGbc(0, 0, 1, 1, 1, 0.08));
        windowPanel.add(mainPanel, Window.getGbc(0, 1, 1, 1, 1, 0.92));
        this.add(windowPanel);
        this.pack();
        this.setLocationRelativeTo(Window.getMainPanel());
        this.setLocation((int) line.getMiddlePoint().x, (int) line.getMiddlePoint().y);
        this.setVisible(true);

        lengthTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                topPanel.setAlpha(1f);
                infoPanel.repaint();
                topPanel.repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                topPanel.setAlpha(0.5f);
                infoPanel.repaint();
                topPanel.repaint();
            }
        });
    }

    public void closeWindow() {
        Window.getLineInfoWindows().remove(this);
        this.dispose();
    }

    public void changeTheme() {
        topPanel.changeTheme();
        okButton.changeTheme();
        lengthTextField.repaint();
        repaint();
    }
}
