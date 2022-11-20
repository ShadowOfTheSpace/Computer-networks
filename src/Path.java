import java.util.*;
import java.util.stream.Collectors;

public class Path {
    public static void showPath(Collection<Node> nodes, Collection<Line> lines, Node startNode, Node endNode) {
        lines.forEach(line -> line.setElementStatus(ElementStatus.NONE));
        if (startNode == null || endNode == null) {
            return;
        }
        List<Node> path = new ArrayList<>(getPath(nodes, startNode, endNode));
        for (int i = 1; i < path.size(); i++) {
            Line lineToShow = path.get(i - 1).getLineToOtherNode(path.get(i));
            lineToShow.setElementStatus(ElementStatus.PART_OF_PATH);
        }
    }

    public static Collection<Node> getPath(Collection<Node> nodes, Node startNode, Node endNode) {
        if (startNode == null || endNode == null) {
            return new ArrayList<>();
        }
        return unpackTable(DijkstraAlgorithm.dijkstraAlgorithm(nodes, startNode), startNode, endNode);
    }

    public static String getStringPath(Collection<Node> nodes, Node startNode, Node endNode) {
        return getPath(nodes, startNode, endNode).stream()
                .map(Element::getElementName)
                .collect(Collectors.joining("->"));
    }

    public static Collection<Node> unpackTable(HashMap<Node, Node> nextPrevNodeMap, Node startNode, Node endNode) {
        nextPrevNodeMap = new HashMap<>(nextPrevNodeMap);
        Deque<Node> path = new ArrayDeque<>();
        Node currentNode = endNode;
        while (!currentNode.equals(startNode) && !nextPrevNodeMap.isEmpty()) {
            path.push(currentNode);
            currentNode = nextPrevNodeMap.remove(currentNode);
            if (currentNode == null) {
                return new ArrayList<>();
            }
        }
        path.push(startNode);
        return path;
    }
}
