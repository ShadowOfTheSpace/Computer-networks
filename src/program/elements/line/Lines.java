package program.elements.line;

import program.elements.element.Status;
import program.elements.node.Nodes;
import program.modes.Metric;
import program.ui.windows.information.LineInformationWindow;
import program.ui.windows.main.Window;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class Lines {
    private static Line nonExistingLine;

    public static Line getNonExistingLine() {
        if (nonExistingLine == null) {
            nonExistingLine = new Line();
        }
        return nonExistingLine;
    }

    public static List<Line> getLinesByStatus(Status status, List<Line> lines) {
        return lines.stream()
                .filter(line -> line.hasStatus(status))
                .collect(Collectors.toList());
    }


    public static Line getLineByStatus(Status status, List<Line> lines) {
        return lines.stream()
                .filter(line -> line.hasStatus(status))
                .findFirst()
                .orElse(getNonExistingLine());
    }

    public static Line getLineByPoint(Point point, List<Line> lines) {
        return lines.stream()
                .filter(line -> line.containsPoint(point))
                .findFirst()
                .orElse(getNonExistingLine());
    }

    public static int getLengthOfLine(Line line, Metric metric) {
        if (metric == Metric.CONGESTION) {
            Window.getLineInfoWindows().add(new LineInformationWindow(line));
        } else if (metric == Metric.DISTANCE) {
            return Nodes.calculateDistance(line.getStartNode(), line.getEndNode());
        }
        return 1;
    }
}
