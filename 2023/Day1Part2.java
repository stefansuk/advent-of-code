import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day1Part2 {
    public static void main(String[] args) throws IOException {
        String input = Files.readString(Path.of("2023/Day1Input"));
        List<String> lines = input.lines().toList();
        Map<String, Character> numberMap = Map.of(
                "one", '1',
                "two", '2',
                "three", '3',
                "four", '4',
                "five", '5',
                "six", '6',
                "seven", '7',
                "eight", '8',
                "nine", '9'
        );
        Pattern p = Pattern.compile("one|two|three|four|five|six|seven|eight|nine");
        int sum = 0;
        for (String line : lines) {
            char[] chars = line.toCharArray();
            Matcher m = p.matcher(line);
            int startIndex = 0;
            while (m.find(startIndex)) {
                chars[m.start()] = numberMap.get(m.group());
                startIndex = m.start() + 1;
            }
            char first = '\0', last = '\0';
            boolean firstFound = false;
            for (char c : chars) {
                if (!firstFound && Character.isDigit(c)) {
                    first = c;
                    firstFound = true;
                }
                if (Character.isDigit(c))
                    last = c;
            }
            sum += Integer.parseInt(String.valueOf(first) + last);
        }
        System.out.println(sum);
    }
}
