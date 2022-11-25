package program.ui.windows.panels.executing_mode_rule.mouse_pressed;

import program.algorithm.Path;
import program.elements.element.ElementStatus;
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

public class MousePressedWithModeFindingPath extends MouseActionMode implements Executable {
    public MousePressedWithModeFindingPath(List<Node> nodes, List<Line> lines, Metric metric, MouseEvent event) {
        super(nodes, lines, metric, event);
    }

    @Override
    public void execute() {
        Point cursorPoint = event.getPoint();
        Node currentNode = Nodes.getNodeByPoint(cursorPoint, nodes);
        Line currentLine = Lines.getLineByPoint(cursorPoint, lines);
        if (SwingUtilities.isLeftMouseButton(event) && currentNode.exists()) {
            Node startNode = Nodes.getNodeByStatus(ElementStatus.START_NODE, nodes);
            Node endNode = Nodes.getNodeByStatus(ElementStatus.END_NODE, nodes);
            if (!startNode.exists() && !endNode.exists()) {
                currentNode.setElementStatus(ElementStatus.START_NODE);
            } else if (startNode.exists() && !endNode.exists()) {
                currentNode.setElementStatus(ElementStatus.END_NODE);
            } else if (!startNode.exists()) {
                currentNode.setElementStatus(ElementStatus.START_NODE);
            } else if (currentNode.hasStatus(ElementStatus.START_NODE) || currentNode.hasStatus(ElementStatus.END_NODE)) {
                currentNode.setElementStatus(ElementStatus.NONE);
            }
            startNode = Nodes.getNodeByStatus(ElementStatus.START_NODE, nodes);
            endNode = Nodes.getNodeByStatus(ElementStatus.END_NODE, nodes);
            Path.showPath(nodes, lines, startNode, endNode);
        }else if (SwingUtilities.isRightMouseButton(event)) {
            if (currentNode.exists()) {
                RoutingTable routingTable = new RoutingTable(currentNode, nodes, metric);
                Window.getNodeInfoWindows().add(new NodeInformationWindow(currentNode, routingTable));
            } else if (currentLine.exists()) {
                Window.getLineInfoWindows().add(new LineInformationWindow(currentLine));
            }
        }
    }
}
