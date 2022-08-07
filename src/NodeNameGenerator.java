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
        StringBuilder reversedStr = new StringBuilder(resultString.toString());
        reversedStr.reverse();
        return reversedStr.toString();
    }
}
