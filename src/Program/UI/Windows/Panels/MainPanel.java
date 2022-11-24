package Program.UI.Windows.Panels;

import Program.Algorithm.Path;
import Program.Algorithm.Tree;
import Program.Elements.*;
import Program.Modes.Metric;
import Program.Modes.Mode;
import Program.UI.Colors.Palette;
import Program.UI.Icons.ImagesAndIcons;
import Program.UI.Windows.InfoWindows.LineInformationWindow;
import Program.UI.Windows.InfoWindows.NodeInfoWindow;
import Program.UI.Windows.MainWindow.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class MainPanel extends JPanel {
    private static boolean mapIsVisible = false;
    private static boolean gridIsVisible = false;
    private boolean imagesAreReady = false;
    private static Image darkThemeMap;
    private static Image lightThemeMap;
    private static int numberOfCreatedNodes = 0;
    private static int numberOfCreatedLines = 0;
    private static final ArrayList<Node> nodes = new ArrayList<>();
    private static final ArrayList<Line> lines = new ArrayList<>();
    private static Mode mode = Mode.CREATING_NODES;
    private static Metric metric = Metric.HOPS;
    public static final int GRID_SIZE = 40;


    public MainPanel() {
        this.setFocusable(true);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    setStatusForAllElements(ElementStatus.NONE);
                } else if (e.isAltDown() && e.getKeyCode() == KeyEvent.VK_ENTER) {
                    SwingUtilities.getWindowAncestor(Window.getMainPanel()).dispose();
                    Window.changeFullscreen();
                    ((JFrame) SwingUtilities.getWindowAncestor(Window.getMainPanel())).setUndecorated(Window.isFullScreenEnabled());
                    SwingUtilities.getWindowAncestor(Window.getMainPanel()).setVisible(true);
                } else if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    deleteElements();
                } else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_A) {
                    setStatusForAllElements(ElementStatus.ACTIVE);
                }
                repaint();
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getX(), y = e.getY();
                if (SwingUtilities.isLeftMouseButton(e) && !e.isControlDown()) {
                    if (mode == Mode.CREATING_NODES) {
                        Node currentNode = getNodeOrNullByPoint(x, y);
                        if (currentNode == null) {
                            currentNode = getNodeOrNullByStatus(ElementStatus.MOVING_NODE);
                        }
                        if (currentNode != null && !currentNode.hasStatus(ElementStatus.NONE)) {
                            currentNode.setElementStatus(ElementStatus.MOVING_NODE);
                            int oldX = currentNode.getX(), oldY = currentNode.getY();
                            currentNode.setCoordinates(x, y);
                            if (hasIntersection(currentNode)) {
                                currentNode.setCoordinates(oldX, oldY);
                            }
                            setCursor(new Cursor(Cursor.MOVE_CURSOR));
                        }
                    } else if (mode == Mode.CREATING_LINES) {
                        Line currentLine = getLineOrNullByStatus(ElementStatus.DRAWING_LINE);
                        if (currentLine != null) {
                            currentLine.setCursorCoordinates(x, y);
                            Node currentNode = getNodeOrNullByPoint(x, y);
                            if (currentNode != null && !currentNode.hasStatus(ElementStatus.START_NODE)) {
                                currentNode.setElementStatus(ElementStatus.END_NODE);
                            } else {
                                currentNode = getNodeOrNullByStatus(ElementStatus.END_NODE);
                                if (currentNode != null) {
                                    currentNode.setElementStatus(ElementStatus.NONE);
                                }
                            }
                        }
                    }
                    repaint();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                int x = e.getX(), y = e.getY();
                if (mode == Mode.CREATING_NODES) {
                    if (getNodeOrNullByPoint(x, y) != null || (getLineOrNullByPoint(x, y) != null && e.isControlDown())) {
                        setCursor(new Cursor(Cursor.HAND_CURSOR));
                    } else {
                        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    }
                } else if (mode == Mode.CREATING_LINES) {
                    if (getNodeOrNullByPoint(x, y) != null || getLineOrNullByPoint(x, y) != null) {
                        setCursor(new Cursor(Cursor.HAND_CURSOR));
                    } else {
                        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    }
                } else if (mode == Mode.FINDING_PATH || mode == Mode.FINDING_TREE) {
                    if (getNodeOrNullByPoint(x, y) != null) {
                        setCursor(new Cursor(Cursor.HAND_CURSOR));
                    } else {
                        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    }
                }
            }
        });

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                requestFocus();
                int x = e.getX(), y = e.getY();
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if ((mode == Mode.CREATING_NODES || mode == Mode.CREATING_LINES) && e.isControlDown()) {
                        Element currentElement = getElementOrNullByPoint(x, y);
                        if (currentElement != null) {
                            currentElement.changeActiveStatus();
                        }
                    } else if (mode == Mode.CREATING_NODES) {
                        Node activeNode = getNodeOrNullByStatus(ElementStatus.ACTIVE);
                        if ((activeNode == null || getNodeOrNullByPoint(x, y) != null)) {
                            Node newNode = new Node(numberOfCreatedNodes + 1, x, y);
                            if (!hasIntersection(newNode)) {
                                nodes.add(newNode);
                                numberOfCreatedNodes++;
                            } else {
                                Node currentNode = getNodeOrNullByPoint(x, y);
                                if (currentNode != null && currentNode.hasStatus(ElementStatus.NONE)) {
                                    if (!e.isControlDown()) {
                                        setStatusForAllElements(ElementStatus.NONE);
                                    }
                                    makeElementActive(currentNode);
                                }
                            }
                        } else {
                            setStatusForAllElements(ElementStatus.NONE);
                        }
                    } else if (mode == Mode.CREATING_LINES) {
                        Node currentNode = getNodeOrNullByPoint(x, y);
                        Line currentLine = getLineOrNullByPoint(x, y);
                        if (currentNode != null) {
                            currentNode.setElementStatus(ElementStatus.START_NODE);
                            Line newLine = new Line(numberOfCreatedLines, currentNode);
                            lines.add(newLine);
                            numberOfCreatedLines++;
                        } else if (currentLine != null && currentLine.hasStatus(ElementStatus.NONE)) {
                            if (!e.isControlDown()) {
                                setStatusForAllElements(ElementStatus.NONE);
                            }
                            makeElementActive(currentLine);
                        }
                    } else if (mode == Mode.FINDING_PATH) {
                        Node currentNode = getNodeOrNullByPoint(x, y);
                        if (currentNode != null) {
                            Node startNode = getNodeOrNullByStatus(ElementStatus.START_NODE);
                            Node endNode = getNodeOrNullByStatus(ElementStatus.END_NODE);
                            if (startNode == null && endNode == null) {
                                currentNode.setElementStatus(ElementStatus.START_NODE);
                            } else if (startNode != null && endNode == null) {
                                currentNode.setElementStatus(ElementStatus.END_NODE);
                            } else if (startNode == null) {
                                currentNode.setElementStatus(ElementStatus.START_NODE);
                            } else if (currentNode.hasStatus(ElementStatus.START_NODE) || currentNode.hasStatus(ElementStatus.END_NODE)) {
                                currentNode.setElementStatus(ElementStatus.NONE);
                            }
                            drawPath();
                        }
                    } else if (mode == Mode.FINDING_TREE) {
                        Node currentNode = getNodeOrNullByPoint(x, y);
                        if (currentNode != null) {
                            Node startNode = getNodeOrNullByStatus(ElementStatus.START_NODE);
                            if (startNode == null) {
                                currentNode.setElementStatus(ElementStatus.START_NODE);
                            } else {
                                if (currentNode.equals(startNode)) {
                                    currentNode.setElementStatus(ElementStatus.NONE);
                                } else {
                                    startNode.setElementStatus(ElementStatus.NONE);
                                    currentNode.setElementStatus(ElementStatus.START_NODE);
                                }
                            }
                            drawTree();
                        }
                    }
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    Node currentNode = getNodeOrNullByPoint(x, y);
                    Line currentLine = getLineOrNullByPoint(x, y);
                    if (currentNode != null) {
                        Window.getNodeInfoWindows().add(new NodeInfoWindow(currentNode, new RoutingTable(currentNode, nodes, metric)));
                    } else if (currentLine != null) {
                        Window.getLineInfoWindows().add(new LineInformationWindow(currentLine));
                    }
                }
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int x = e.getX(), y = e.getY();
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if (mode == Mode.CREATING_NODES) {
                        Node currentNode = getNodeOrNullByPoint(x, y);
                        if (currentNode != null && currentNode.hasStatus(ElementStatus.MOVING_NODE)) {
                            currentNode.setElementStatus(ElementStatus.ACTIVE);
                            setCursor(new Cursor(Cursor.HAND_CURSOR));
                        }
                    }
                    if (mode == Mode.CREATING_LINES) {
                        Node currentNode = getNodeOrNullByPoint(x, y);
                        if (currentNode != null) {
                            Line currentLine = getLineOrNullByStatus(ElementStatus.DRAWING_LINE);
                            if (currentLine != null) {
                                if (!currentLine.getStartNode().equals(currentNode)) {
                                    currentLine.setEndNode(currentNode);
                                    currentLine.setElementStatus(ElementStatus.NONE);
                                    if (!currentNode.addLine(currentLine) || !currentLine.getStartNode().addLine(currentLine)) {
                                        lines.removeIf(line -> line.getId() == currentLine.getId());
                                    } else {
                                        currentLine.setLength(getLengthOfLine(currentLine));
                                    }
                                } else {
                                    lines.remove(currentLine);
                                    currentNode.setElementStatus(ElementStatus.NONE);
                                }
                            }
                        } else {
                            lines.removeIf(line -> line.hasStatus(ElementStatus.DRAWING_LINE));
                            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        }
                        if ((getLineOrNullByPoint(x, y) == null || getNodeOrNullByPoint(x, y) != null) && !e.isControlDown()) {
                            setStatusForAllElements(ElementStatus.NONE);
                        }
                    }
                } else if (SwingUtilities.isMiddleMouseButton(e)) {
                    Node currentNode = getNodeOrNullByPoint(x, y);
                    if (currentNode != null) {
                        currentNode.changeTrustFactor();
                        if (mode == Mode.FINDING_PATH) {
                            drawPath();
                        } else if (mode == Mode.FINDING_TREE) {
                            drawTree();
                        }
                    }
                }
                repaint();
            }
        });
    }

    private void drawPath() {
        Node startNode = getNodeOrNullByStatus(ElementStatus.START_NODE);
        Node endNode = getNodeOrNullByStatus(ElementStatus.END_NODE);
        Path.showPath(nodes, lines, startNode, endNode);
    }

    private void drawTree() {
        Node startNode = getNodeOrNullByStatus(ElementStatus.START_NODE);
        Tree.showTree(nodes, lines, startNode);
    }

    private void deleteLine(Line lineToDelete) {
        lineToDelete.removeFromNodes();
        lines.remove(lineToDelete);
    }


    private void makeElementActive(Element element) {
        element.setElementStatus(ElementStatus.ACTIVE);
    }

    private Image getMap() {
        if (!imagesAreReady) {
            darkThemeMap = ImagesAndIcons.getDarkThemeMap().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
            lightThemeMap = ImagesAndIcons.getLightThemeMap().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
            imagesAreReady = true;
        }
        return Window.isDarkModeEnabled() ? darkThemeMap : lightThemeMap;
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

    private Collection<Line> getLinesByStatus(ElementStatus status) {
        return lines.stream().filter(line -> line.hasStatus(status)).collect(Collectors.toList());
    }

    private Collection<Node> getNodesByStatus(ElementStatus status) {
        return nodes.stream().filter(node -> node.hasStatus(status)).collect(Collectors.toList());
    }

    private Line getLineOrNullByStatus(ElementStatus elementStatus) {
        return lines.stream().filter(line -> line.hasStatus(elementStatus)).findFirst().orElse(null);
    }

    private Node getNodeOrNullByStatus(ElementStatus status) {
        return nodes.stream().filter(node -> node.hasStatus(status)).findFirst().orElse(null);
    }

    private Element getElementOrNullByPoint(int x, int y) {
        Node currentNode = getNodeOrNullByPoint(x, y);
        Line currentLine = getLineOrNullByPoint(x, y);
        if (currentNode == null) {
            return currentLine;
        }
        return currentNode;
    }

    private Node getNodeOrNullByPoint(int x, int y) {
        return nodes.stream().filter(node -> node.containsPoint(x, y)).findFirst().orElse(null);
    }

    private Line getLineOrNullByPoint(int x, int y) {
        return lines.stream().filter(line -> line.containsPoint(x, y)).findFirst().orElse(null);
    }

    private Collection<Element> getElementsWithNonNoneStatus() {
        Collection<Element> elements = nodes.stream().filter(node -> !node.hasStatus(ElementStatus.NONE)).collect(Collectors.toList());
        elements.addAll(lines.stream().filter(line -> !line.hasStatus(ElementStatus.NONE)).collect(Collectors.toList()));
        return elements;
    }

    private void drawAllNodes(Graphics2D graphics2D) {
        nodes.forEach(node -> node.draw(graphics2D));
    }

    private void drawAllLines(Graphics2D graphics2D) {
        lines.forEach(line -> line.draw(graphics2D));
    }

    private void drawGrid(Graphics2D graphics2D) {
        Color gridColor = Palette.getFontColor();
        int red = gridColor.getRed(), green = gridColor.getGreen(), blue = gridColor.getBlue();
        graphics2D.setColor(new Color(red, green, blue, mapIsVisible ? 30 : 60));
        for (int x = GRID_SIZE; x < getWidth(); x += GRID_SIZE) {
            graphics2D.drawLine(x, 0, x, getHeight());
        }
        for (int y = GRID_SIZE; y < getHeight(); y += GRID_SIZE) {
            graphics2D.drawLine(0, y, getWidth(), y);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        graphics2D.setColor(Palette.getMainPanelBackground());
        graphics2D.fillRect(0, 0, this.getWidth(), this.getHeight());
        if (gridIsVisible) {
            drawGrid(graphics2D);
        }
        if (mapIsVisible) {
            graphics2D.drawImage(getMap(), 0, 0, null);
        }
        drawAllLines(graphics2D);
        drawAllNodes(graphics2D);
    }

    public void reset() {
        if (getElementsWithNonNoneStatus().isEmpty()) {
            nodes.clear();
            lines.clear();
            numberOfCreatedLines = 0;
            numberOfCreatedNodes = 0;
        } else {
            setStatusForAllElements(ElementStatus.NONE);
        }
        repaint();
    }

    public void deleteElements() {
        Collection<Node> nodesToDelete = getNodesByStatus(ElementStatus.ACTIVE);
        Collection<Line> linesToDelete = getLinesByStatus(ElementStatus.ACTIVE);
        if (!nodesToDelete.isEmpty() || !linesToDelete.isEmpty()) {
            nodesToDelete.forEach(node -> node.getLines().forEach(this::deleteLine));
            nodes.removeAll(nodesToDelete);
            linesToDelete.forEach(this::deleteLine);
            repaint();
        }
    }

    public boolean isMapVisible() {
        return mapIsVisible;
    }

    public void setMapVisible(boolean mapIsVisible) {
        MainPanel.mapIsVisible = mapIsVisible;
    }

    public boolean isGridVisible() {
        return gridIsVisible;
    }

    public void setGridVisible(boolean gridIsVisible) {
        MainPanel.gridIsVisible = gridIsVisible;
    }

    public void setMode(Mode mode) {
        MainPanel.mode = mode;
        this.setStatusForAllElements(ElementStatus.NONE);
    }

    public void setMetric(Metric metric) {
        MainPanel.metric = metric;
        setStatusForAllElements(ElementStatus.NONE);
        reset();
    }

    public static Metric getMetric() {
        return metric;
    }

    private int getLengthOfLine(Line line) {
        if (metric == Metric.CONGESTION) {
            Window.getLineInfoWindows().add(new LineInformationWindow(line));
        } else if (metric == Metric.DISTANCE) {
            return Node.calculateDistance(line.getStartNode(), line.getEndNode());
        }
        return 1;
    }
}
