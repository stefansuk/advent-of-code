import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day6Part2 {
    public static void main(String[] args) throws IOException {
        String input = Files.readString(Path.of("2023/Day6Input"));
        List<String> lines = input.lines().toList();
        long time = Integer.parseInt(lines.get(0).substring(13).replaceAll("\\D+", ""));
        long distance = Long.parseLong(lines.get(1).substring(12).replaceAll("\\D+", ""));
        long lowest = ((int) (time - Math.sqrt(time * time - 4 * distance)) >> 1) + 1;
        long winners = time - (lowest << 1) + 1;
        System.out.println(winners);
    }
}
