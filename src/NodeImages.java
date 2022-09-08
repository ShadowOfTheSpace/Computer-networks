import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class NodeImages {
    private static HashMap<ElementStatus, Image> lightModeTrustedNodesImage = new HashMap<>();
    private static HashMap<ElementStatus, Image> darkModeTrustedNodesImage = new HashMap<>();
    private static HashMap<ElementStatus, Image> lightModeNonTrustedNodesImage = new HashMap<>();
    private static HashMap<ElementStatus, Image> darkModeNonTrustedNodesImage = new HashMap<>();

    static {
        try {
            lightModeTrustedNodesImage.put(ElementStatus.NONE, getImageForNode("images/trustedNode-light.png", Node.getSizeOfNode()));
            darkModeTrustedNodesImage.put(ElementStatus.NONE, getImageForNode("images/trustedNode-dark.png", Node.getSizeOfNode()));
            lightModeNonTrustedNodesImage.put(ElementStatus.NONE, getImageForNode("images/nonTrustedNode-light.png", Node.getSizeOfNode()));
            darkModeNonTrustedNodesImage.put(ElementStatus.NONE, getImageForNode("images/nonTrustedNode-dark.png", Node.getSizeOfNode()));

            lightModeTrustedNodesImage.put(ElementStatus.ACTIVE, getImageForNode("images/trustedNode-light-active.png", Node.getSizeOfNode()));
            darkModeTrustedNodesImage.put(ElementStatus.ACTIVE, getImageForNode("images/trustedNode-dark-active.png", Node.getSizeOfNode()));
            lightModeNonTrustedNodesImage.put(ElementStatus.ACTIVE, getImageForNode("images/nonTrustedNode-light-active.png", Node.getSizeOfNode()));
            darkModeNonTrustedNodesImage.put(ElementStatus.ACTIVE, getImageForNode("images/nonTrustedNode-dark-active.png", Node.getSizeOfNode()));

            lightModeTrustedNodesImage.put(ElementStatus.NODE_IS_MOVABLE, getImageForNode("images/trustedNode-light-active.png", Node.getSizeOfNode()));
            darkModeTrustedNodesImage.put(ElementStatus.NODE_IS_MOVABLE, getImageForNode("images/trustedNode-dark-active.png", Node.getSizeOfNode()));
            lightModeNonTrustedNodesImage.put(ElementStatus.NODE_IS_MOVABLE, getImageForNode("images/nonTrustedNode-light-active.png", Node.getSizeOfNode()));
            darkModeNonTrustedNodesImage.put(ElementStatus.NODE_IS_MOVABLE, getImageForNode("images/nonTrustedNode-dark-active.png", Node.getSizeOfNode()));

            lightModeTrustedNodesImage.put(ElementStatus.NODE_IS_START_NODE, getImageForNode("images/trustedNode-light-start.png", Node.getSizeOfNode()));
            darkModeTrustedNodesImage.put(ElementStatus.NODE_IS_START_NODE, getImageForNode("images/trustedNode-dark-start.png", Node.getSizeOfNode()));
            lightModeNonTrustedNodesImage.put(ElementStatus.NODE_IS_START_NODE, getImageForNode("images/nonTrustedNode-light-start.png", Node.getSizeOfNode()));
            darkModeNonTrustedNodesImage.put(ElementStatus.NODE_IS_START_NODE, getImageForNode("images/nonTrustedNode-dark-start.png", Node.getSizeOfNode()));

            lightModeTrustedNodesImage.put(ElementStatus.NODE_CAN_BE_END_NODE, getImageForNode("images/trustedNode-light-end.png", Node.getSizeOfNode()));
            darkModeTrustedNodesImage.put(ElementStatus.NODE_CAN_BE_END_NODE, getImageForNode("images/trustedNode-dark-end.png", Node.getSizeOfNode()));
            lightModeNonTrustedNodesImage.put(ElementStatus.NODE_CAN_BE_END_NODE, getImageForNode("images/nonTrustedNode-light-end.png", Node.getSizeOfNode()));
            darkModeNonTrustedNodesImage.put(ElementStatus.NODE_CAN_BE_END_NODE, getImageForNode("images/nonTrustedNode-dark-end.png", Node.getSizeOfNode()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Image getImageForNode(String imageName, int imageSize) throws IOException {
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
