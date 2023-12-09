import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day6Part2 {
    public static void main(String[] args) throws IOException {
        String input = Files.readString(Path.of("2023/Day6Input"));
        String[] lines = input.lines().toArray(String[]::new);
        long time = Integer.parseInt(lines[0].substring(13).replaceAll("\\D+", ""));
        long distance = Long.parseLong(lines[1].substring(12).replaceAll("\\D+", ""));
        long lowest = ((int) (time - Math.sqrt(time * time - 4 * distance)) >> 1) + 1;
        long winners = time - (lowest << 1) + 1;
        System.out.println(winners);
    }
}
