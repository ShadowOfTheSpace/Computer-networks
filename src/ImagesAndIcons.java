import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ImagesAndIcons {
    private static HashMap<ElementStatus, Image> lightModeTrustedNodesImage = new HashMap<>();
    private static HashMap<ElementStatus, Image> darkModeTrustedNodesImage = new HashMap<>();
    private static HashMap<ElementStatus, Image> lightModeNonTrustedNodesImage = new HashMap<>();
    private static HashMap<ElementStatus, Image> darkModeNonTrustedNodesImage = new HashMap<>();
    private static Image moonIcon;
    private static Image sunIcon;
    private static Image darkThemeMap;
    private static Image lightThemeMap;

    static {
        try {
            lightModeTrustedNodesImage.put(ElementStatus.NONE, getImageFromFile("images/trustedNode-light.png", Node.getSizeOfNode()));
            darkModeTrustedNodesImage.put(ElementStatus.NONE, getImageFromFile("images/trustedNode-dark.png", Node.getSizeOfNode()));
            lightModeNonTrustedNodesImage.put(ElementStatus.NONE, getImageFromFile("images/nonTrustedNode-light.png", Node.getSizeOfNode()));
            darkModeNonTrustedNodesImage.put(ElementStatus.NONE, getImageFromFile("images/nonTrustedNode-dark.png", Node.getSizeOfNode()));

            lightModeTrustedNodesImage.put(ElementStatus.ACTIVE, getImageFromFile("images/trustedNode-light-active.png", Node.getSizeOfNode()));
            darkModeTrustedNodesImage.put(ElementStatus.ACTIVE, getImageFromFile("images/trustedNode-dark-active.png", Node.getSizeOfNode()));
            lightModeNonTrustedNodesImage.put(ElementStatus.ACTIVE, getImageFromFile("images/nonTrustedNode-light-active.png", Node.getSizeOfNode()));
            darkModeNonTrustedNodesImage.put(ElementStatus.ACTIVE, getImageFromFile("images/nonTrustedNode-dark-active.png", Node.getSizeOfNode()));

            lightModeTrustedNodesImage.put(ElementStatus.NODE_IS_MOVABLE, getImageFromFile("images/trustedNode-light-active.png", Node.getSizeOfNode()));
            darkModeTrustedNodesImage.put(ElementStatus.NODE_IS_MOVABLE, getImageFromFile("images/trustedNode-dark-active.png", Node.getSizeOfNode()));
            lightModeNonTrustedNodesImage.put(ElementStatus.NODE_IS_MOVABLE, getImageFromFile("images/nonTrustedNode-light-active.png", Node.getSizeOfNode()));
            darkModeNonTrustedNodesImage.put(ElementStatus.NODE_IS_MOVABLE, getImageFromFile("images/nonTrustedNode-dark-active.png", Node.getSizeOfNode()));

            lightModeTrustedNodesImage.put(ElementStatus.NODE_IS_START_NODE, getImageFromFile("images/trustedNode-light-start.png", Node.getSizeOfNode()));
            darkModeTrustedNodesImage.put(ElementStatus.NODE_IS_START_NODE, getImageFromFile("images/trustedNode-dark-start.png", Node.getSizeOfNode()));
            lightModeNonTrustedNodesImage.put(ElementStatus.NODE_IS_START_NODE, getImageFromFile("images/nonTrustedNode-light-start.png", Node.getSizeOfNode()));
            darkModeNonTrustedNodesImage.put(ElementStatus.NODE_IS_START_NODE, getImageFromFile("images/nonTrustedNode-dark-start.png", Node.getSizeOfNode()));

            lightModeTrustedNodesImage.put(ElementStatus.NODE_CAN_BE_END_NODE, getImageFromFile("images/trustedNode-light-end.png", Node.getSizeOfNode()));
            darkModeTrustedNodesImage.put(ElementStatus.NODE_CAN_BE_END_NODE, getImageFromFile("images/trustedNode-dark-end.png", Node.getSizeOfNode()));
            lightModeNonTrustedNodesImage.put(ElementStatus.NODE_CAN_BE_END_NODE, getImageFromFile("images/nonTrustedNode-light-end.png", Node.getSizeOfNode()));
            darkModeNonTrustedNodesImage.put(ElementStatus.NODE_CAN_BE_END_NODE, getImageFromFile("images/nonTrustedNode-dark-end.png", Node.getSizeOfNode()));
            darkThemeMap = ImageIO.read(new File("images/darkThemeMap.png"));
            lightThemeMap = ImageIO.read(new File("images/lightThemeMap.png"));
            moonIcon = getImageFromFile("images/moon.png",SwitchButton.SIZE_OF_CYCLE);
            sunIcon = getImageFromFile("images/sun.png",SwitchButton.SIZE_OF_CYCLE);
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

    public static Image getImage(boolean darkModeEnabled, boolean trustFactor, ElementStatus elementStatus) {
        if (darkModeEnabled) {
            if (trustFactor) {
                return darkModeTrustedNodesImage.get(elementStatus);
            } else {
                return darkModeNonTrustedNodesImage.get(elementStatus);
            }
        } else {
            if (trustFactor) {
                return lightModeTrustedNodesImage.get(elementStatus);
            } else {
                return lightModeNonTrustedNodesImage.get(elementStatus);
            }
        }
    }


}
