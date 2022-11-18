import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    static boolean darkModeEnabled = true;
    static boolean fullScreenEnabled = false;
    static Button clearButton = new Button("Clear");
    static CustomComboBox modeChooser = new CustomComboBox(new Modes[]{Modes.CREATING_NODES, Modes.CREATING_LINES, Modes.FINDING_PATH, Modes.FINDING_SHORTEST_PATH_TREE});
    static CustomComboBox metricChooser = new CustomComboBox(new Modes[]{Modes.CREATING_NODES, Modes.CREATING_LINES, Modes.FINDING_PATH});
    static MenuPanel menuPanel = new MenuPanel();
    static MainPanel mainPanel = new MainPanel();

    public Window() {
        this.setTitle("Modified Dijkstra`s algorithm by Tkachuk Oleksandr");
        this.setUndecorated(fullScreenEnabled);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);


        this.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.weightx = 0.95;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        this.add(mainPanel, gridBagConstraints);
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.weightx = 0.05;
        this.add(menuPanel, gridBagConstraints);

        clearButton.addActionListener((event) -> mainPanel.clear());


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
        gridSwitchPanel.add(new SwitchButton(Palette.BUTTON_DARK_BACKGROUND, Palette.BUTTON_LIGHT_BACKGROUND, 255, MainPanel.isGridVisible()) {
            @Override
            public boolean checkCondition() {
                return MainPanel.isGridVisible();
            }

            @Override
            public void buttonPressed() {
                MainPanel.setGridVisible(!MainPanel.isGridVisible());
                mainPanel.repaint();
            }
        });

        mapSwitchPanel.add(new MenuPanelLabel("Map", JLabel.BOTTOM, JLabel.CENTER));
        mapSwitchPanel.add(new SwitchButton(Palette.BUTTON_DARK_BACKGROUND, Palette.BUTTON_LIGHT_BACKGROUND, 255, MainPanel.isMapVisible()) {
            @Override
            public boolean checkCondition() {
                return MainPanel.isMapVisible();
            }

            @Override
            public void buttonPressed() {
                MainPanel.setMapVisible(!MainPanel.isMapVisible());
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

    private GridBagConstraints getGbc(int x, int y, int height, int width, double weightX, double weightY) {
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
