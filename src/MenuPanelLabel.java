import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class MenuPanelLabel extends JLabel {
    public MenuPanelLabel(String text, int verticalPosition) {
        super(text, JLabel.CENTER);
        this.setVerticalAlignment(verticalPosition);
        this.setFont(new Font("Arial", Font.BOLD, 20));
        this.setOpaque(true);
        this.setBorder(null);
//        this.setPreferredSize(new Dimension(1,50));
    }

    @Override
    protected void paintComponent(Graphics g) {
        this.setForeground(Palette.getFontColor());
        this.setBackground((new Color(new Random().nextInt(256),new Random().nextInt(256),new Random().nextInt(256))));

//        this.setForeground(new Color(0,0,0,0));
        super.paintComponent(g);
    }
}
