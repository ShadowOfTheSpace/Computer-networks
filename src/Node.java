import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Node extends Element {
    private int x, y;
    private boolean trustFactor;
    private final int sizeOfNode = 50;


    private final ArrayList<Line> lines = new ArrayList<>();
    private static final HashMap<ElementStatus, Color> elementStatusColorMap = new HashMap<>();

    static {
        elementStatusColorMap.put(ElementStatus.ACTIVE, Color.ORANGE);
        elementStatusColorMap.put(ElementStatus.NODE_IS_START_NODE, Color.GREEN.brighter());
        elementStatusColorMap.put(ElementStatus.NODE_CAN_BE_END_NODE, Color.RED);
        elementStatusColorMap.put(ElementStatus.NODE_IS_MOVABLE, Color.ORANGE);
    }

    public Node(int id, int x, int y, String nodeName) {
        super(id, nodeName, ElementStatus.NONE);
        this.x = x;
        this.y = y;
        this.trustFactor = true;
    }

    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isTrustful() {
        return trustFactor;
    }

    public void changeTrustFactor() {
        this.trustFactor = !this.trustFactor;
    }

    public boolean isIntersect(Node node) {
        if (this.equals(node)) {
            return false;
        }
        Ellipse2D firstEllipse = this.getEllipse();
        Ellipse2D secondEllipse = node.getEllipse();
        Area firstArea = new Area(firstEllipse);
        Area secondArea = new Area(secondEllipse);
        firstArea.intersect(secondArea);
        return !firstArea.isEmpty();
    }

    @Override
    public boolean containsPoint(int x, int y) {
        return new Area(this.getEllipse()).contains(x, y);
    }

    public Ellipse2D getEllipse() {
        return new Ellipse2D.Double(this.x - sizeOfNode / 2.0, this.y - sizeOfNode / 2.0, sizeOfNode, sizeOfNode);
    }

    public ArrayList<Line> getLines() {
        return new ArrayList<>(lines);
    }

    public boolean hasLine(Line line) {
        return this.lines.contains(line);
    }

    public boolean addLine(Line line) {
        if (!hasLine(line)) {
            return lines.add(line);
        } else {
            return false;
        }
    }

    public void removeLine(Line line) {
        lines.remove(line);
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.setColor(Color.BLUE);
        graphics2D.fill(this.getEllipse());
        graphics2D.setColor(elementStatusColorMap.get(this.elementStatus));
        graphics2D.setStroke(new BasicStroke(3));
        graphics2D.draw(this.getEllipse());
        graphics2D.setColor(Color.BLACK);
        graphics2D.setFont(new Font("Arial", Font.BOLD, 24));
        graphics2D.drawString(this.elementName + this.lines.size(), this.x, this.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return id == node.id && x == node.x && y == node.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, x, y);
    }
}
