package program.ui.windows.panels.executing_mode_rule.mouse_released;

import program.elements.element.Status;
import program.elements.element.Elements;
import program.elements.line.Line;
import program.elements.line.Lines;
import program.elements.node.Node;
import program.elements.node.Nodes;
import program.modes.Metric;
import program.ui.windows.main.Window;
import program.ui.windows.panels.executing_mode_rule.executable.Executable;
import program.ui.windows.panels.executing_mode_rule.mouse_action.MouseActionMode;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;

public class MouseReleasedWithModeCreatingLines extends MouseActionMode implements Executable {

    public MouseReleasedWithModeCreatingLines(List<Node> nodes, List<Line> lines, Metric metric, MouseEvent event) {
        super(nodes, lines, metric, event);
    }

    @Override
    public void execute() {
        Point cursorPoint = event.getPoint();
        Node currentNode = Nodes.getNodeByPoint(cursorPoint, nodes);
        Line currentLine = Lines.getLineOrNullByStatus(Status.DRAWING_LINE, lines);
        if (currentNode.exists()) {
            if (currentLine.exists()) {
                if (!currentLine.getStartNode().equals(currentNode)) {
                    currentLine.setEndNode(currentNode);
                    currentLine.setElementStatus(Status.NONE);
                    if (!currentNode.addLine(currentLine) || !currentLine.getStartNode().addLine(currentLine)) {
                        lines.removeIf(line -> line.getId() == currentLine.getId());
                    } else {
                        currentLine.setLength(Lines.getLengthOfLine(currentLine, metric));
                    }
                } else {
                    lines.remove(currentLine);
                    currentNode.setElementStatus(Status.NONE);
                }
            }
        } else {
            lines.removeIf(line -> line.hasStatus(Status.DRAWING_LINE));
            Window.getMainPanel().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        if ((!Lines.getLineByPoint(cursorPoint, lines).exists() || currentNode.exists()) && !event.isControlDown()) {
            Elements.setStatusForAllElements(Status.NONE, nodes, lines);
        }
    }
}
