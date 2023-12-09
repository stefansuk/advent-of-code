import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day2Part2 {
    public static void main(String[] args) throws IOException {
        String input = Files.readString(Path.of("2023/Day2Input"));
        String[] lines = input.lines().toArray(String[]::new);
        Pattern p = Pattern.compile("(\\d+) (red|green|blue)");
        int sum = 0;
        for (String line : lines) {
            int maxRedCubes = 0, maxGreenCubes = 0, maxBlueCubes = 0;
            Matcher m = p.matcher(line);
            while (m.find()) {
                int amount = Integer.parseInt(m.group(1));
                String colour = m.group(2);
                if (colour.equals("red") && amount > maxRedCubes)
                    maxRedCubes = amount;
                if (colour.equals("green") && amount > maxGreenCubes)
                    maxGreenCubes = amount;
                if (colour.equals("blue") && amount > maxBlueCubes)
                    maxBlueCubes = amount;
            }
            sum += maxRedCubes * maxGreenCubes * maxBlueCubes;
        }
        System.out.println(sum);
    }
}
