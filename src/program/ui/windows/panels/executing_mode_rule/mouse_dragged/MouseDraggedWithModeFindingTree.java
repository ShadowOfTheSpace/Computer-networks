package program.ui.windows.panels.executing_mode_rule.mouse_dragged;

import program.elements.line.Line;
import program.elements.node.Node;
import program.modes.Metric;
import program.ui.windows.panels.executing_mode_rule.executable.Executable;
import program.ui.windows.panels.executing_mode_rule.mouse_action.MouseActionMode;

import java.awt.event.MouseEvent;
import java.util.List;

public class MouseDraggedWithModeFindingTree extends MouseActionMode implements Executable {

    public MouseDraggedWithModeFindingTree(List<Node> nodes, List<Line> lines, Metric metric, MouseEvent event) {
        super(nodes, lines, metric, event);
    }

    @Override
    public void execute() {

    }
}
