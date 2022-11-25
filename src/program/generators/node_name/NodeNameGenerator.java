package program.generators.node_name;

public class NodeNameGenerator {
    public static String generateName(int nodeNumber) {
        int letterValue;
        StringBuilder resultString = new StringBuilder();
        do {
            letterValue = nodeNumber % 26;
            if (letterValue != 0) {
                resultString.append((char) (letterValue + 'A' - 1));
            } else {
                resultString.append("Z");
                nodeNumber--;
            }
            nodeNumber /= 26;
        } while (nodeNumber > 0);
        return new StringBuilder(resultString.toString())
                .reverse()
                .toString();
    }
}
