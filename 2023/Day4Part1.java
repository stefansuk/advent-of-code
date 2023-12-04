import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day4Part1 {
    public static void main(String[] args) throws IOException {
        String input = Files.readString(Path.of("2023/Day4Input"));
        List<String> lines = input.lines().toList();
        Pattern p1 = Pattern.compile("(?!.*:)\\d{1,2}(?=.*\\|)");
        Pattern p2 = Pattern.compile("(?!.*\\|)\\d{1,2}");
        int sum = 0;
        for (String line : lines) {
            List<Integer> winningNumbers = new ArrayList<>();
            List<Integer> numbersYouHave = new ArrayList<>();
            Matcher m1 = p1.matcher(line);
            Matcher m2 = p2.matcher(line);
            while (m1.find()) {
                winningNumbers.add(Integer.parseInt(m1.group()));
            }
            while (m2.find()) {
                numbersYouHave.add(Integer.parseInt(m2.group()));
            }
            int winners = 0;
            for (int numberYouHave : numbersYouHave) {
                if (winningNumbers.contains(numberYouHave))
                    winners++;
            }
            sum += (int) Math.pow(2, winners - 1);
        }
        System.out.println(sum);
    }
}
