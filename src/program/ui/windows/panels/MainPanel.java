package program.ui.windows.panels;

import program.elements.element.ElementStatus;
import program.elements.element.Elements;
import program.elements.line.Line;
import program.elements.node.Node;
import program.modes.Metric;
import program.modes.Mode;
import program.ui.colors.Palette;
import program.ui.images.ImagesAndIcons;
import program.ui.windows.main.Window;
import program.ui.windows.panels.executing_mode_rule.key_action.KeyPressed;
import program.ui.windows.panels.executing_mode_rule.mouse_dragged.MouseDragged;
import program.ui.windows.panels.executing_mode_rule.mouse_moved.MouseMoved;
import program.ui.windows.panels.executing_mode_rule.mouse_pressed.*;
import program.ui.windows.panels.executing_mode_rule.mouse_released.MouseReleased;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MainPanel extends JPanel {
    private static boolean mapIsVisible = false;
    private static boolean gridIsVisible = false;
    private boolean imagesAreReady = false;
    private static Image darkThemeMap;
    private static Image lightThemeMap;
    private static int numberOfCreatedNodes = 0;
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
                new KeyPressed(nodes, lines, e).executeRuleForCurrentKey(e.getKeyCode());
                repaint();
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                new MouseDragged(nodes, lines, metric, e).executeRuleForMouseAction(mode);
                requestFocus();
                repaint();

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                new MouseMoved(nodes, lines, metric, e).executeRuleForMouseAction(mode);
            }
        });

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                new MousePressed(nodes, lines, metric, e).executeRuleForMouseAction(mode);
                requestFocus();
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                new MouseReleased(nodes, lines, metric, e).executeRuleForMouseAction(mode);
                requestFocus();
                repaint();
            }
        });
    }


    private Image getMap() {
        if (!imagesAreReady) {
            darkThemeMap = ImagesAndIcons.getDarkThemeMap().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
            lightThemeMap = ImagesAndIcons.getLightThemeMap().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
            imagesAreReady = true;
        }
        return Window.isDarkModeEnabled() ? darkThemeMap : lightThemeMap;
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
        graphics2D.fillRect(0, 0, getWidth(), getHeight());
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
        if (Elements.getElementsWithNonNoneStatus(nodes, lines).isEmpty()) {
            nodes.clear();
            lines.clear();
            numberOfCreatedNodes = 0;
        } else {
            Elements.setStatusForAllElements(ElementStatus.NONE, nodes, lines);
        }
        repaint();
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
        Elements.setStatusForAllElements(ElementStatus.NONE, nodes, lines);
    }

    public void setMetric(Metric metric) {
        MainPanel.metric = metric;
        Elements.setStatusForAllElements(ElementStatus.NONE, nodes, lines);
        reset();
    }

    public static Metric getMetric() {
        return metric;
    }

    public static int getNumberOfCreatedNodes() {
        return numberOfCreatedNodes;
    }

    public static void incrementNumberOfCreatedNodes() {
        numberOfCreatedNodes++;
    }
}
