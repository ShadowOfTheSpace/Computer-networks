package program.ui.windows.panels.executing_mode_rule.mouse_pressed;

import program.algorithm.Tree;
import program.elements.element.ElementStatus;
import program.elements.element.Elements;
import program.elements.line.Line;
import program.elements.line.Lines;
import program.elements.node.Node;
import program.elements.node.Nodes;
import program.elements.routing_table.RoutingTable;
import program.modes.Metric;
import program.ui.windows.information.LineInformationWindow;
import program.ui.windows.information.NodeInformationWindow;
import program.ui.windows.main.Window;
import program.ui.windows.panels.executing_mode_rule.executable.Executable;
import program.ui.windows.panels.executing_mode_rule.mouse_action.MouseActionMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;

public class MousePressedWithModeFindingTree extends MouseActionMode implements Executable {
    public MousePressedWithModeFindingTree(List<Node> nodes, List<Line> lines, Metric metric, MouseEvent event) {
        super(nodes, lines, metric, event);
    }

    @Override
    public void execute() {
        Point cursorPoint = event.getPoint();
        Node currentNode = Nodes.getNodeByPoint(cursorPoint, nodes);
        Line currentLine = Lines.getLineByPoint(cursorPoint, lines);
        if (SwingUtilities.isLeftMouseButton(event) && currentNode.exists()) {
            if (currentNode.hasStatus(ElementStatus.START_NODE)) {
                Elements.setStatusForAllElements(ElementStatus.NONE, nodes, lines);
            } else {
                Node oldStartNode = Nodes.getNodeByStatus(ElementStatus.START_NODE, nodes);
                if (oldStartNode.exists()) {
                    oldStartNode.setElementStatus(ElementStatus.NONE);
                }
                currentNode.setElementStatus(ElementStatus.START_NODE);
            }
            Node startNode = Nodes.getNodeByStatus(ElementStatus.START_NODE, nodes);
            Tree.showTree(nodes, lines, startNode);
        } else if (SwingUtilities.isRightMouseButton(event)) {
            if (currentNode.exists()) {
                RoutingTable routingTable = new RoutingTable(currentNode, nodes, metric);
                Window.getNodeInfoWindows().add(new NodeInformationWindow(currentNode, routingTable));
            } else if (currentLine.exists()) {
                Window.getLineInfoWindows().add(new LineInformationWindow(currentLine));
            }
        }
    }
}
