package program.ui.windows.panels.executing_mode_rule.mouse_pressed;

import program.elements.element.Element;
import program.elements.element.Status;
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
import program.ui.windows.panels.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;

public class MousePressedWithModeCreatingNodes extends MouseActionMode implements Executable {
    public MousePressedWithModeCreatingNodes(List<Node> nodes, List<Line> lines, Metric metric, MouseEvent event) {
        super(nodes, lines, metric, event);
    }

    @Override
    public void execute() {
        Point cursorPoint = event.getPoint();
        Node currentNode = Nodes.getNodeByPoint(cursorPoint, nodes);
        Node activeNode = Nodes.getNodeByStatus(Status.ACTIVE, nodes);
        Line currentLine = Lines.getLineByPoint(cursorPoint, lines);
        if (SwingUtilities.isLeftMouseButton(event)) {
            if (event.isControlDown()) {
                Element currentElement = Elements.getElementOrNullByPoint(cursorPoint, nodes, lines);
                if (currentElement.exists()) {
                    currentElement.changeActiveStatus();
                }
                return;
            }
            if (!activeNode.exists() || currentNode.exists()) {
                Node newNode = new Node(MainPanel.getNumberOfCreatedNodes() + 1, cursorPoint.x, cursorPoint.y);
                if (!Nodes.hasIntersection(newNode, nodes)) {
                    nodes.add(newNode);
                    MainPanel.incrementNumberOfCreatedNodes();
                } else {
                    if (currentNode.exists() && currentNode.hasStatus(Status.NONE)) {
                        if (!event.isControlDown()) {
                            Elements.setStatusForAllElements(Status.NONE, nodes, lines);
                        }
                        Elements.makeElementActive(currentNode);
                    }
                }
            } else {
                Elements.setStatusForAllElements(Status.NONE, nodes, lines);
            }
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
