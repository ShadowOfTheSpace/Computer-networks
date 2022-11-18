import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Window extends JFrame {
    static boolean darkModeEnabled = false;
    static boolean fullScreenDisabled = false;
    static Button clearButton = new Button("Clear");
    static CustomComboBox modeChooser = new CustomComboBox(new Modes[]{Modes.CREATING_NODES, Modes.CREATING_LINES, Modes.FINDING_PATH, Modes.FINDING_SHORTEST_PATH_TREE});
    static CustomComboBox metricChooser = new CustomComboBox(new Modes[]{Modes.CREATING_NODES, Modes.CREATING_LINES, Modes.FINDING_PATH});
    static MenuPanel menuPanel = new MenuPanel();
    static MainPanel mainPanel = new MainPanel();

    public Window() {
        this.setTitle("Modified Dijkstra`s algorithm by Tkachuk Oleksandr");
        this.setUndecorated(true);
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
        menuPanel.setLayout(new GridBagLayout());

        SubMenuPanel metricPanel = new SubMenuPanel(new GridBagLayout());
        SubMenuPanel modePanel = new SubMenuPanel(new GridBagLayout());
        SubMenuPanel switchPanel = new SubMenuPanel();

        menuPanel.add(metricPanel, getGbc(0, 0, 2, 1, 1,0.3));
        menuPanel.add(modePanel, getGbc(0, 2, 3, 1, 1,0.35));
        menuPanel.add(switchPanel, getGbc(0, 5, 2, 1, 1,0.35));

        switchPanel.setLayout(new GridBagLayout());

        SubMenuPanel clearPanel = new SubMenuPanel(new GridBagLayout());
        SubMenuPanel gridPanel = new SubMenuPanel(new GridLayout(2,1));
        SubMenuPanel themePanel = new SubMenuPanel(new GridLayout(2,1));

        clearPanel.add(clearButton);
        gridPanel.add(new MenuPanelLabel("Grid",JLabel.BOTTOM,JLabel.CENTER));
        gridPanel.add(new SwitchButton());
//
        themePanel.add(new MenuPanelLabel("Theme",JLabel.BOTTOM,JLabel.CENTER));
        themePanel.add(new SwitchButton());

        switchPanel.add(clearPanel,getGbc(0,0,1,4,1,0.33));
        switchPanel.add(gridPanel,getGbc(0,1,2,2,0.5,0.66));
        switchPanel.add(themePanel,getGbc(2,1,2,2,0.5,0.66));


        SubMenuPanel metricLabelPanel = new SubMenuPanel(new GridBagLayout());
        SubMenuPanel metricChooserPanel = new SubMenuPanel(new GridBagLayout());
        metricPanel.add(metricLabelPanel,getGbc(0,0,1,1,1,0.2));
        metricPanel.add(metricChooserPanel,getGbc(0,1,2,1,1,0.8));


        SubMenuPanel modeLabelPanel = new SubMenuPanel(new GridBagLayout());
        SubMenuPanel modeChooserPanel = new SubMenuPanel(new GridBagLayout());
        modePanel.add(modeLabelPanel,getGbc(0,0,1,1,1,0.2));
        modePanel.add(modeChooserPanel,getGbc(0,1,2,1,1,0.8));


        metricLabelPanel.add(new MenuPanelLabel("Metric mode",JLabel.CENTER,JLabel.CENTER));
        metricChooserPanel.add(metricChooser);

        modeLabelPanel.add(new MenuPanelLabel("Mode",JLabel.CENTER,JLabel.CENTER));
        modeChooserPanel.add(modeChooser);





//        SubMenuPanel jPanel = new SubMenuPanel();
//        jPanel.setLayout(new GridBagLayout());
//        jPanel.add();
//        clearPanel.add(clearButton);
//        clearButtonPanel.add(clearButton);
//        gridSwitchPanel.add(new MenuPanelLabel("Grid", JLabel.CENTER));
//        gridSwitchPanel.add(new SwitchButton());
        this.add(menuPanel, gridBagConstraints);


//        clearButtonPanel.add(clearButton);
//        gridSwitchPanel.add(clearButton);
//        themeSwitchPanel.add(clearButton);




//        SubMenuPanel darkModeSwitchPanel = new SubMenuPanel(new GridLayout(3,1));
//        SubMenuPanel clearButtonPanel = new SubMenuPanel(new GridBagLayout());
//        SubMenuPanel modeChooserPanel = new SubMenuPanel(new GridBagLayout());
//        SubMenuPanel modeChooserLabelPanel = new SubMenuPanel(new GridLayout(3, 1));


//        menuPanel.add(Box.createGlue());
//        menuPanel.add(modeChooserLabelPanel);
//        menuPanel.add(modeChooserPanel);
////        menuPanel.add(clearButtonPanel);
//        menuPanel.add(darkModeSwitchPanel);
//        modeChooserLabelPanel.add(new MenuPanelLabel("Current working mode", JLabel.CENTER));
////        modeChooserPanel.add(modeChooser);
////        modeChooserPanel.add(clearButton);
////        clearButtonPanel.add(Box.createGlue());
////        clearButtonPanel.add(Box.createGlue());
////        clearButtonPanel.add(clearButton);
////        clearButtonPanel.add();
//        modeChooserLabelPanel.add(new MenuPanelLabel("Current working mode", JLabel.CENTER));
////        darkModeSwitchPanel.add(Box.createGlue());
//        darkModeSwitchPanel.add(Box.createGlue());
//        darkModeSwitchPanel.add(clearButton);
//        darkModeSwitchPanel.add(new MenuPanelLabel("Grid", JLabel.CENTER));
//        darkModeSwitchPanel.add(new SwitchButton());
//        darkModeSwitchPanel.add(new MenuPanelLabel("Theme", JLabel.CENTER));
//        darkModeSwitchPanel.add(new SwitchButton());
//        clearButtonPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

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
