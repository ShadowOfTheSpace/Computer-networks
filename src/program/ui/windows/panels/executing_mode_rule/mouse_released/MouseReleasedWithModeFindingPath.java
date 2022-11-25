package program.ui.windows.panels.executing_mode_rule.mouse_released;

import program.algorithm.Path;
import program.elements.element.Status;
import program.elements.line.Line;
import program.elements.node.Node;
import program.elements.node.Nodes;
import program.modes.Metric;
import program.ui.windows.panels.executing_mode_rule.executable.Executable;
import program.ui.windows.panels.executing_mode_rule.mouse_action.MouseActionMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;

public class MouseReleasedWithModeFindingPath extends MouseActionMode implements Executable {

    public MouseReleasedWithModeFindingPath(List<Node> nodes, List<Line> lines, Metric metric, MouseEvent event) {
        super(nodes, lines, metric, event);
    }

    @Override
    public void execute() {
        if (SwingUtilities.isMiddleMouseButton(event)) {
            Point cursorPoint = event.getPoint();
            Node currentNode = Nodes.getNodeByPoint(cursorPoint, nodes);
            if (currentNode.exists()) {
                currentNode.changeTrustFactor();
                Node startNode = Nodes.getNodeByStatus(Status.START_NODE, nodes);
                Node endNode = Nodes.getNodeByStatus(Status.END_NODE, nodes);
                Path.showPath(nodes, lines, startNode, endNode);
            }
        }
    }
}
