import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Node extends Element {
    private int x, y;
    private boolean trustFactor;
    private static final int SIZE_OF_NODE = 60;
    private final String ipAddress;


    private final ArrayList<Line> lines = new ArrayList<>();
    private static final HashMap<ElementStatus, Image> elementStatusColorMap = new HashMap<>();

    public Node(int id, int x, int y) {
        super(id, NodeNameGenerator.generateName(id), ElementStatus.NONE);
        setCoordinates(x, y);
        this.trustFactor = true;
        this.ipAddress = RandomIPAddressGenerator.getIP();
    }

    public Line getLineToOtherNode(Node otherNode) {
        for (Line line : lines) {
            if (line.getOtherNode(this).equals(otherNode)) {
                return line;
            }
        }
        return null;
    }

    public void setCoordinates(int x, int y) {
        if (Window.mainPanel.isGridVisible()) {
            this.x = Math.round(x / (float) MainPanel.GRID_SIZE) * MainPanel.GRID_SIZE;
            this.y = Math.round(y / (float) MainPanel.GRID_SIZE) * MainPanel.GRID_SIZE;
        } else {
            this.x = x;
            this.y = y;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static int getSizeOfNode() {
        return SIZE_OF_NODE;
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
        Area firstArea = new Area(this.getEllipse());
        Area secondArea = new Area(node.getEllipse());
        firstArea.intersect(secondArea);
        return !firstArea.isEmpty();
    }

    @Override
    public boolean containsPoint(int x, int y) {
        return new Area(this.getEllipse()).contains(x, y);
    }

    public Ellipse2D getEllipse() {
        return new Ellipse2D.Double(this.x - SIZE_OF_NODE / 2.0, this.y - SIZE_OF_NODE / 2.0, SIZE_OF_NODE, SIZE_OF_NODE);
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
        int circleX = (int) this.getEllipse().getX(), circleY = (int) this.getEllipse().getY();
        graphics2D.drawImage(ImagesAndIcons.getImage(Window.darkModeEnabled, this.trustFactor, this.elementStatus), circleX, circleY, null);
        graphics2D.setStroke(new BasicStroke(3));
        graphics2D.setColor(Palette.getMainPanelBackground());
        graphics2D.setFont(new Font("Arial", Font.BOLD, 24));
        graphics2D.drawString(this.elementName, circleX + 3, circleY - 3);
        graphics2D.drawString(this.elementName, circleX + 3, circleY + 3);
        graphics2D.drawString(this.elementName, circleX - 3, circleY - 3);
        graphics2D.drawString(this.elementName, circleX - 3, circleY + 3);
        graphics2D.setColor(Palette.getFontColor());
        graphics2D.drawString(this.elementName, circleX, circleY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Node node = (Node) o;
        return id == node.id && x == node.x && y == node.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, x, y);
    }
}
