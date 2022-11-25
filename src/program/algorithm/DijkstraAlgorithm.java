package program.algorithm;

import program.elements.line.Line;
import program.elements.node.Node;

import java.util.*;

public class DijkstraAlgorithm {
    public static HashMap<Node, Node> dijkstraAlgorithm(Collection<Node> nodes, Node startNode) {
        List<Node> unvisitedNodes = new ArrayList<>(nodes);
        List<Node> visitedNodes = new ArrayList<>();
        HashMap<Node, Node> nextPrevNodeMap = new HashMap<>();
        HashMap<Node, Integer> nodeDistanceMap = new HashMap<>();
        for (Node node : unvisitedNodes) {
            nodeDistanceMap.put(node, Integer.MAX_VALUE);
            nextPrevNodeMap.put(node, null);
        }
        nodeDistanceMap.replace(startNode, 0);
        int currentDistance = 0;
        while (!unvisitedNodes.isEmpty()) {
            unvisitedNodes.sort(Comparator.comparingDouble(nodeDistanceMap::get));
            Node currentNode = unvisitedNodes.get(0);
            for (Line currentLine : currentNode.getLines()) {
                Node currentNeighbor = currentLine.getOtherNode(currentNode);
                if (visitedNodes.contains(currentNode)) {
                    continue;
                }
                currentDistance += nodeDistanceMap.get(currentNode) + currentLine.getLength();
                currentDistance += !currentLine.isTrustful() ? 100000 : 0;
                if (currentDistance < nodeDistanceMap.get(currentNeighbor)) {
                    nodeDistanceMap.replace(currentNeighbor, currentDistance);
                    nextPrevNodeMap.replace(currentNeighbor, currentNode);
                }
                currentDistance = 0;
            }
            visitedNodes.add(currentNode);
            unvisitedNodes.remove(currentNode);
        }
        return nextPrevNodeMap;
    }
}
