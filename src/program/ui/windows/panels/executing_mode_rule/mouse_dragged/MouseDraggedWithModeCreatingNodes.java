package program.ui.windows.panels.executing_mode_rule.mouse_dragged;

import program.elements.element.Status;
import program.elements.line.Line;
import program.elements.node.Node;
import program.elements.node.Nodes;
import program.modes.Metric;
import program.ui.windows.main.Window;
import program.ui.windows.panels.executing_mode_rule.executable.Executable;
import program.ui.windows.panels.executing_mode_rule.mouse_action.MouseActionMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;

public class MouseDraggedWithModeCreatingNodes extends MouseActionMode implements Executable {

    public MouseDraggedWithModeCreatingNodes(List<Node> nodes, List<Line> lines, Metric metric, MouseEvent event) {
        super(nodes, lines, metric, event);
    }

    @Override
    public void execute() {
        if (SwingUtilities.isLeftMouseButton(event) && !event.isControlDown()) {
            Point cursorPoint = event.getPoint();
            Node currentNode = Nodes.getNodeByPoint(cursorPoint, nodes);
            if (!currentNode.exists()) {
                currentNode = Nodes.getNodeByStatus(Status.MOVING_NODE, nodes);
            }
            if (currentNode.exists() && !currentNode.hasStatus(Status.NONE)) {
                currentNode.setElementStatus(Status.MOVING_NODE);
                int oldX = currentNode.getX(), oldY = currentNode.getY();
                currentNode.setCoordinates(cursorPoint);
                if (Nodes.hasIntersection(currentNode, nodes)) {
                    currentNode.setCoordinates(oldX, oldY);
                }
                Window.getMainPanel().setCursor(new Cursor(Cursor.MOVE_CURSOR));
            }
        }
    }
}
