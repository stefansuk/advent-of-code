import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day11Part1 {
    public static void main(String[] args) throws IOException {
        String input = Files.readString(Path.of("2023/Day11Input"));
        input = input.replaceAll("(?m)^\\.*$", "$0\n$0");
        input = transpose(input);
        input = input.replaceAll("(?m)^\\.*$", "$0\n$0");
        Pattern p = Pattern.compile("#");
        Matcher m = p.matcher(input);
        List<int[]> galaxies = new ArrayList<>();
        while (m.find())
            galaxies.add(new int[] { m.start() / (input.indexOf('\n') + 1), m.start() % (input.indexOf('\n') + 1)});
        int sum = 0;
        for (int i = 0; i < galaxies.size(); i++)
            for (int j = i + 1; j < galaxies.size(); j++)
                sum += Math.abs(galaxies.get(i)[0] - galaxies.get(j)[0]) + Math.abs(galaxies.get(i)[1] - galaxies.get(j)[1]);
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
