package Program.Algorithm;

import Program.Elements.ElementStatus;
import Program.Elements.Line;
import Program.Elements.Node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Tree {
    public static void showTree(Collection<Node> nodes, Collection<Line> lines, Node startNode) {
        lines.forEach(line -> line.setElementStatus(ElementStatus.NONE));
        if (startNode == null) {
            return;
        }
        List<Node> endNodes = new ArrayList<>(nodes);
        endNodes.remove(startNode);
        lines.forEach(line -> line.setElementStatus(ElementStatus.NOT_PART_OF_TREE));
        HashMap<Node, Node> nextPrevNodeMap = DijkstraAlgorithm.dijkstraAlgorithm(nodes, startNode);
        for (Node endNode : endNodes) {
            ArrayList<Node> currentPath = new ArrayList<>(Path.unpackTable(nextPrevNodeMap, startNode, endNode));
            for (int i = 1; i < currentPath.size(); i++) {
                Line lineToShow = currentPath.get(i - 1).getLineToOtherNode(currentPath.get(i));
                lineToShow.setElementStatus(ElementStatus.PART_OF_PATH);
            }
        }
    }
}
