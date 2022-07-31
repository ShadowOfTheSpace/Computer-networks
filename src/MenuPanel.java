import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    private static final Color darkBackground = Color.DARK_GRAY.brighter();
    private static final Color lightBackground = Color.decode("#BDC3D2");

    public MenuPanel() {
        changeTheme();
    }

    public void changeTheme() {
        this.setBackground(Window.darkModeEnabled ? darkBackground : lightBackground);
    }
}
