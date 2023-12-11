import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day11Part2 {
    public static void main(String[] args) throws IOException {
        String input = Files.readString(Path.of("2023/Day11Input"));
        Pattern p1 = Pattern.compile("(?m)^\\.*$");
        Matcher m1 = p1.matcher(input);
        List<Integer> emptyRows = new ArrayList<>();
        while (m1.find())
            emptyRows.add(m1.start() / (input.indexOf('\n') + 1));
        input = transpose(input);
        m1 = p1.matcher(input);
        List<Integer> emptyColumns = new ArrayList<>();
        while (m1.find())
            emptyColumns.add(m1.start() / (input.indexOf('\n') + 1));
        Pattern p2 = Pattern.compile("#");
        Matcher m2 = p2.matcher(input);
        List<int[]> galaxies = new ArrayList<>();
        while (m2.find())
            galaxies.add(new int[] { m2.start() / (input.indexOf('\n') + 1), m2.start() % (input.indexOf('\n') + 1)});
        long sum = 0;
        for (int i = 0; i < galaxies.size(); i++)
            for (int j = i + 1; j < galaxies.size(); j++) {
                sum += Math.abs(galaxies.get(i)[0] - galaxies.get(j)[0]) + Math.abs(galaxies.get(i)[1] - galaxies.get(j)[1]);
                for (int emptyColumn : emptyColumns)
                    sum += emptyColumn > Math.min(galaxies.get(i)[0], galaxies.get(j)[0]) && emptyColumn < Math.max(galaxies.get(i)[0], galaxies.get(j)[0]) ? 999999 : 0;
                for (int emptyRow : emptyRows)
                    sum += emptyRow > Math.min(galaxies.get(i)[1], galaxies.get(j)[1]) && emptyRow < Math.max(galaxies.get(i)[1], galaxies.get(j)[1]) ? 999999 : 0;
            }
        System.out.println(sum);
    }

    private static String transpose(String string) {
        char[][] inputChars = string.lines().map(String::toCharArray).toArray(char[][]::new);
        char[][] outputChars = new char[inputChars[0].length][inputChars.length];
        for (int i = 0; i < inputChars.length; i++)
            for (int j = 0; j < inputChars[0].length; j++)
                outputChars[j][i] = inputChars [i][j];
        return Arrays.stream(outputChars).map(String::new).collect(Collectors.joining("\n"));
    }
}
