package program.ui.windows.panels.executing_mode_rule.mouse_moved;

import program.elements.line.Line;
import program.elements.node.Node;
import program.modes.Metric;
import program.ui.windows.panels.executing_mode_rule.mouse_action.MouseAction;

import java.awt.event.MouseEvent;
import java.util.List;

public class MouseMoved extends MouseAction {
    public MouseMoved(List<Node> nodes, List<Line> lines, Metric metric, MouseEvent event) {
        super(new MouseMovedWithModeCreatingNodes(nodes, lines, metric, event),
                new MouseMovedWithModeCreatingLines(nodes, lines, metric, event),
                new MouseMovedWithModeFindingPath(nodes, lines, metric, event),
                new MouseMovedWithModeFindingTree(nodes, lines, metric, event));
    }


}
