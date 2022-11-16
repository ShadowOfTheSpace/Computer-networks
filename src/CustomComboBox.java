import javax.swing.*;
import java.awt.*;

public class CustomComboBox extends JPanel {
    public CustomComboBox() {
        this.setLayout(new GridLayout(3,1));
        this.add(new Button(""));
        Button button = new Button("");
        button.setPreferredSize(new Dimension(250,30));
        this.add(button);
//        this.add(new Button(""));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setColor(Color.RED);
        graphics2D.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
    }
}
