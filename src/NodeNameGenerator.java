public class NodeNameGenerator {
    public static String generateName(int nodeNumber) {
        return String.valueOf((char) (65 + nodeNumber));
    }
}
