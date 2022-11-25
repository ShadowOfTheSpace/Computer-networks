package program.ui.windows.panels.executing_mode_rule.mouse_dragged;

import program.elements.line.Line;
import program.elements.node.Node;
import program.modes.Metric;
import program.ui.windows.panels.executing_mode_rule.mouse_action.MouseAction;

import java.awt.event.MouseEvent;
import java.util.List;

public class MouseDragged extends MouseAction {
    public MouseDragged(List<Node> nodes, List<Line> lines, Metric metric, MouseEvent event) {
        super(new MouseDraggedWithModeCreatingNodes(nodes, lines, metric, event),
                new MouseDraggedWithModeCreatingLines(nodes, lines, metric, event),
                new MouseDraggedWithModeFindingPath(nodes, lines, metric, event),
                new MouseDraggedWithModeFindingTree(nodes, lines, metric, event));
    }
}
