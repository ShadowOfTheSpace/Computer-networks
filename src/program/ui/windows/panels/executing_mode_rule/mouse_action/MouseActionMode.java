package program.ui.windows.panels.executing_mode_rule.mouse_action;

import program.elements.line.Line;
import program.elements.node.Node;
import program.modes.Metric;

import java.awt.event.MouseEvent;
import java.util.List;

public class MouseActionMode {
    protected final List<Node> nodes;
    protected final List<Line> lines;
    protected final Metric metric;
    protected final MouseEvent event;

    public MouseActionMode(List<Node> nodes, List<Line> lines, Metric metric, MouseEvent event) {
        this.nodes = nodes;
        this.lines = lines;
        this.metric = metric;
        this.event = event;
    }
}
