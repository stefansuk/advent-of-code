import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day14Part2 {
    public static void main(String[] args) throws IOException {
        String input = Files.readString(Path.of("2023/Day14Input"));
        String[] lines = input.lines().toArray(String[]::new);
        List<String[]> platformLayouts = new ArrayList<>();
        int loopStart = 0;
        int loopLength = 0;
        cycle: for (long i = 0; i < 4000000000L; i++) {
            for (int j = 0; j < platformLayouts.size(); j++) {
                if (Arrays.equals(lines, platformLayouts.get(j))) {
                    loopStart = j;
                    loopLength = (int) i - j;
                    break cycle;
                }
            }
            platformLayouts.add(lines);
            lines = shift(rotate(lines));
        }
        for (int i = 0; i < (4000000000L - loopStart) % loopLength; i++)
            lines = shift(rotate(lines));
        lines = rotate(lines);
        int sum = 0;
        for (String line : lines)
            for (int i = 0; i < lines.length; i++)
                if (line.charAt(i) == 'O')
                    sum += i + 1;
        System.out.println(sum);
    }

    private static String[] rotate(String[] lines) {
        char[][] inputChars = Arrays.stream(lines).map(String::toCharArray).toArray(char[][]::new);
        char[][] outputChars = new char[inputChars[0].length][inputChars.length];
        for (int i = 0; i < inputChars.length; i++)
            for (int j = 0; j < inputChars[0].length; j++)
                outputChars[j][i] = inputChars[inputChars.length - 1 - i][j];
        return Arrays.stream(outputChars).map(String::new).toArray(String[]::new);
    }

    private static String[] shift(String[] lines) {
        for (int i = 0; i < lines.length; i++)
            lines[i] = String.join("#", Arrays.stream(lines[i].split("#", -1))
                    .map(s -> {
                        char[] c = s.toCharArray();
                        Arrays.sort(c);
                        return new String(c);
                    }).toArray(String[]::new));
        return lines;
    }
}
