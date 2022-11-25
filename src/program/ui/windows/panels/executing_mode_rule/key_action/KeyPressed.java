package program.ui.windows.panels.executing_mode_rule.key_action;

import program.elements.element.Status;
import program.elements.element.Elements;
import program.elements.line.Line;
import program.elements.node.Node;
import program.ui.windows.main.Window;
import program.ui.windows.panels.executing_mode_rule.executable.Executable;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;

public class KeyPressed {

    private final HashMap<Integer, Executable> keyExecutableMap = new HashMap<>();

    private final KeyEvent event;
    private final List<Node> nodes;
    private final List<Line> lines;

    public KeyPressed(List<Node> nodes, List<Line> lines, KeyEvent event) {
        this.event = event;
        this.nodes = nodes;
        this.lines = lines;

        keyExecutableMap.put(KeyEvent.VK_ESCAPE, new KeyEscapePressed());
        keyExecutableMap.put(KeyEvent.VK_ENTER, new KeyEnterPressed());
        keyExecutableMap.put(KeyEvent.VK_DELETE, new KeyDeletePressed());
        keyExecutableMap.put(KeyEvent.VK_A, new KeyAPressed());
    }


    public void executeRuleForCurrentKey(int keyValue) {
        keyExecutableMap.getOrDefault(keyValue, () -> {
        }).execute();
    }


    private class KeyEscapePressed implements Executable {
        @Override
        public void execute() {
            Elements.setStatusForAllElements(Status.NONE, nodes, lines);
        }
    }

    private class KeyEnterPressed implements Executable {
        @Override
        public void execute() {
            if (event.isControlDown()) {
                SwingUtilities.getWindowAncestor(Window.getMainPanel()).dispose();
                Window.changeFullscreen();
                ((JFrame) SwingUtilities.getWindowAncestor(Window.getMainPanel())).setUndecorated(Window.isFullScreenEnabled());
                SwingUtilities.getWindowAncestor(Window.getMainPanel()).setVisible(true);
            }
        }
    }

    private class KeyDeletePressed implements Executable {
        @Override
        public void execute() {
            Elements.deleteElements(nodes, lines);
        }
    }

    private class KeyAPressed implements Executable {
        @Override
        public void execute() {
            if (event.isControlDown()) {
                Elements.setStatusForAllElements(Status.ACTIVE, nodes, lines);
            }
        }
    }

}
