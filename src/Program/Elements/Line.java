package Program.Elements;

import Program.Modes.Metric;
import Program.UI.Colors.Palette;
import Program.UI.Windows.Panels.MainPanel;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.Objects;

public class Line extends Element {
    public static final int MAX_LENGTH = 99999;
    private final Node startNode;
    private Node endNode;
    private int cursorX, cursorY;
    private int length;


    public Line(int id, Node startNode) {
        super(id, "", ElementStatus.DRAWING_LINE);
        this.startNode = startNode;
        this.cursorX = startNode.getX();
        this.cursorY = startNode.getY();
        this.length = 1;
    }

    public Node getStartNode() {
        return startNode;
    }


    public Node getEndNode() {
        return endNode;
    }

    public int getLength() {
        return length;
    }

    public Node getOtherNode(Node node) {
        return startNode.equals(node) ? endNode : startNode;
    }

    public boolean isTrustful() {
        return startNode.isTrustful() && endNode.isTrustful();
    }

    public void setEndNode(Node endNode) {
        this.endNode = endNode;
        String[] namesOfNodes = new String[]{startNode.elementName, endNode.elementName};
        Arrays.sort(namesOfNodes);
        this.elementName = namesOfNodes[0] + "-" + namesOfNodes[1];
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setCursorCoordinates(int cursorX, int cursorY) {
        this.cursorX = cursorX;
        this.cursorY = cursorY;
    }

    public void removeFromNodes() {
        startNode.removeLine(this);
        endNode.removeLine(this);
    }

    public Line2D getLine2D() {
        if (endNode != null) {
            return new Line2D.Double(startNode.getX(), startNode.getY(), endNode.getX(), endNode.getY());
        } else {
            return new Line2D.Double(startNode.getX(), startNode.getY(), cursorX, cursorY);
        }
    }

    public Point getMiddlePoint() {
        int x = (this.startNode.getX() + this.endNode.getX()) / 2;
        int y = (this.startNode.getY() + this.endNode.getY()) / 2;
        return new Point(x, y);
    }

    public double getAngleOfLine() {
        float x1 = startNode.getX(), y1 = startNode.getY();
        float x2 = endNode.getX(), y2 = endNode.getY();
        return (Math.atan((y1 - y2) / (x1 - x2)));
    }

    @Override
    public boolean containsPoint(int x, int y) {
        Ellipse2D currentPoint = new Ellipse2D.Double(x - 10 / 2.0, y - 10 / 2.0, 10, 10);
        return this.getLine2D().intersects(currentPoint.getBounds2D());
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        Line2D newLine;
        if (endNode == null) {
            newLine = new Line2D.Double(startNode.getX(), startNode.getY(), cursorX, cursorY);
        } else {
            newLine = new Line2D.Double(startNode.getX(), startNode.getY(), endNode.getX(), endNode.getY());
            if (MainPanel.getMetric() != Metric.HOPS) {
                if (MainPanel.getMetric() == Metric.DISTANCE) {
                    this.length = Node.calculateDistance(this.startNode, this.endNode);
                }
                drawLength(graphics2D);
            }
        }
        if (this.hasStatus(ElementStatus.NONE)) {
            drawLine(graphics2D, newLine, Palette.getLineNoneColor(), 5, false);
        } else if (this.hasStatus(ElementStatus.ACTIVE)) {
            drawLine(graphics2D, newLine, Palette.LINE_COLOR_ACTIVE, 11, false);
            drawLine(graphics2D, newLine, Palette.getLineNoneColor(), 5, false);
        } else if (this.hasStatus(ElementStatus.PART_OF_PATH)) {
            drawLine(graphics2D, newLine, Palette.getLineColorWithTrustFactor(this.isTrustful()), 5, false);
        } else if (this.hasStatus(ElementStatus.NOT_PART_OF_TREE)) {
            drawLine(graphics2D, newLine, Palette.LINE_COLOR_NOT_PART_OF_TREE, 5, false);
        } else {
            drawLine(graphics2D, newLine, Palette.getLineNoneColor(), 5, true);
        }
    }

    private void drawLength(Graphics2D graphics2D) {
        Font font = new Font("Arial", Font.BOLD, 20);
        graphics2D.setFont(font);
        String length = String.valueOf(this.length);
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        int dx = fontMetrics.stringWidth(length);
        int dy = fontMetrics.getHeight();
        Point middlePoint = getMiddlePoint();
        float stringX = (middlePoint.x - dx / 2f), stringY = (middlePoint.y - dy / 2f);
        graphics2D.rotate(this.getAngleOfLine(), middlePoint.x, middlePoint.y);
        graphics2D.setColor(Palette.getMainPanelBackground());
        graphics2D.fillRect((int) stringX, (int) (stringY - dy / 1.5f), dx, (int) (dy / 1.3f));
        if (this.hasStatus(ElementStatus.NOT_PART_OF_TREE)) {
            graphics2D.setColor(Palette.LINE_COLOR_NOT_PART_OF_TREE);
        } else {
            graphics2D.setColor(Palette.getFontColor());
        }
        graphics2D.drawString(length, stringX, stringY);
        graphics2D.rotate(-this.getAngleOfLine(), middlePoint.x, middlePoint.y);
    }

    private void drawLine(Graphics2D graphics2D, Line2D line, Color color, int strokeWith, boolean isSeparated) {
        graphics2D.setColor(color);
        Stroke stroke = isSeparated ? new BasicStroke(strokeWith, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                1, new float[]{20}, 1) : new BasicStroke(strokeWith);
        graphics2D.setStroke(stroke);
        graphics2D.draw(line);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return this.startNode.equals(line.startNode) && Objects.equals(this.endNode, line.endNode) ||
                this.startNode.equals(line.endNode) && Objects.equals(this.endNode, line.startNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startNode, endNode);
    }
}
