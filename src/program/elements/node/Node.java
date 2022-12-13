package program.elements.node;

import program.elements.element.Element;
import program.elements.element.Status;
import program.elements.line.Line;
import program.generators.ip_address.IPAddressGenerator;
import program.ui.colors.Palette;
import program.ui.images.ImagesAndIcons;
import program.ui.windows.main.Window;
import program.ui.windows.panels.MainPanel;
import program.generators.node_name.NodeNameGenerator;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Objects;

public class Node extends Element {
    private int x, y;
    private boolean trustFactor;
    private static final int SIZE_OF_NODE = 60;
    private final String ipAddress;
    private final ArrayList<Line> lines = new ArrayList<>();

    public Node(int id, int x, int y) {
        super(id, NodeNameGenerator.generateName(id), Status.NONE);
        setCoordinates(x, y);
        this.trustFactor = true;
        this.ipAddress = IPAddressGenerator.generateIPAddress();
    }

    public Node() {
        super(-1, "", Status.EMPTY_ELEMENT);
        this.trustFactor = false;
        this.ipAddress = "";
    }

    public Line getLineToOtherNode(Node otherNode) {
        for (Line line : lines) {
            if (line.getOtherNode(this).equals(otherNode)) {
                return line;
            }
        }
        return null;
    }

    public void setCoordinates(Point point) {
        setCoordinates(point.x, point.y);
    }

    public void setCoordinates(int x, int y) {
        if (Window.getMainPanel().isGridVisible()) {
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

    public Point getPoint() {
        return new Point(x, y);
    }

    public static int getSizeOfNode() {
        return SIZE_OF_NODE;
    }

    public boolean isTrustful() {
        return trustFactor;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void changeTrustFactor() {
        this.trustFactor = !this.trustFactor;
    }

    public boolean isIntersecting(Node node) {
        if (this.equals(node)) {
            return false;
        }
        Area firstArea = new Area(this.getEllipse());
        Area secondArea = new Area(node.getEllipse());
        firstArea.intersect(secondArea);
        return !firstArea.isEmpty();
    }

    @Override
    public boolean containsPoint(Point point) {
        this.getEllipse().contains(point);
        return this.getEllipse().contains(point);
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
        if (hasLine(line)) {
            return false;
        }
        return lines.add(line);
    }

    public void removeLine(Line line) {
        lines.remove(line);
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        int circleX = (int) this.getEllipse().getX(), circleY = (int) this.getEllipse().getY();
        graphics2D.drawImage(ImagesAndIcons.getImage(Window.isDarkModeEnabled(), this.trustFactor, this.status), circleX, circleY, null);
        graphics2D.setStroke(new BasicStroke(3));
        graphics2D.setColor(Palette.getMainPanelBackground());
        graphics2D.setFont(new Font("Arial", Font.BOLD, 24));
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        int dx = fontMetrics.stringWidth(this.elementName);
        int dy = fontMetrics.getHeight();
        graphics2D.fillRect(circleX, (int) (circleY - dy / 1.5f), dx, (int) (dy / 1.3f));
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
