package program.modes;

import java.util.HashMap;

public enum Metric {
    HOPS,
    CONGESTION,
    DISTANCE;

    private static final HashMap<Metric, String> names = new HashMap<>();

    static {
        names.put(HOPS, "Hops");
        names.put(CONGESTION, "Congestion");
        names.put(DISTANCE, "Distance");
    }

    @Override
    public String toString() {
        return names.get(this);
    }
}
