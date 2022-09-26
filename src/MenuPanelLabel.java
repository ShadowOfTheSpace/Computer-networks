import javax.swing.*;
import java.awt.*;

public class MenuPanelLabel extends JLabel {
    public MenuPanelLabel(String text) {
        super(text, JLabel.CENTER);
        this.setVerticalAlignment(JLabel.BOTTOM);
        this.setFont(new Font("Arial", Font.BOLD, 20));
        this.setPreferredSize(new Dimension(1,50));
    }

    @Override
    protected void paintComponent(Graphics g) {
        this.setForeground(Window.darkModeEnabled ? Color.WHITE : Color.BLACK);
        super.paintComponent(g);
    }
}
