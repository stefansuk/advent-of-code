import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day1Part1 {
    public static void main(String[] args) throws IOException {
        String input = Files.readString(Path.of("2023/Day1Input"));
        List<String> lines = input.lines().toList();
        int sum = 0;
        for (String line : lines) {
            char first = '\0', last = '\0';
            boolean firstFound = false;
            for (char c : line.toCharArray()) {
                if (!firstFound && Character.isDigit(c)) {
                    first = c;
                    firstFound = true;
                }
                if (Character.isDigit(c))
                    last = c;
            }
            System.out.println(String.valueOf(first) + last);
            sum += Integer.parseInt(String.valueOf(first) + last);
        }
        System.out.println(sum);
    }
}
