package program.ui.windows.panels.executing_mode_rule.mouse_dragged;

import program.elements.element.Status;
import program.elements.line.Line;
import program.elements.line.Lines;
import program.elements.node.Node;
import program.elements.node.Nodes;
import program.modes.Metric;
import program.ui.windows.panels.executing_mode_rule.executable.Executable;
import program.ui.windows.panels.executing_mode_rule.mouse_action.MouseActionMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;

public class MouseDraggedWithModeCreatingLines extends MouseActionMode implements Executable {

    public MouseDraggedWithModeCreatingLines(List<Node> nodes, List<Line> lines, Metric metric, MouseEvent event) {
        super(nodes, lines, metric, event);
    }

    @Override
    public void execute() {
        if(SwingUtilities.isLeftMouseButton(event)&&!event.isControlDown()){
            Point cursorPoint = event.getPoint();
            Line currentLine = Lines.getLineOrNullByStatus(Status.DRAWING_LINE, lines);
            if (currentLine.exists()) {
                currentLine.setCursorPoint(cursorPoint);
                Node currentNode = Nodes.getNodeByPoint(cursorPoint, nodes);
                if (currentNode.exists() && !currentNode.hasStatus(Status.START_NODE)) {
                    currentNode.setElementStatus(Status.END_NODE);
                } else {
                    currentNode = Nodes.getNodeByStatus(Status.END_NODE, nodes);
                    if (currentNode.exists()) {
                        currentNode.setElementStatus(Status.NONE);
                    }
                }
            }
        }
    }
}
