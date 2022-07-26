import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MainPanel extends JPanel {
    private boolean imagesAreReady = false;
    private static Image darkThemeMap;
    private static Image lightThemeMap;
    private static final Color darkBackground = Color.DARK_GRAY;
    private static final Color lightBackground = Color.decode("#D9E1F2");
    private static int numberOfCreatedNodes = 0;
    private static int numberOfCreatedLines = 0;
    private static final ArrayList<Node> nodes = new ArrayList<>();
    private static final ArrayList<Line> lines = new ArrayList<>();
    private static Modes mode = Modes.CREATING_NODES;
    private static final HashMap<Integer, Modes> modesMap = new HashMap<>();

    static {
        modesMap.put(KeyEvent.VK_1, Modes.CREATING_NODES);
        modesMap.put(KeyEvent.VK_2, Modes.CREATING_LINES);
        modesMap.put(KeyEvent.VK_3, Modes.FINDING_PATH);
        modesMap.put(KeyEvent.VK_4, Modes.FINDING_SHORTEST_PATH_TREE);

        try {
            darkThemeMap = ImageIO.read(new File("images/darkThemeMap.png"));
            lightThemeMap = ImageIO.read(new File("images/lightThemeMap.png"));
        } catch (IOException e) {
            System.err.println("Can`t open image of map!");
        }
    }

    public MainPanel() {
        this.setFocusable(true);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    nodes.forEach(node -> node.setStatus(ElementStatus.NONE));
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    Window.darkModeEnabled = !Window.darkModeEnabled;
                    Window.menuPanel.changeTheme();
                    paintImmediately(0, 0, getWidth(), getHeight());
                } else {
                    mode = modesMap.getOrDefault(e.getKeyCode(), Modes.CREATING_NODES);
                }
                repaint();
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getX(), y = e.getY();
                if (mode == Modes.CREATING_NODES) {
                    Node currentNode = getNodeByPoint(x, y);
                    if (currentNode == null) {
                        currentNode = getNodeByStatus(ElementStatus.NODE_IS_MOVABLE);
                    }
                    if (currentNode != null && currentNode.getStatus() != ElementStatus.NONE) {
                        currentNode.setStatus(ElementStatus.NODE_IS_MOVABLE);
                        int oldX = currentNode.getX(), oldY = currentNode.getY();
                        currentNode.setCoordinates(x, y);
                        if (hasIntersection(currentNode)) {
                            currentNode.setCoordinates(oldX, oldY);
                        }
                    }
                } else if (mode == Modes.CREATING_LINES) {
                    lines
                            .stream()
                            .filter(line -> line.getStatus() == ElementStatus.LINE_STILL_DRAWING)
                            .findFirst().ifPresent(line -> line.setCursorCoordinates(x, y));
                }
                repaint();
            }
        });

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX(), y = e.getY();
                if (mode == Modes.CREATING_NODES) {
                    Node newNode = new Node(numberOfCreatedNodes, x, y, "A");
                    if (!hasIntersection(newNode)) {
                        nodes.add(newNode);
                        numberOfCreatedNodes++;
                    } else {
                        Node currentNode = getNodeByPoint(x, y);
                        if (currentNode != null && currentNode.getStatus() == ElementStatus.NONE) {
                            nodes.forEach(node -> node.setStatus(ElementStatus.NONE));
                            currentNode.setStatus(ElementStatus.ACTIVE);
                        }
                    }
                } else if (mode == Modes.CREATING_LINES) {
                    if (getNodeByPoint(x, y) != null) {
                        Line newLine = new Line(numberOfCreatedNodes, getNodeByPoint(x, y));
                        lines.add(newLine);
                    }
                }
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int x = e.getX(), y = e.getY();
                if (mode == Modes.CREATING_NODES) {
                    Node currentNode = getNodeByPoint(x, y);
                    if (currentNode != null && currentNode.getStatus() == ElementStatus.NODE_IS_MOVABLE) {
                        currentNode.setStatus(ElementStatus.ACTIVE);
                    }
                }
                if (mode == Modes.CREATING_LINES) {
                    Node currentNode = getNodeByPoint(x, y);
                    if (currentNode != null) {
                        Line currentLine = lines.stream().filter(line -> line.getStatus() == ElementStatus.LINE_STILL_DRAWING).findFirst().orElse(null);
                        if (currentLine != null) {
                            if (!currentLine.getStartNode().equals(currentNode)) {
                                currentLine.setEndNodeAndLength(currentNode, 1);
                                currentLine.setStatus(ElementStatus.NONE);
                            } else {
                                lines.remove(currentLine);
                            }
                        }
                    } else {
                        lines.removeIf(line -> line.getStatus() == ElementStatus.LINE_STILL_DRAWING);
                    }
                }
                repaint();
            }
        });
    }

    private Image getMap() {
        if (!imagesAreReady) {
            darkThemeMap = darkThemeMap.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_AREA_AVERAGING);
            lightThemeMap = lightThemeMap.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_AREA_AVERAGING);
            imagesAreReady = true;
        }
        return Window.darkModeEnabled ? darkThemeMap : lightThemeMap;
    }

    private boolean hasIntersection(Node newNode) {
        for (Node node : nodes) {
            if (newNode.isIntersect(node)) {
                return true;
            }
        }
        return false;
    }

    private Node getNodeByStatus(ElementStatus status) {
        return nodes.stream().filter(node -> node.getStatus() == status).findAny().orElse(null);
    }

    private Node getNodeByPoint(int x, int y) {
        return nodes.stream().filter(node -> node.containsPoint(x, y)).findFirst().orElse(null);
    }

    private void drawAllNodes(Graphics2D graphics2D) {
        nodes.forEach(node -> node.draw(graphics2D));
    }

    private void drawAllLines(Graphics2D graphics2D) {
        lines.forEach(line -> line.draw(graphics2D));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setColor(Window.darkModeEnabled ? darkBackground : lightBackground);
        graphics2D.fillRect(0, 0, this.getWidth(), this.getHeight());
        graphics2D.drawImage(getMap(), 0, 0, null);
        drawAllLines(graphics2D);
        drawAllNodes(graphics2D);
    }
}
