package program.ui.images;

import program.elements.element.Status;
import program.elements.node.Node;
import program.ui.elements.buttons.SwitchButton;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ImagesAndIcons {
    private static final HashMap<Status, Image> lightModeTrustedNodesImage = new HashMap<>();
    private static final HashMap<Status, Image> darkModeTrustedNodesImage = new HashMap<>();
    private static final HashMap<Status, Image> lightModeNonTrustedNodesImage = new HashMap<>();
    private static final HashMap<Status, Image> darkModeNonTrustedNodesImage = new HashMap<>();
    private static final Image moonIcon;
    private static final Image sunIcon;
    private static final Image darkThemeMap;
    private static final Image lightThemeMap;

    static {
        try {
            lightModeTrustedNodesImage.put(Status.NONE, getImageFromFile("images/trustedNode-light.png", Node.getSizeOfNode()));
            darkModeTrustedNodesImage.put(Status.NONE, getImageFromFile("images/trustedNode-dark.png", Node.getSizeOfNode()));
            lightModeNonTrustedNodesImage.put(Status.NONE, getImageFromFile("images/nonTrustedNode-light.png", Node.getSizeOfNode()));
            darkModeNonTrustedNodesImage.put(Status.NONE, getImageFromFile("images/nonTrustedNode-dark.png", Node.getSizeOfNode()));

            lightModeTrustedNodesImage.put(Status.ACTIVE, getImageFromFile("images/trustedNode-light-active.png", Node.getSizeOfNode()));
            darkModeTrustedNodesImage.put(Status.ACTIVE, getImageFromFile("images/trustedNode-dark-active.png", Node.getSizeOfNode()));
            lightModeNonTrustedNodesImage.put(Status.ACTIVE, getImageFromFile("images/nonTrustedNode-light-active.png", Node.getSizeOfNode()));
            darkModeNonTrustedNodesImage.put(Status.ACTIVE, getImageFromFile("images/nonTrustedNode-dark-active.png", Node.getSizeOfNode()));

            lightModeTrustedNodesImage.put(Status.MOVING_NODE, getImageFromFile("images/trustedNode-light-active.png", Node.getSizeOfNode()));
            darkModeTrustedNodesImage.put(Status.MOVING_NODE, getImageFromFile("images/trustedNode-dark-active.png", Node.getSizeOfNode()));
            lightModeNonTrustedNodesImage.put(Status.MOVING_NODE, getImageFromFile("images/nonTrustedNode-light-active.png", Node.getSizeOfNode()));
            darkModeNonTrustedNodesImage.put(Status.MOVING_NODE, getImageFromFile("images/nonTrustedNode-dark-active.png", Node.getSizeOfNode()));

            lightModeTrustedNodesImage.put(Status.START_NODE, getImageFromFile("images/trustedNode-light-start.png", Node.getSizeOfNode()));
            darkModeTrustedNodesImage.put(Status.START_NODE, getImageFromFile("images/trustedNode-dark-start.png", Node.getSizeOfNode()));
            lightModeNonTrustedNodesImage.put(Status.START_NODE, getImageFromFile("images/nonTrustedNode-light-start.png", Node.getSizeOfNode()));
            darkModeNonTrustedNodesImage.put(Status.START_NODE, getImageFromFile("images/nonTrustedNode-dark-start.png", Node.getSizeOfNode()));

            lightModeTrustedNodesImage.put(Status.END_NODE, getImageFromFile("images/trustedNode-light-end.png", Node.getSizeOfNode()));
            darkModeTrustedNodesImage.put(Status.END_NODE, getImageFromFile("images/trustedNode-dark-end.png", Node.getSizeOfNode()));
            lightModeNonTrustedNodesImage.put(Status.END_NODE, getImageFromFile("images/nonTrustedNode-light-end.png", Node.getSizeOfNode()));
            darkModeNonTrustedNodesImage.put(Status.END_NODE, getImageFromFile("images/nonTrustedNode-dark-end.png", Node.getSizeOfNode()));
            darkThemeMap = ImageIO.read(new File("images/darkThemeMap.png"));
            lightThemeMap = ImageIO.read(new File("images/lightThemeMap.png"));
            moonIcon = getImageFromFile("images/moon.png", SwitchButton.SIZE_OF_CYCLE);
            sunIcon = getImageFromFile("images/sun.png", SwitchButton.SIZE_OF_CYCLE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Image getMoonIcon() {
        return moonIcon;
    }

    public static Image getSunIcon() {
        return sunIcon;
    }

    public static Image getDarkThemeMap(){
        return darkThemeMap;
    }

    public static Image getLightThemeMap(){
        return lightThemeMap;
    }

    private static Image getImageFromFile(String imageName, int imageSize) throws IOException {
        return ImageIO.read(new File(imageName)).getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
    }

    public static Image getImage(boolean darkModeEnabled, boolean trustFactor, Status status) {
        if (darkModeEnabled) {
            if (trustFactor) {
                return darkModeTrustedNodesImage.get(status);
            } else {
                return darkModeNonTrustedNodesImage.get(status);
            }
        } else {
            if (trustFactor) {
                return lightModeTrustedNodesImage.get(status);
            } else {
                return lightModeNonTrustedNodesImage.get(status);
            }
        }
    }
}
