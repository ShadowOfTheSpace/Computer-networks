package program.ui.windows.panels.executing_mode_rule.mouse_action;

import program.modes.Mode;
import program.ui.windows.panels.executing_mode_rule.executable.Executable;

import java.util.HashMap;

public class MouseAction {
    protected final HashMap<Mode, Executable> modeExecuteRuleMap = new HashMap<>();

    protected final Executable creatingNodes;
    protected final Executable creatingLines;
    protected final Executable findingPath;
    protected final Executable findingTree;

    public MouseAction(Executable creatingNodes, Executable creatingLines, Executable findingPath, Executable findingTree) {
        this.creatingNodes = creatingNodes;
        this.creatingLines = creatingLines;
        this.findingPath = findingPath;
        this.findingTree = findingTree;

        modeExecuteRuleMap.put(Mode.CREATING_NODES, creatingNodes);
        modeExecuteRuleMap.put(Mode.CREATING_LINES, creatingLines);
        modeExecuteRuleMap.put(Mode.FINDING_PATH, findingPath);
        modeExecuteRuleMap.put(Mode.FINDING_TREE, findingTree);
    }

    public void executeRuleForMouseAction(Mode currentMode) {
        modeExecuteRuleMap.getOrDefault(currentMode, () -> {
        }).execute();
    }
}
