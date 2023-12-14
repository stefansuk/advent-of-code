import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day12Part2 {
    private static Long[][] cache;

    public static void main(String[] args) throws IOException {
        String input = Files.readString(Path.of("2023/Day12Input"));
        String[] lines = input.lines().toArray(String[]::new);
        Pattern p1 = Pattern.compile("[.#?]+");
        Pattern p2 = Pattern.compile("[\\d,]+");
        long sum = 0;
        for (String line : lines) {
            Matcher m1 = p1.matcher(line);
            Matcher m2 = p2.matcher(line);
            String conditionRecord = "";
            int[] damagedSpringGroups = new int[0];
            if (m1.find())
                conditionRecord = ('?' + m1.group()).repeat(5).substring(1);
            if (m2.find())
                damagedSpringGroups = Arrays.stream((',' + m2.group()).repeat(5).substring(1).split(",")).mapToInt(Integer::parseInt).toArray();
            cache = new Long[conditionRecord.length()][damagedSpringGroups.length];
            sum += findPossibleArrangements(conditionRecord, damagedSpringGroups);
        }
        System.out.println(sum);
    }

    private static long findPossibleArrangements(String conditionRecord, int[] damagedSpringGroups) {
        if (damagedSpringGroups.length == 0 && conditionRecord.contains("#") || Arrays.stream(damagedSpringGroups).sum() + damagedSpringGroups.length - 1 > conditionRecord.length())
            return 0;
        if (damagedSpringGroups.length == 0)
            return 1;
        if (conditionRecord.charAt(0) == '.')
            return findPossibleArrangements(conditionRecord.substring(1), damagedSpringGroups);
        if (cache[conditionRecord.length() - 1][damagedSpringGroups.length - 1] != null)
            return cache[conditionRecord.length() - 1][damagedSpringGroups.length - 1];
        long possibleArrangements = 0;
        if (conditionRecord.length() == damagedSpringGroups[0]
                && conditionRecord.substring(0, damagedSpringGroups[0]).matches("[#?]+"))
            possibleArrangements += findPossibleArrangements(conditionRecord.substring(damagedSpringGroups[0]), Arrays.copyOfRange(damagedSpringGroups, 1, damagedSpringGroups.length));
        if (conditionRecord.length() > damagedSpringGroups[0] && conditionRecord.substring(0, damagedSpringGroups[0] + 1).matches("[#?]+[.?]"))
            possibleArrangements += findPossibleArrangements(conditionRecord.substring(damagedSpringGroups[0] + 1), Arrays.copyOfRange(damagedSpringGroups, 1, damagedSpringGroups.length));
        if (conditionRecord.charAt(0) == '?')
            possibleArrangements += findPossibleArrangements(conditionRecord.substring(1), damagedSpringGroups);
        cache[conditionRecord.length() - 1][damagedSpringGroups.length - 1] = possibleArrangements;
        return possibleArrangements;
    }
}
