package program.elements.routing_table;

import program.algorithm.Tree;
import program.elements.node.Node;
import program.modes.Metric;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class RoutingTable {
    private final ArrayList<ArrayList<String>> columns;
    private final ArrayList<String> stringWithMaxWidth = new ArrayList<>();

    public RoutingTable(Node node, List<Node> nodes, Metric metric) {
        columns = Tree.getStringTree(nodes, node, metric);
        columns.forEach(columns -> stringWithMaxWidth.add(columns.stream()
                .max(Comparator.comparingInt(String::length))
                .orElse("")));
    }

    private static int getStringWidth(String string, Font font) {
        FontRenderContext frc = new FontRenderContext(null, true, true);
        return (int) font.getStringBounds(string, frc).getWidth();
    }

    public int[] getWidthOfEachColumn(Font font) {
        return stringWithMaxWidth.stream()
                .mapToInt(s -> getStringWidth(s, font) + 10)
                .toArray();
    }

    public int getTotalWidth(Font font) {
        return Arrays.stream(getWidthOfEachColumn(font))
                .sum() + (getColumnCount() - 1);
    }

    public int getTotalHeight() {
        return getRowsCount() * 40 + (getRowsCount());
    }

    public ArrayList<ArrayList<String>> getTable() {
        return columns;
    }

    public int getColumnCount() {
        return columns.size();
    }

    public int getRowsCount() {
        return columns.get(0).size();
    }
}
