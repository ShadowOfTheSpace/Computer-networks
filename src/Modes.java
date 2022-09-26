import java.util.HashMap;

public enum Modes {
    CREATING_NODES,
    CREATING_LINES,
    FINDING_PATH,
    FINDING_SHORTEST_PATH_TREE;

    private static HashMap<Modes, String> names = new HashMap<>();

    static {
        names.put(CREATING_NODES, "Creating nodes");
        names.put(CREATING_LINES, "Creating lines");
        names.put(FINDING_PATH, "Finding path");
        names.put(FINDING_SHORTEST_PATH_TREE, "Finding tree");
    }

    @Override
    public String toString() {
        return names.get(this);
    }
}
