import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    public MenuPanel() {
        this.setOpaque(true);
        changeTheme();
        this.setBorder(new RoundedCornerBorder());
    }

    public static Color getBackgroundColor() {
        return Palette.getMenuPanelBackground();
    }

    public void changeTheme() {
        this.setBackground(Palette.getMenuPanelBackground());
        Window.clearButton.changeTheme();
        Window.modeChooser.changeTheme();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setColor(Palette.getMainPanelBackground());
        graphics2D.fillRoundRect(getWidth()/2, 0, getWidth(), getHeight(),10,10);
        graphics2D.fillRect(getWidth()/2, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}
