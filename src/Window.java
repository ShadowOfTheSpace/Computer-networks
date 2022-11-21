import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Window extends JFrame {
    static boolean darkModeEnabled = true;
    static boolean fullScreenEnabled = false;
    static Button clearButton;
    static CustomComboBox modeChooser;
    static CustomComboBox metricChooser;
    static MenuPanel menuPanel;
    static MainPanel mainPanel;
    static ArrayList<LineInfoWindow> lineInfoWindows = new ArrayList<>();

    public Window() {
        this.setTitle("Modified Dijkstra`s algorithm by Tkachuk Oleksandr");
        this.setUndecorated(fullScreenEnabled);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLayout(new GridBagLayout());
        clearButton = new Button("Clear",false) {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                Window.mainPanel.requestFocus();
            }
        };
        clearButton.setPreferredSize(new Dimension(250, 60));
        clearButton.addActionListener((event) -> mainPanel.reset());


        mainPanel = new MainPanel();

        modeChooser = new CustomComboBox(Mode.CREATING_NODES, Mode.CREATING_LINES, Mode.FINDING_PATH, Mode.FINDING_TREE) {
            @Override
            public void itemChanged() {
                mainPanel.setMode((Mode) getSelectedItem());
            }
        };
        metricChooser = new CustomComboBox(Metric.HOPS, Metric.CONGESTION, Metric.DISTANCE) {
            @Override
            public void itemChanged() {
                mainPanel.setMetric((Metric) getSelectedItem());
            }
        };
        menuPanel = new MenuPanel();

//        this.add(new TopBar(1700, ""),getGbc(0,0,1,10,1,0.1));
        this.add(mainPanel, getGbc(0, 1, 1, 8, 0.95, 1));
        this.add(menuPanel, getGbc(8, 1, 1, 2, 0.06, 1));


        menuPanel.setLayout(new GridBagLayout());

        SubMenuPanel metricPanel = new SubMenuPanel(new GridBagLayout());
        SubMenuPanel modePanel = new SubMenuPanel(new GridBagLayout());
        SubMenuPanel switchPanel = new SubMenuPanel();

        menuPanel.add(metricPanel, getGbc(0, 0, 2, 1, 1, 0.35));
        menuPanel.add(modePanel, getGbc(0, 2, 3, 1, 1, 0.25));
        menuPanel.add(switchPanel, getGbc(0, 5, 2, 1, 1, 0.4));

        switchPanel.setLayout(new GridBagLayout());

        SubMenuPanel clearPanel = new SubMenuPanel(new GridBagLayout());
        SubMenuPanel gridSwitchPanel = new SubMenuPanel(new GridLayout(2, 1));
        SubMenuPanel mapSwitchPanel = new SubMenuPanel(new GridLayout(2, 1));
        SubMenuPanel themeSwitchPanel = new SubMenuPanel(new GridLayout(2, 1));

        clearPanel.add(clearButton);

        gridSwitchPanel.add(new MenuPanelLabel("Grid", JLabel.BOTTOM, JLabel.CENTER));
        gridSwitchPanel.add(new SwitchButton(Palette.BUTTON_DARK_BACKGROUND, Palette.BUTTON_LIGHT_BACKGROUND, 255, mainPanel.isGridVisible()) {
            @Override
            public boolean checkCondition() {
                return mainPanel.isGridVisible();
            }

            @Override
            public void buttonPressed() {
                mainPanel.setGridVisible(!mainPanel.isGridVisible());
                mainPanel.repaint();
            }
        });

        mapSwitchPanel.add(new MenuPanelLabel("Map", JLabel.BOTTOM, JLabel.CENTER));
        mapSwitchPanel.add(new SwitchButton(Palette.BUTTON_DARK_BACKGROUND, Palette.BUTTON_LIGHT_BACKGROUND, 255, mainPanel.isMapVisible()) {
            @Override
            public boolean checkCondition() {
                return mainPanel.isMapVisible();
            }

            @Override
            public void buttonPressed() {
                mainPanel.setMapVisible(!mainPanel.isMapVisible());
                mainPanel.repaint();
            }
        });

        themeSwitchPanel.add(new MenuPanelLabel("Theme", JLabel.BOTTOM, JLabel.CENTER));
        themeSwitchPanel.add(new SwitchButton(Palette.DARK_SWITCH_THEME_BUTTON_BACKGROUND, Palette.LIGHT_SWITCH_THEME_BUTTON_BACKGROUND, 30, darkModeEnabled) {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Graphics2D graphics2D = (Graphics2D) g;
                graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, getAlpha()));
                graphics2D.drawImage(ImagesAndIcons.getMoonIcon(), (int) (getRectangleX() + getCurrentPosition()), getRectangleY() + (SwitchButton.HEIGHT - SwitchButton.SIZE_OF_CYCLE) / 2, null);
                graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1 - getAlpha()));
                graphics2D.drawImage(ImagesAndIcons.getSunIcon(), (int) (getRectangleX() + getCurrentPosition()), getRectangleY() + (SwitchButton.HEIGHT - SwitchButton.SIZE_OF_CYCLE) / 2, null);
            }

            @Override
            public boolean checkCondition() {
                return darkModeEnabled;
            }

            @Override
            public void buttonPressed() {
                Window.darkModeEnabled = !Window.darkModeEnabled;
                Window.menuPanel.changeTheme();
                Window.mainPanel.paintImmediately(0, 0, Window.mainPanel.getWidth(), Window.mainPanel.getHeight());
                Window.lineInfoWindows.forEach(LineInfoWindow::changeTheme);
            }
        });

        switchPanel.add(clearPanel, getGbc(0, 0, 1, 4, 1, 0.2));
        switchPanel.add(gridSwitchPanel, getGbc(0, 1, 2, 2, 0.5, 0.4));
        switchPanel.add(mapSwitchPanel, getGbc(2, 1, 2, 2, 0.5, 0.4));
        switchPanel.add(themeSwitchPanel, getGbc(0, 3, 1, 4, 0.5, 0.4));


        SubMenuPanel metricLabelPanel = new SubMenuPanel(new GridLayout());
        SubMenuPanel metricChooserPanel = new SubMenuPanel();

        metricPanel.add(new SubMenuPanel(), getGbc(0, 0, 1, 1, 1, 0.3));
        metricPanel.add(metricLabelPanel, getGbc(0, 1, 1, 1, 1, 0.2));
        metricPanel.add(metricChooserPanel, getGbc(0, 2, 2, 1, 1, 0.6));


        SubMenuPanel modeLabelPanel = new SubMenuPanel(new GridLayout());
        SubMenuPanel modeChooserPanel = new SubMenuPanel();
        modePanel.add(modeLabelPanel, getGbc(0, 0, 1, 1, 1, 0.2));
        modePanel.add(modeChooserPanel, getGbc(0, 1, 2, 1, 1, 0.8));


        metricLabelPanel.add(new MenuPanelLabel("Metric mode", JLabel.CENTER, JLabel.CENTER));
        metricChooserPanel.add(metricChooser);

        modeLabelPanel.add(new MenuPanelLabel("Mode", JLabel.CENTER, JLabel.CENTER));
        modeChooserPanel.add(modeChooser);


        this.setVisible(true);
    }

    public static GridBagConstraints getGbc(int x, int y, int height, int width, double weightX, double weightY) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridheight = height;
        gbc.gridwidth = width;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = weightX;
        gbc.weighty = weightY;
        return gbc;
    }

}
