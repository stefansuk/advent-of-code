import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Day15Part2 {
    public static void main(String[] args) throws IOException {
        String input = Files.readString(Path.of("2023/Day15Input"));
        Pattern p = Pattern.compile("(\\w+)([=-])(\\d?)");
        Matcher m = p.matcher(input);
        List<ArrayList<Object[]>> boxes = IntStream.range(0, 256)
                .mapToObj(b -> new ArrayList<Object[]>())
                .toList();
        while (m.find()) {
            String label = m.group(1);
            String operation = m.group(2);
            if (operation.equals("=")) {
                int focalLength = Integer.parseInt(m.group(3));
                boxes.get(hash(label)).stream()
                        .filter(b -> b[0].equals(label))
                        .findFirst()
                        .ifPresentOrElse(
                                b -> b[1] = focalLength,
                                () -> boxes.get(hash(label)).add(new Object[] { label, focalLength }));
            } else
                boxes.get(hash(label)).removeIf(a -> a[0].equals(label));
        }
        int sum = 0;
        for (int i = 0; i < boxes.size(); i++) {
            for (int j = 0; j < boxes.get(i).size(); j++) {
                sum += (i + 1) * (j + 1) * (int) boxes.get(i).get(j)[1];
            }
        }
        System.out.println(sum);
    }

    private static int hash(String label) {
        int currentValue = 0;
        for (char c : label.toCharArray()) {
            currentValue += c;
            currentValue *= 17;
            currentValue %= 256;
        }
        return currentValue;
    }
}
