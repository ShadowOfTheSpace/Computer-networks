package program.elements.element;

import program.elements.line.Line;
import program.elements.line.Lines;
import program.elements.node.Node;
import program.elements.node.Nodes;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class Elements {


    public static List<Element> getElementsWithNonNoneStatus(List<Node> nodes, List<Line> lines) {
        List<Element> elements = nodes.stream()
                .filter(node -> !node.hasStatus(ElementStatus.NONE))
                .collect(Collectors.toList());
        elements.addAll(lines.stream()
                .filter(line -> !line.hasStatus(ElementStatus.NONE))
                .collect(Collectors.toList()));
        return elements;
    }


    public static void setStatusForAllElements(ElementStatus elementStatus, List<Node> nodes, List<Line> lines) {
        nodes.forEach(node -> node.setElementStatus(elementStatus));
        lines.forEach(line -> line.setElementStatus(elementStatus));
    }


    public static Element getElementOrNullByPoint(Point point, List<Node> nodes, List<Line> lines) {
        Node currentNode = Nodes.getNodeByPoint(point, nodes);
        Line currentLine = Lines.getLineByPoint(point, lines);
        if (!currentNode.exists()) {
            return currentLine;
        }
        return currentNode;
    }

    public static void makeElementActive(Element element) {
        element.setElementStatus(ElementStatus.ACTIVE);
    }

    private static void deleteLine(Line lineToDelete, List<Line> lines) {
        lineToDelete.removeFromNodes();
        lines.remove(lineToDelete);
    }

    public static void deleteElements(List<Node> nodes, List<Line> lines) {
        List<Node> nodesToDelete = Nodes.getNodesByStatus(ElementStatus.ACTIVE, nodes);
        List<Line> linesToDelete = Lines.getLinesByStatus(ElementStatus.ACTIVE, lines);
        if (!nodesToDelete.isEmpty() || !linesToDelete.isEmpty()) {
            nodesToDelete.forEach(node -> node.getLines().forEach(line -> deleteLine(line, lines)));
            nodes.removeAll(nodesToDelete);
            linesToDelete.forEach(line -> deleteLine(line, lines));
        }
    }
}
