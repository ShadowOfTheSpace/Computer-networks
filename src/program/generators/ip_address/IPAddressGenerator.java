package program.generators.ip_address;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class IPAddressGenerator {
    private static final Random random = new Random();
    private static final List<String> usedIPAddresses = new ArrayList<>();

    private static final HashMap<Integer, Generatable> randomIPAddressClassMap = new HashMap<>();

    static {
        randomIPAddressClassMap.put(0, new GenerateFirstClass());
        randomIPAddressClassMap.put(1, new GenerateSecondClass());
        randomIPAddressClassMap.put(2, new GenerateThirdClass());
        randomIPAddressClassMap.put(3, new GenerateFourthClass());
        randomIPAddressClassMap.put(4, new GenerateFifthClass());
    }

    public static String generateIPAddress() {
        String randomIPAddress;
        do {
            Generatable randomClass = randomIPAddressClassMap.get(random.nextInt(5));
            randomIPAddress = randomClass.generateIPAddress();
        } while (usedIPAddresses.contains(randomIPAddress));
        usedIPAddresses.add(randomIPAddress);
        return randomIPAddress;
    }

    static class GenerateFirstClass implements Generatable {
        @Override
        public String generateIPAddress() {
            return (random.nextInt(9) + 1) + ".0.0.0";
        }
    }

    static class GenerateSecondClass implements Generatable {
        @Override
        public String generateIPAddress() {
            return (random.nextInt(116) + 11) + ".0.0.0";
        }
    }

    static class GenerateThirdClass implements Generatable {
        @Override
        public String generateIPAddress() {
            return (random.nextInt(40) + 129) + "." + random.nextInt(255) + ".0.0";
        }
    }

    static class GenerateFourthClass implements Generatable {
        @Override
        public String generateIPAddress() {
            return (random.nextInt(3) + 170) + "." + random.nextInt(16) + ".0.0";
        }
    }

    static class GenerateFifthClass implements Generatable {
        @Override
        public String generateIPAddress() {
            return (random.nextInt(25) + 199) + "." + random.nextInt(256) + "." + random.nextInt(256) + ".0";
        }
    }
}
