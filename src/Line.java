import java.awt.*;
import java.awt.geom.Line2D;
import java.util.Objects;

public class Line extends Element {
    private final Node startNode;
    private Node endNode;
    private int cursorX, cursorY;
    private int length;

    public Line(int id, Node startNode) {
        super(id, "", ElementStatus.LINE_STILL_DRAWING);
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

    public void setEndNodeAndLength(Node endNode, int length) {
        this.endNode = endNode;
        this.elementName = startNode.elementName + "-" + endNode.elementName;
        this.length = length;
    }

    public void setCursorCoordinates(int cursorX, int cursorY) {
        this.cursorX = cursorX;
        this.cursorY = cursorY;
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        Line2D newLine;
        if (endNode == null) {
            newLine = new Line2D.Double(startNode.getX(), startNode.getY(), cursorX, cursorY);
        } else {
            newLine = new Line2D.Double(startNode.getX(), startNode.getY(), endNode.getX(), endNode.getY());
        }
        graphics2D.setColor(Color.BLACK);
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.draw(newLine);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return startNode.equals(line.startNode) && Objects.equals(endNode, line.endNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startNode, endNode);
    }
}
