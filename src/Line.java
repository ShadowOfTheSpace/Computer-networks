import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.HashMap;
import java.util.Objects;

public class Line extends Element {
    private final Node startNode;
    private Node endNode;
    private int cursorX, cursorY;
    private int length;
    private Color darkLineColor = Color.WHITE;
    private Color lightLineColor = Color.BLACK;
    private static final HashMap<Color, Color> darkColorMap = new HashMap<>();
    private static final HashMap<Color, Color> lightColorMap = new HashMap<>();

    static {
        darkColorMap.put(Color.WHITE, Color.GREEN);
        darkColorMap.put(Color.GREEN, Color.RED);
        darkColorMap.put(Color.RED, new Color(0, 0, 0, 0));
        darkColorMap.put(new Color(0, 0, 0, 0), Color.WHITE);

        lightColorMap.put(Color.BLACK, Color.GREEN.darker());
        lightColorMap.put(Color.GREEN.darker(), Color.RED.darker());
        lightColorMap.put(Color.RED.darker(), new Color(0, 0, 0, 0));
        lightColorMap.put(new Color(0, 0, 0, 0), Color.BLACK);
    }


    public void changeColor() {
//        darkLineColor = darkColorMap.get(darkLineColor);
//        lightLineColor = lightColorMap.get(lightLineColor);
    }

    public Line(int id, Node startNode) {
        super(id, "", ElementStatus.DRAWING_LINE);
        this.startNode = startNode;
        this.cursorX = startNode.getX();
        this.cursorY = startNode.getY();
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

    public void setEndNodeAndLength(Node endNode, int length) {
        this.endNode = endNode;
        this.elementName = startNode.elementName + "-" + endNode.elementName;
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
