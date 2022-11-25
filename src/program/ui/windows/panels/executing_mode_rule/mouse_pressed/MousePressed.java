package program.ui.windows.panels.executing_mode_rule.mouse_pressed;

import program.elements.line.Line;
import program.elements.node.Node;
import program.modes.Metric;
import program.ui.windows.panels.executing_mode_rule.mouse_action.MouseAction;

import java.awt.event.MouseEvent;
import java.util.List;

public class MousePressed extends MouseAction {
    public MousePressed(List<Node> nodes, List<Line> lines, Metric metric, MouseEvent event) {
        super(new MousePressedWithModeCreatingNodes(nodes, lines, metric, event),
                new MousePressedWithModeCreatingLines(nodes, lines, metric, event),
                new MousePressedWithModeFindingPath(nodes, lines, metric, event),
                new MousePressedWithModeFindingTree(nodes, lines, metric, event));
    }
}
