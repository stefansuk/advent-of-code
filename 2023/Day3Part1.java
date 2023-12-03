import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3Part1 {
    public static void main(String[] args) throws IOException {
        String input = Files.readString(Path.of("2023/Day3Input"));
        List<String> lines = input.lines().toList();
        String symbols = "#$%&*+-/=@";
        Pattern p = Pattern.compile("\\d+");
        int sum = 0;
        for (int i = 0; i < lines.size(); i++) {
            boolean checkTop = i > 0;
            boolean checkBottom = i < lines.size() - 1;
            String line = lines.get(i);
            Matcher m = p.matcher(line);
            while (m.find()) {
                boolean addValue = false;
                for (int j = m.start(); j < m.end(); j++) {
                    boolean checkLeft = j > 0;
                    boolean checkRight = j < line.length() - 1;
                    if (checkTop && (checkLeft && symbols.contains(lines.get(i - 1).substring(j - 1, j))
                            || symbols.contains(lines.get(i - 1).substring(j, j + 1))
                            || checkRight && symbols.contains(lines.get(i - 1).substring(j + 1, j + 2)))) {
                        addValue = true;
                        break;
                    }
                    if ((checkLeft && symbols.contains(line.substring(j - 1, j)))
                            || (checkRight && symbols.contains(line.substring(j + 1, j + 2)))) {
                        addValue = true;
                        break;
                    }
                    if (checkBottom && (checkLeft && symbols.contains(lines.get(i + 1).substring(j - 1, j))
                            || symbols.contains(lines.get(i + 1).substring(j, j + 1))
                            || checkRight && symbols.contains(lines.get(i + 1).substring(j + 1, j + 2)))) {
                        addValue = true;
                        break;
                    }
                }
                if (addValue)
                    sum += Integer.parseInt(m.group());
            }
        }
        System.out.println(sum);
    }
}
