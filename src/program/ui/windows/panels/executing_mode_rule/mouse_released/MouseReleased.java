package program.ui.windows.panels.executing_mode_rule.mouse_released;

import program.elements.line.Line;
import program.elements.node.Node;
import program.modes.Metric;
import program.ui.windows.panels.executing_mode_rule.mouse_action.MouseAction;


import java.awt.event.MouseEvent;
import java.util.List;

public class MouseReleased extends MouseAction {

    public MouseReleased(List<Node> nodes, List<Line> lines, Metric metric, MouseEvent event) {
        super(new MouseReleasedWithModeCreatingNodes(nodes, lines, metric, event),
                new MouseReleasedWithModeCreatingLines(nodes, lines, metric, event),
                new MouseReleasedWithModeFindingPath(nodes, lines, metric, event),
                new MouseReleasedWithModeFindingTree(nodes, lines, metric, event));

    }
}
