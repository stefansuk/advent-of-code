import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day2Part2 {
    public static void main(String[] args) throws IOException {
        String input = Files.readString(Path.of("2023/Day2Input"));
        List<String> lines = input.lines().toList();
        Pattern p = Pattern.compile("\\d+ (red|green|blue)");
        record Pair(int amount, String colour) {}
        int sum = 0;
        for (String line : lines) {
            Matcher m = p.matcher(line);
            List<Pair> pairs = new ArrayList<>();
            while (m.find()) {
                String[] amountColourPair = m.group().split(" ");
                pairs.add(new Pair(Integer.parseInt(amountColourPair[0]), amountColourPair[1]));
            }
            int maxRedCubes = Collections.max(pairs.stream()
                    .filter(pair -> pair.colour.equals("red"))
                    .mapToInt(pair -> pair.amount)
                    .boxed()
                    .toList());
            int maxGreenCubes = Collections.max(pairs.stream()
                    .filter(pair -> pair.colour.equals("green"))
                    .mapToInt(pair -> pair.amount)
                    .boxed()
                    .toList());
            int maxBlueCubes = Collections.max(pairs.stream()
                    .filter(pair -> pair.colour.equals("blue"))
                    .mapToInt(pair -> pair.amount)
                    .boxed()
                    .toList());
            sum += maxRedCubes * maxGreenCubes * maxBlueCubes;
        }
        System.out.println(sum);
    }
}
