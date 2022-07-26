import java.awt.*;
import java.awt.geom.Line2D;

public class Line implements Drawable {
    private final int id;
    private String name;
    private final Node startNode;
    private Node endNode;
    private int cursorX, cursorY;
    private int length;
    private ElementStatus status;

    public void setStatus(ElementStatus status) {
        this.status = status;
    }

    public ElementStatus getStatus() {
        return status;
    }

    public Line(int id, Node startNode) {
        this.id = id;
        this.startNode = startNode;
        this.cursorX = startNode.getX();
        this.cursorY = startNode.getY();
        this.status = ElementStatus.LINE_STILL_DRAWING;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        this.length = length;
    }

    public int getCursorX() {
        return cursorX;
    }

    public int getCursorY() {
        return cursorY;
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
}
