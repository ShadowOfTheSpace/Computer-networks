import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class Window extends JFrame {
    static boolean darkModeEnabled = false;
    static Button clearButton = new Button("Clear");
    static ComboBox<Modes> modeChooser = new ComboBox<>(new Modes[]{Modes.CREATING_NODES, Modes.CREATING_LINES, Modes.FINDING_PATH, Modes.FINDING_SHORTEST_PATH_TREE});
    static MenuPanel menuPanel = new MenuPanel();
    static MainPanel mainPanel = new MainPanel();

    public Window() {
        this.setTitle("Modified Dijkstra`s algorithm by Tkachuk Oleksandr");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.9;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        this.add(mainPanel, gridBagConstraints);
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridwidth = 10;
        gridBagConstraints.weightx = 0;
        menuPanel.setLayout(new GridLayout(6, 1));
        SubMenuPanel darkModeSwitchPanel = new SubMenuPanel();
        SubMenuPanel clearButtonPanel = new SubMenuPanel(new FlowLayout());
        SubMenuPanel modeChooserPanel = new SubMenuPanel(new FlowLayout());
        SubMenuPanel modeChooserLabelPanel = new SubMenuPanel(new GridLayout(1,1));


        menuPanel.add(Box.createGlue());
        menuPanel.add(modeChooserLabelPanel);
        menuPanel.add(modeChooserPanel);
        menuPanel.add(Box.createGlue());
        menuPanel.add(clearButtonPanel);
        menuPanel.add(darkModeSwitchPanel);
        modeChooserLabelPanel.add(new MenuPanelLabel("Current working mode"));
        modeChooserPanel.add(new CustomComboBox());
        clearButtonPanel.add(clearButton);
        darkModeSwitchPanel.add(new MenuPanelLabel("Lighting theme"));
        darkModeSwitchPanel.add(new SwitchButton());
        clearButtonPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        this.add(menuPanel, gridBagConstraints);
        this.setVisible(true);
    }

}
