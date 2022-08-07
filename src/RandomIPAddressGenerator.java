import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomIPAddressGenerator {
    private static List<String> usedIPAddresses = new ArrayList<>();

    public static String getIP() {
        String generatedIP = generateIP();
        while (usedIPAddresses.contains(generatedIP)){
            generatedIP = generateIP();
        }
        return generatedIP;
    }
    /**This method is for JDK 14+ version*/
//    private static String generateIP() {
//        Random random = new Random();
//
//        return switch (random.nextInt(5)) {
//            case 0 -> (random.nextInt(9) + 1) + ".0.0.0";
//            case 1 -> (random.nextInt(116) + 11) + ".0.0.0";
//            case 2 -> (random.nextInt(40) + 129) + "." + random.nextInt(255) + ".0.0";
//            case 3 -> (random.nextInt(3) + 170) + "." + random.nextInt(16) + ".0.0";
//            case 4 -> (random.nextInt(25) + 199) + "." + random.nextInt(256) + "." + random.nextInt(256) + ".0";
//            default -> "";
//        };
//    }
    private static String generateIP() {
        Random random = new Random();
        switch (random.nextInt(5)) {
            case 0: {
                return (random.nextInt(9 - 1 + 1) + 1) + ".0.0.0";
            }
            case 1: {
                return (random.nextInt(126 - 11 + 1) + 11) + ".0.0.0";
            }
            case 2: {
                return (random.nextInt(168 - 129 + 1) + 129) + "." + random.nextInt(255) + ".0.0";
            }
            case 3: {
                return (random.nextInt(172 - 170 + 1) + 170) + "." + random.nextInt(16) + ".0.0";
            }
            case 4: {
                return (random.nextInt(223 - 199 + 1) + 199) + "." + random.nextInt(256) + "." + random.nextInt(256) + ".0";
            }
        }
        return "";
    }
}
