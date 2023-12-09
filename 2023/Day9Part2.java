import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Day9Part2 {
    public static void main(String[] args) throws IOException {
        String input = Files.readString(Path.of("2023/Day9Input"));
        int[][] lines = input.lines()
                .map(l -> Arrays.stream(l.split("\\s"))
                        .mapToInt(Integer::parseInt)
                        .toArray())
                .map(array -> IntStream.range(0, array.length)
                        .mapToObj(i -> array[array.length - i - 1])
                        .mapToInt(Integer::intValue)
                        .toArray())
                .toArray(int[][]::new);
        int sum = 0;
        for (int[] line : lines) {
            int curSeqLength = line.length - 1;
            while (!Arrays.stream(line, 0, curSeqLength).allMatch(n -> n == 0)) {
                for (int i = 0; i < curSeqLength; i++)
                    line[i] = line[i + 1] - line[i];
                curSeqLength--;
            }
            sum += Arrays.stream(line).sum();
        }
        System.out.println(sum);
    }
}
