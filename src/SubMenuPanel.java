import javax.swing.*;
import java.awt.*;

public class SubMenuPanel extends JPanel {
    public SubMenuPanel() {
        super(new GridLayout(2,1));
    }

    public SubMenuPanel(LayoutManager layoutManager) {
        super(layoutManager);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setColor(MenuPanel.getBackgroundColor());
        graphics2D.fillRect(0, 0, getWidth(), getHeight());
    }
}
