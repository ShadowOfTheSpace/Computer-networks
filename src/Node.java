import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Node implements Drawable {
    private final int id;
    private int x, y;
    private final String nodeName;
    private ElementStatus status;
    private boolean trustFactor;
    private final int sizeOfNode = 50;

    public Node(int id, int x, int y, String nodeName) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.nodeName = nodeName;
        this.status = ElementStatus.NONE;
        this.trustFactor = true;
    }

    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getNodeName() {
        return nodeName;
    }

    public ElementStatus getStatus() {
        return status;
    }

    public void setStatus(ElementStatus status) {
        this.status = status;
    }

    public void changeActiveStatus() {
        this.status = (this.status == ElementStatus.ACTIVE ? ElementStatus.NONE : ElementStatus.ACTIVE);
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

    public boolean containsPoint(int x, int y) {
        return new Area(this.getEllipse()).contains(x, y);
    }

    public Ellipse2D getEllipse() {
        return new Ellipse2D.Double(this.x - sizeOfNode / 2.0, this.y - sizeOfNode / 2.0, sizeOfNode, sizeOfNode);
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.setColor(Color.GREEN);
        graphics2D.fill(this.getEllipse());
        if (this.status == ElementStatus.ACTIVE || this.status == ElementStatus.NODE_IS_MOVABLE) {
            graphics2D.setColor(Color.ORANGE);
            graphics2D.setStroke(new BasicStroke(3));
            graphics2D.draw(this.getEllipse());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return x == node.x && y == node.y && id == node.id;
    }
}
