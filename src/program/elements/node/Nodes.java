package program.elements.node;

import program.elements.element.Status;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class Nodes {
    private static Node nonExistingNode;

    public static Node getNonExistingNode() {
        if (nonExistingNode == null) {
            nonExistingNode = new Node();
        }
        return nonExistingNode;
    }

    public static Node getNodeByPoint(Point point, List<Node> nodes) {
        return nodes.stream()
                .filter(node -> node.containsPoint(point))
                .findFirst()
                .orElse(Nodes.getNonExistingNode());
    }

    public static Node getNodeByStatus(Status status, List<Node> nodes) {
        return nodes.stream()
                .filter(node -> node.hasStatus(status))
                .findFirst()
                .orElse(Nodes.getNonExistingNode());
    }

    public static List<Node> getNodesByStatus(Status status, List<Node> nodes) {
        return nodes.stream()
                .filter(node -> node.hasStatus(status))
                .collect(Collectors.toList());
    }

    public static boolean hasIntersection(Node newNode, List<Node> nodes) {
        for (Node node : nodes) {
            if (newNode.isIntersect(node)) {
                return true;
            }
        }
        return false;
    }

    public static int calculateDistance(Node node1, Node node2) {
        int x1 = node1.getX(), x2 = node2.getX(), y1 = node1.getY(), y2 = node2.getY();
        return (int) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

}
