package Program.Modes;

import java.util.HashMap;

public enum Mode {
    CREATING_NODES,
    CREATING_LINES,
    FINDING_PATH,
    FINDING_TREE;

    private static final HashMap<Mode, String> names = new HashMap<>();

    static {
        names.put(CREATING_NODES, "Creating nodes");
        names.put(CREATING_LINES, "Creating lines");
        names.put(FINDING_PATH, "Finding path");
        names.put(FINDING_TREE, "Finding tree");
    }

    @Override
    public String toString() {
        return names.get(this);
    }
}
