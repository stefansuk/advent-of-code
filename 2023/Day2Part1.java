import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day2Part1 {
    public static void main(String[] args) throws IOException {
        String input = Files.readString(Path.of("2023/Day2Input"));
        String[] lines = input.lines().toArray(String[]::new);
        Pattern p = Pattern.compile("^(?:(?!\\d([3-9] red|[4-9] green|[5-9] blue)).)+$");
        int sum = 0;
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            Matcher m = p.matcher(line);
            if (m.find())
                sum += i + 1;
        }
        System.out.println(sum);
    }
}
