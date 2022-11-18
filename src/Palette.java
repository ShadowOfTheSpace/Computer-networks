import java.awt.*;

public class Palette {
    public static final Color MAIN_PANEL_DARK_BACKGROUND = Color.decode("#0F0F0F");
    public static final Color MAIN_PANEL_LIGHT_BACKGROUND = Color.WHITE;
    public static final Color MENU_PANEL_DARK_BACKGROUND = Color.DARK_GRAY;
    public static final Color MENU_PANEL_LIGHT_BACKGROUND = Color.decode("#DADADA");
    public static final Color BUTTON_DARK_BACKGROUND = MAIN_PANEL_DARK_BACKGROUND;
    public static final Color BUTTON_LIGHT_BACKGROUND = Color.decode("#A9A9A9");
    public static final Color BUTTON_DARK_HOVER_BACKGROUND = Color.decode("#272727");
    public static final Color BUTTON_LIGHT_HOVER_BACKGROUND = Color.decode("#B4B4B4");
    public static final Color BUTTON_DARK_PRESS_BACKGROUND = Color.decode("#323232");
    public static final Color BUTTON_LIGHT_PRESS_BACKGROUND = Color.decode("#BFBFBF");
    public static final Color DARK_FONT_COLOR = MAIN_PANEL_LIGHT_BACKGROUND;
    public static final Color LIGHT_FONT_COLOR = MAIN_PANEL_DARK_BACKGROUND;
    public static final Color DARK_SWITCH_THEME_BUTTON_BACKGROUND = Color.decode("#FFAA1D");
    public static final Color LIGHT_SWITCH_THEME_BUTTON_BACKGROUND = Color.decode("#27173A");

    public static final Color DARK_SWITCH_ON_BUTTON_BACKGROUND = BUTTON_DARK_BACKGROUND;
    public static final Color DARK_SWITCH_OFF_BUTTON_BACKGROUND = BUTTON_DARK_HOVER_BACKGROUND;

    public static final Color DARK_BUTTON_BORDER_COLOR = MAIN_PANEL_LIGHT_BACKGROUND;
    public static final Color LIGHT_BUTTON_BORDER_COLOR = MAIN_PANEL_DARK_BACKGROUND;

    public static final Color LIGHT_SWITCH_ON_BUTTON_BACKGROUND = BUTTON_LIGHT_BACKGROUND;
    public static final Color LIGHT_SWITCH_OFF_BUTTON_BACKGROUND =BUTTON_LIGHT_PRESS_BACKGROUND;

    public static Color getMainPanelBackground() {
        return Window.darkModeEnabled ? MAIN_PANEL_DARK_BACKGROUND : MAIN_PANEL_LIGHT_BACKGROUND;
    }

    public static Color getMenuPanelBackground() {
        return Window.darkModeEnabled ? MENU_PANEL_DARK_BACKGROUND : MENU_PANEL_LIGHT_BACKGROUND;
    }

    public static Color getButtonBackground() {
        return Window.darkModeEnabled ? BUTTON_DARK_BACKGROUND : BUTTON_LIGHT_BACKGROUND;
    }

    public static Color getButtonHoverBackground() {
        return Window.darkModeEnabled ? BUTTON_DARK_HOVER_BACKGROUND : BUTTON_LIGHT_HOVER_BACKGROUND;
    }

    public static Color getButtonPressBackground() {
        return Window.darkModeEnabled ? BUTTON_DARK_PRESS_BACKGROUND : BUTTON_LIGHT_PRESS_BACKGROUND;
    }

    public static Color getSwitchOnBackground() {
        return Window.darkModeEnabled ? DARK_SWITCH_ON_BUTTON_BACKGROUND : LIGHT_SWITCH_ON_BUTTON_BACKGROUND;
    }

    public static Color getSwitchOffBackground() {
        return Window.darkModeEnabled ? DARK_SWITCH_OFF_BUTTON_BACKGROUND : LIGHT_SWITCH_OFF_BUTTON_BACKGROUND;
    }

    public static Color getButtonBorderColor() {
        return Window.darkModeEnabled ? DARK_BUTTON_BORDER_COLOR : LIGHT_BUTTON_BORDER_COLOR;
    }

    public static Color getFontColor() {
        return Window.darkModeEnabled ? DARK_FONT_COLOR : LIGHT_FONT_COLOR;
    }
}
