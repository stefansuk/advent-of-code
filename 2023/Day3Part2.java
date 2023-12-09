import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3Part2 {
    private static String[] lines;

    public static void main(String[] args) throws IOException {
        String input = Files.readString(Path.of("2023/Day3Input"));
        lines = input.lines().toArray(String[]::new);
        Pattern p = Pattern.compile("\\*");
        int sum = 0;
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            Matcher m = p.matcher(line);
            while (m.find()) {
                int j = m.start();
                List<Integer> numbers = new ArrayList<>();
                if (isDigit(i - 1, j))
                    numbers.add(findNumber(i - 1, j));
                else {
                    if (isDigit(i - 1, j - 1))
                        numbers.add(findNumber(i - 1, j - 1));
                    if (isDigit(i - 1, j + 1))
                        numbers.add(findNumber(i - 1, j + 1));
                }
                if (isDigit(i, j - 1))
                    numbers.add(findNumber(i, j - 1));
                if (isDigit(i, j + 1))
                    numbers.add(findNumber(i, j + 1));
                if (isDigit(i + 1, j))
                    numbers.add(findNumber(i + 1, j));
                else {
                    if (isDigit(i + 1, j - 1))
                        numbers.add(findNumber(i + 1, j - 1));
                    if (isDigit(i + 1, j + 1))
                        numbers.add(findNumber(i + 1, j + 1));
                }
                if (numbers.size() == 2)
                    sum += numbers.get(0) * numbers.get(1);
            }
        }
        System.out.println(sum);
    }

    private static boolean isDigit(int line, int index) {
        return Character.isDigit(lines[line].charAt(index));
    }

    private static int findNumber(int line, int index) {
        char[] chars = lines[line].substring(index - 2, index + 3).toCharArray();
        String numString = "";
        if (Character.isDigit(chars[1])) {
            if (Character.isDigit(chars[0]))
                numString += chars[0];
            numString += chars[1];
        }
        numString += chars[2];
        if (Character.isDigit(chars[3])) {
            numString += chars[3];
            if (Character.isDigit(chars[4]))
                numString += chars[4];
        }
        return Integer.parseInt(numString);
    }
}
