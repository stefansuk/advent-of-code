import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day8Part2 {
    public static void main(String[] args) throws IOException {
        String input = Files.readString(Path.of("2023/Day8Input"));
        Pattern p = Pattern.compile("(\\w+)[=( ]*(\\w+)?[, ]*(\\w+)?");
        Matcher m = p.matcher(input);
        record Pair(String left, String right) {}
        Map<String, Pair> map = new HashMap<>();
        char[] instructions = null;
        if (m.find())
            instructions = m.group().toCharArray();
        while (m.find())
            map.put(m.group(1), new Pair(m.group(2), m.group(3)));
        String[] locations = map.keySet().stream().filter(l -> l.endsWith("A")).toArray(String[]::new);
        int[] loopLengths = new int[locations.length];
        int steps = 0;
        for (int i = 0; i < locations.length; i++) {
            String location = locations[i];
            while (!location.endsWith("Z")) {
                Pair directions = map.get(location);
                location = instructions[steps % instructions.length] == 'L' ? directions.left : directions.right;
                steps++;
            }
            loopLengths[i] = steps;
            steps = 0;
        }
        BigInteger a = BigInteger.valueOf(loopLengths[0]);
        for (int i = 1; i < loopLengths.length; i++) {
            BigInteger b = BigInteger.valueOf(loopLengths[i]);
            a = a.multiply(b).divide(a.gcd(b));
        }
        System.out.println(a);
    }
}
