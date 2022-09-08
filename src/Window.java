import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Window extends JFrame {
    static boolean darkModeEnabled = false;
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
        gridBagConstraints.weightx = 0.9;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        this.add(mainPanel, gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.weightx = 0.2;
        menuPanel.setLayout(new GridLayout(8,1));
        menuPanel.add(Box.createGlue());
        menuPanel.add(Box.createGlue());
        menuPanel.add(Box.createGlue());
        menuPanel.add(Box.createGlue());
        menuPanel.add(Box.createGlue());
        menuPanel.add(Box.createGlue());
        menuPanel.add(Box.createGlue());
        menuPanel.add(new SwitchButton());

        this.add(menuPanel, gridBagConstraints);
        this.setVisible(true);
    }

}
