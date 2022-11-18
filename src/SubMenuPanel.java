import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Random;

public class SubMenuPanel extends JPanel {
    public SubMenuPanel() {
//        super(new GridLayout(2,1));
        this.setBorder(null);
    }

    public SubMenuPanel(LayoutManager layoutManager) {
        this.setLayout(layoutManager);

        this.setBorder(null);
//        this.setBorder(new EmptyBorder(0,20,0,0));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
//        graphics2D.setColor(new Color(new Random().nextInt(256),new Random().nextInt(256),new Random().nextInt(256)));
        graphics2D.setColor(MenuPanel.getBackgroundColor());
        graphics2D.fillRect(0, 0, getWidth(), getHeight());
    }
}
