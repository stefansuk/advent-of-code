import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Day6Part1 {
    public static void main(String[] args) throws IOException {
        String input = Files.readString(Path.of("2023/Day6Input"));
        List<String> lines = input.lines().toList();
        int[] times = Arrays.stream(lines.get(0).substring(13).split("\\s+")).mapToInt(Integer::parseInt).toArray();
        int[] distances = Arrays.stream(lines.get(1).substring(12).split("\\s+")).mapToInt(Integer::parseInt).toArray();
        int product = 1;
        for (int i = 0; i < times.length; i++) {
            int lowest = ((int) (times[i] - Math.sqrt(times[i] * times[i] - 4 * distances[i])) >> 1) + 1;
            product *= times[i] - (lowest << 1) + 1;
        }
        System.out.println(product);
    }
}
