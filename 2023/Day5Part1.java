import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Day5Part1 {
    public static void main(String[] args) throws IOException {
        String input = Files.readString(Path.of("2023/Day5Input"));
        String[] sections = input.substring(7).split("[a-z\\s-]*:\\s");
        record Triplet(long destinationRangeStart, long sourceRangeStart, int rangeLength) {}
        long[] seeds = Arrays.stream(sections[0].split("\\s")).mapToLong(Long::parseLong).toArray();
        Triplet[][] maps = new Triplet[sections.length - 1][];
        for (int i = 0; i < sections.length - 1; i++) {
            String[] section = sections[i + 1].split("\\s");
            maps[i] = new Triplet[section.length / 3];
            for (int j = 0; j < section.length; j += 3) {
                maps[i][j / 3] = new Triplet(
                        Long.parseLong(section[j]),
                        Long.parseLong(section[j + 1]),
                        Integer.parseInt(section[j + 2]));
            }
        }
        long location = Long.MAX_VALUE;
        for (long seed : seeds) {
            for (Triplet[] map : maps)
                for (Triplet triplet : map)
                    if (seed >= triplet.sourceRangeStart && seed < triplet.sourceRangeStart + triplet.rangeLength) {
                        seed = seed - triplet.sourceRangeStart + triplet.destinationRangeStart;
                        break;
                    }
            if (seed < location)
                location = seed;
        }
        System.out.println(location);
    }
}
