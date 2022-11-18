import java.awt.*;

public class Palette {
    public static final Color MAIN_PANEL_DARK_BACKGROUND = Color.decode("#0F0F0F").brighter();
    public static final Color MAIN_PANEL_LIGHT_BACKGROUND = Color.decode("#D9E1F2");
    public static final Color MENU_PANEL_DARK_BACKGROUND = Color.DARK_GRAY.brighter();
    public static final Color MENU_PANEL_LIGHT_BACKGROUND = Color.decode("#BDC3D2");
    public static final Color BUTTON_DARK_BACKGROUND = MAIN_PANEL_DARK_BACKGROUND;
    public static final Color BUTTON_LIGHT_BACKGROUND = MAIN_PANEL_LIGHT_BACKGROUND;
    public static final Color BUTTON_DARK_HOVER_BACKGROUND = Color.decode("#3F3F3F");
    public static final Color BUTTON_LIGHT_HOVER_BACKGROUND = Color.decode("#FFAA1D");
    public static final Color BUTTON_DARK_PRESS_BACKGROUND = Color.decode("#3F3F3F");
    public static final Color BUTTON_LIGHT_PRESS_BACKGROUND = Color.decode("#FFAA1D");
    public static final Color DARK_FONT_COLOR = Color.WHITE;
    public static final Color LIGHT_FONT_COLOR = Color.BLACK;
    public static final Color DARK_SWITCH_BUTTON_BACKGROUND = Color.decode("#FFAA1D");
    public static final Color LIGHT_SWITCH_BUTTON_BACKGROUND = Color.decode("#27173A");

    public static Color getMainPanelBackground(){
        return Window.darkModeEnabled ? MAIN_PANEL_DARK_BACKGROUND:MAIN_PANEL_LIGHT_BACKGROUND;
    }

    public static Color getMenuPanelBackground(){
        return Window.darkModeEnabled ? MENU_PANEL_DARK_BACKGROUND:MENU_PANEL_LIGHT_BACKGROUND;
    }

    public static Color getButtonBackground(){
        return Window.darkModeEnabled ? BUTTON_DARK_BACKGROUND:BUTTON_LIGHT_BACKGROUND;
    }
    public static Color getButtonHoverBackground(){
        return Window.darkModeEnabled ? BUTTON_DARK_HOVER_BACKGROUND:BUTTON_LIGHT_HOVER_BACKGROUND;
    }

    public static Color getButtonPressBackground(){
        return Window.darkModeEnabled ? BUTTON_DARK_PRESS_BACKGROUND:BUTTON_LIGHT_PRESS_BACKGROUND;
    }

    public static Color getInverseMainPanelBackground(){
        return Window.darkModeEnabled ? MAIN_PANEL_LIGHT_BACKGROUND:MAIN_PANEL_DARK_BACKGROUND;
    }
    public static Color getFontColor(){
        return Window.darkModeEnabled ? DARK_FONT_COLOR:LIGHT_FONT_COLOR;
    }
}
