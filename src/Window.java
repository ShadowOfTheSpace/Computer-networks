import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Window extends JFrame {
    static boolean darkModeEnabled = true;
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
        gridBagConstraints.weightx = 5.0 / 6.0;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        this.add(mainPanel, gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.weightx = 1.0 / 6.0;
        this.add(menuPanel, gridBagConstraints);
        this.setVisible(true);
    }

}
