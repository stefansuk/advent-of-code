import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day15Part1 {
    public static void main(String[] args) throws IOException {
        String input = Files.readString(Path.of("2023/Day15Input"));
        String[] codes = input.split(",");
        int sum = 0;
        for (String code : codes) {
            int currentValue = 0;
            for (char c : code.toCharArray()) {
                currentValue += c;
                currentValue *= 17;
                currentValue %= 256;
            }
            sum += currentValue;
        }
        System.out.println(sum);
    }
}
