import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class MenuPanel extends JPanel {
    private static final Color darkBackground = Color.DARK_GRAY.brighter();
    private static final Color lightBackground = Color.decode("#BDC3D2");

    public MenuPanel() {

        changeTheme();
    }
        public static Color getBackgroundColor(){
        return Window.darkModeEnabled ? darkBackground : lightBackground;
    }
    public void changeTheme() {
        this.setBackground(Window.darkModeEnabled ? darkBackground : lightBackground);
        Window.clearButton.ChangeTheme();
    }
}
