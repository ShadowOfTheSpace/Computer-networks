package Program.Algorithm;

import Program.Elements.Element;
import Program.Elements.ElementStatus;
import Program.Elements.Line;
import Program.Elements.Node;
import Program.Modes.Metric;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

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


    public static ArrayList<ArrayList<String>> getStringTree(Collection<Node> nodes, Node startNode, Metric metric) {
        ArrayList<String> firstColumn = new ArrayList<>();
        ArrayList<String> secondColumn = new ArrayList<>();
        ArrayList<String> thirdColumn = new ArrayList<>();
        ArrayList<String> fourthColumn = new ArrayList<>();
        firstColumn.add("Destination network");
        secondColumn.add("Gateway");
        thirdColumn.add("Metric");
        fourthColumn.add("Path");
        List<Node> endNodes = new ArrayList<>(nodes);
        endNodes.remove(startNode);


        HashMap<Node, Node> nextPrevNodeMap = DijkstraAlgorithm.dijkstraAlgorithm(nodes, startNode);
        for (Node endNode : endNodes) {
            ArrayList<Node> currentPath = new ArrayList<>(Path.unpackTable(nextPrevNodeMap, startNode, endNode));
            if (currentPath.isEmpty()) {
                continue;
            }
            firstColumn.add(" " + endNode.getElementName() + " — " + endNode.getIpAddress());
            secondColumn.add(currentPath.get(1).getElementName());
            int totalLength = 0;
            for (int i = 1; i < currentPath.size(); i++) {
                Line line = currentPath.get(i - 1).getLineToOtherNode(currentPath.get(i));
                totalLength += line.getLength();
            }
            if (metric == Metric.HOPS) {
                totalLength--;
            }
            thirdColumn.add(String.valueOf(totalLength));
            fourthColumn.add(" " + currentPath.stream()
                    .map(Element::getElementName)
                    .collect(Collectors.joining("→")));
        }
        return new ArrayList<>(Arrays.asList(firstColumn, secondColumn, thirdColumn, fourthColumn));
    }
}
