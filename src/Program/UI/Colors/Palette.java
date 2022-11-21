package Program.UI.Colors;

import Program.UI.Windows.MainWindow.Window;

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
        public static final Color LIGHT_SWITCH_ON_BUTTON_BACKGROUND = BUTTON_LIGHT_BACKGROUND;
    public static final Color LIGHT_SWITCH_OFF_BUTTON_BACKGROUND = BUTTON_LIGHT_PRESS_BACKGROUND;

    public static final Color LINE_LIGHT_COLOR_NONE = MAIN_PANEL_DARK_BACKGROUND;
    public static final Color LINE_LIGHT_COLOR_TRUSTFUL = Color.GREEN;
    public static final Color LINE_LIGHT_COLOR_NON_TRUSTFUL = Color.RED;

    public static final Color LINE_DARK_COLOR_NONE = MAIN_PANEL_LIGHT_BACKGROUND;
    public static final Color LINE_DARK_COLOR_TRUSTFUL = Color.GREEN.darker();
    public static final Color LINE_DARK_COLOR_NON_TRUSTFUL = Color.RED.darker();
    public static final Color LINE_COLOR_ACTIVE = Color.ORANGE;
    public static final Color LINE_COLOR_NOT_PART_OF_TREE = new Color(127,127,127, 50);

    public static Color getLineNoneColor() {
        return Window.isDarkModeEnabled() ? LINE_DARK_COLOR_NONE : LINE_LIGHT_COLOR_NONE;
    }

    public static Color getLineTrustfulColor() {
        return Window.isDarkModeEnabled() ? LINE_DARK_COLOR_TRUSTFUL : LINE_LIGHT_COLOR_TRUSTFUL;
    }

    public static Color getLineNonTrustfulColor() {
        return Window.isDarkModeEnabled() ? LINE_DARK_COLOR_NON_TRUSTFUL : LINE_LIGHT_COLOR_NON_TRUSTFUL;
    }

    public static Color getLineColorWithTrustFactor(boolean trustFactor) {
        return trustFactor ? getLineTrustfulColor() : getLineNonTrustfulColor();
    }

    public static Color getMainPanelBackground() {
        return Window.isDarkModeEnabled() ? MAIN_PANEL_DARK_BACKGROUND : MAIN_PANEL_LIGHT_BACKGROUND;
    }

    public static Color getMenuPanelBackground() {
        return Window.isDarkModeEnabled() ? MENU_PANEL_DARK_BACKGROUND : MENU_PANEL_LIGHT_BACKGROUND;
    }

    public static Color getButtonBackground() {
        return Window.isDarkModeEnabled() ? BUTTON_DARK_BACKGROUND : BUTTON_LIGHT_BACKGROUND;
    }

    public static Color getButtonHoverBackground() {
        return Window.isDarkModeEnabled() ? BUTTON_DARK_HOVER_BACKGROUND : BUTTON_LIGHT_HOVER_BACKGROUND;
    }

    public static Color getButtonPressBackground() {
        return Window.isDarkModeEnabled() ? BUTTON_DARK_PRESS_BACKGROUND : BUTTON_LIGHT_PRESS_BACKGROUND;
    }

    public static Color getSwitchOnBackground() {
        return Window.isDarkModeEnabled() ? DARK_SWITCH_ON_BUTTON_BACKGROUND : LIGHT_SWITCH_ON_BUTTON_BACKGROUND;
    }

    public static Color getSwitchOffBackground() {
        return Window.isDarkModeEnabled() ? DARK_SWITCH_OFF_BUTTON_BACKGROUND : LIGHT_SWITCH_OFF_BUTTON_BACKGROUND;
    }

    public static Color getFontColor() {
        return Window.isDarkModeEnabled() ? DARK_FONT_COLOR : LIGHT_FONT_COLOR;
    }
}
