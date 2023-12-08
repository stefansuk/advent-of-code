import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day8Part1 {
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
        String location = "AAA";
        int steps = 0;
        while (!location.equals("ZZZ")) {
            Pair directions = map.get(location);
            location = instructions[steps % instructions.length] == 'L' ? directions.left : directions.right;
            steps++;
        }
        System.out.println(steps);
    }
}
