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
    private static final Color DARK_BACKGROUND = Color.DARK_GRAY;
    private static final Color LIGHT_BACKGROUND = Color.decode("#D9E1F2");
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
                    setStatusForAllElements(ElementStatus.NONE);
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    Window.darkModeEnabled = !Window.darkModeEnabled;
                    Window.menuPanel.changeTheme();
                    paintImmediately(0, 0, getWidth(), getHeight());
                } else if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    Node nodeToDelete = getNodeOrNullByStatus(ElementStatus.ACTIVE);
                    Line lineToDelete = getLineOrNullByStatus(ElementStatus.ACTIVE);
                    if (nodeToDelete != null) {
                        nodeToDelete.getLines().forEach(line -> deleteLine(line));
                        nodes.remove(nodeToDelete);
                    } else if (lineToDelete != null) {
                        deleteLine(lineToDelete);
                    }

                } else {
                    mode = modesMap.getOrDefault(e.getKeyCode(), Modes.CREATING_NODES);
                    Line line = getLineOrNullByStatus(ElementStatus.LINE_STILL_DRAWING);
                    if (line != null) {
                        lines.removeIf(line1 -> line1.getId() == line.getId());
                    }
                    setStatusForAllElements(ElementStatus.NONE);
                }
                repaint();
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getX(), y = e.getY();
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if (mode == Modes.CREATING_NODES) {
                        Node currentNode = getNodeByPoint(x, y);
                        if (currentNode == null) {
                            currentNode = getNodeOrNullByStatus(ElementStatus.NODE_IS_MOVABLE);
                        }
                        if (currentNode != null && !currentNode.hasStatus(ElementStatus.NONE)) {
                            currentNode.setElementStatus(ElementStatus.NODE_IS_MOVABLE);
                            int oldX = currentNode.getX(), oldY = currentNode.getY();
                            currentNode.setCoordinates(x, y);
                            if (hasIntersection(currentNode)) {
                                currentNode.setCoordinates(oldX, oldY);
                            }
                        }
                    } else if (mode == Modes.CREATING_LINES) {
                        Line currentLine = getLineOrNullByStatus(ElementStatus.LINE_STILL_DRAWING);
                        if (currentLine != null) {
                            currentLine.setCursorCoordinates(x, y);
                            Node currentNode = getNodeByPoint(x, y);
                            if (currentNode != null && !currentNode.hasStatus(ElementStatus.NODE_IS_START_NODE)) {
                                currentNode.setElementStatus(ElementStatus.NODE_CAN_BE_END_NODE);
                            } else {
                                currentNode = getNodeOrNullByStatus(ElementStatus.NODE_CAN_BE_END_NODE);
                                if (currentNode != null) {
                                    currentNode.setElementStatus(ElementStatus.NONE);
                                }
                            }
                        }
                    }
                    repaint();
                }
            }
        });

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX(), y = e.getY();
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if (mode == Modes.CREATING_NODES) {
                        Node activeNode = getNodeOrNullByStatus(ElementStatus.ACTIVE);
                        if (activeNode == null || getNodeByPoint(x, y) != null) {
                            Node newNode = new Node(numberOfCreatedNodes + 1, x, y);
                            if (!hasIntersection(newNode)) {
                                nodes.add(newNode);
                                numberOfCreatedNodes++;
                            } else {
                                Node currentNode = getNodeByPoint(x, y);
                                if (currentNode != null && currentNode.hasStatus(ElementStatus.NONE)) {
                                    makeElementActive(currentNode);
                                }
                            }
                        } else {
                            activeNode.setElementStatus(ElementStatus.NONE);
                        }
                    } else if (mode == Modes.CREATING_LINES) {
                        Node currentNode = getNodeByPoint(x, y);
                        Line currentLine = getLineByPoint(x, y);
                        if (currentNode != null) {
                            currentNode.setElementStatus(ElementStatus.NODE_IS_START_NODE);
                            Line newLine = new Line(numberOfCreatedLines, currentNode);
                            lines.add(newLine);
                            numberOfCreatedLines++;
                        } else if (currentLine != null && currentLine.hasStatus(ElementStatus.NONE)) {
                            makeElementActive(currentLine);
                        }
                    }
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int x = e.getX(), y = e.getY();
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if (mode == Modes.CREATING_NODES) {
                        Node currentNode = getNodeByPoint(x, y);
                        if (currentNode != null && currentNode.hasStatus(ElementStatus.NODE_IS_MOVABLE)) {
                            currentNode.setElementStatus(ElementStatus.ACTIVE);
                        }
                    }
                    if (mode == Modes.CREATING_LINES) {
                        Node currentNode = getNodeByPoint(x, y);
                        if (currentNode != null) {
                            Line currentLine = getLineOrNullByStatus(ElementStatus.LINE_STILL_DRAWING);
                            if (currentLine != null) {
                                if (!currentLine.getStartNode().equals(currentNode)) {
                                    currentLine.setEndNodeAndLength(currentNode, 1);
                                    currentLine.setElementStatus(ElementStatus.NONE);
                                    if (!currentNode.addLine(currentLine) || !currentLine.getStartNode().addLine(currentLine)) {
                                        lines.removeIf(line -> line.getId() == currentLine.getId());
                                    }
                                } else {
                                    lines.remove(currentLine);
                                }
                            }
                        } else {
                            lines.removeIf(line -> line.hasStatus(ElementStatus.LINE_STILL_DRAWING));
                        }
                        if (getLineByPoint(x, y) == null || getNodeByPoint(x, y) != null) {
                            setStatusForAllElements(ElementStatus.NONE);
                        }
                    }
                } else if (SwingUtilities.isMiddleMouseButton(e)) {
                    Node currentNode = getNodeByPoint(x, y);
                    if (currentNode != null) {
                        currentNode.changeTrustFactor();
                    }
                }
                repaint();
            }
        });
    }

    private void deleteLine(Line lineToDelete) {
        lineToDelete.removeFromNodes();
        lines.remove(lineToDelete);
    }


    private void makeElementActive(Element element) {
        setStatusForAllElements(ElementStatus.NONE);
        element.setElementStatus(ElementStatus.ACTIVE);
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

    private void setStatusForAllElements(ElementStatus elementStatus) {
        nodes.forEach(node -> node.setElementStatus(elementStatus));
        lines.forEach(line -> line.setElementStatus(elementStatus));
    }

    private Line getLineOrNullByStatus(ElementStatus elementStatus) {
        return lines.stream().filter(element -> element.hasStatus(elementStatus)).findFirst().orElse(null);
    }

    private Node getNodeOrNullByStatus(ElementStatus status) {
        return nodes.stream().filter(node -> node.hasStatus(status)).findFirst().orElse(null);
    }

    private Node getNodeByPoint(int x, int y) {
        return nodes.stream().filter(node -> node.containsPoint(x, y)).findFirst().orElse(null);
    }

    private Line getLineByPoint(int x, int y) {
        return lines.stream().filter(line -> line.containsPoint(x, y)).findFirst().orElse(null);
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
        graphics2D.setColor(Window.darkModeEnabled ? DARK_BACKGROUND : LIGHT_BACKGROUND);
        graphics2D.fillRect(0, 0, this.getWidth(), this.getHeight());
        graphics2D.drawImage(getMap(), 0, 0, null);
        drawAllLines(graphics2D);
        drawAllNodes(graphics2D);
    }
}
