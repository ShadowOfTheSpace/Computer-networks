import java.awt.*;
import java.awt.geom.Ellipse2D;
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
            graphics2D.setColor(Color.BLACK);
            graphics2D.setStroke(new BasicStroke(5));
            graphics2D.draw(newLine);
        } else if (this.hasStatus(ElementStatus.ACTIVE)) {
            graphics2D.setColor(Color.ORANGE);
            graphics2D.setStroke(new BasicStroke(11));
            graphics2D.draw(newLine);
            graphics2D.setColor(Color.BLACK);
            graphics2D.setStroke(new BasicStroke(5));
            graphics2D.draw(newLine);
        } else {
            graphics2D.setColor(Color.BLACK);
            graphics2D.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                    1, new float[]{20}, 1));
            graphics2D.draw(newLine);

        }
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
