package program.ui.windows.panels.executing_mode_rule.mouse_released;

import program.elements.element.Status;
import program.elements.line.Line;
import program.elements.node.Node;
import program.elements.node.Nodes;
import program.modes.Metric;
import program.ui.windows.main.Window;
import program.ui.windows.panels.executing_mode_rule.executable.Executable;
import program.ui.windows.panels.executing_mode_rule.mouse_action.MouseActionMode;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;

public class MouseReleasedWithModeCreatingNodes extends MouseActionMode implements Executable {

    public MouseReleasedWithModeCreatingNodes(List<Node> nodes, List<Line> lines, Metric metric, MouseEvent event) {
        super(nodes, lines, metric, event);
    }

    @Override
    public void execute() {
        Point cursorPoint = event.getPoint();
        Node currentNode = Nodes.getNodeByPoint(cursorPoint, nodes);
        if (currentNode.exists() && currentNode.hasStatus(Status.MOVING_NODE)) {
            currentNode.setElementStatus(Status.ACTIVE);
            Window.getMainPanel().setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }
}
