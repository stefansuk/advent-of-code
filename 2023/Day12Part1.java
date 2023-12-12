import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day12Part1 {
    public static void main(String[] args) throws IOException {
        String input = Files.readString(Path.of("2023/Day12Input"));
        String[] lines = input.lines().toArray(String[]::new);
        Pattern p1 = Pattern.compile("[.#?]+");
        Pattern p2 = Pattern.compile("[\\d,]+");
        int sum = 0;
        for (String line : lines) {
            Matcher m1 = p1.matcher(line);
            Matcher m2 = p2.matcher(line);
            String conditionRecord = "";
            int[] damagedSpringGroups = new int[0];
            if (m1.find())
                conditionRecord = m1.group();
            if (m2.find())
                damagedSpringGroups = Arrays.stream(m2.group().split(",")).mapToInt(Integer::parseInt).toArray();
            ArrayList<ArrayList<Integer>> operationalPossibilities = findOperationalPossibilities(new ArrayList<>(), damagedSpringGroups.length, true, conditionRecord.length() - Arrays.stream(damagedSpringGroups).sum());
            for (ArrayList<Integer> operationalPossibility : operationalPossibilities) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < damagedSpringGroups.length; i++) {
                    stringBuilder.append(".".repeat(operationalPossibility.get(i)));
                    stringBuilder.append("#".repeat(damagedSpringGroups[i]));
                }
                stringBuilder.append(".".repeat(operationalPossibility.getLast()));
                sum = checkArrangement(stringBuilder.toString().toCharArray(), conditionRecord.toCharArray()) ? sum + 1 : sum;
            }
        }
        System.out.println(sum);
    }

    public static ArrayList<ArrayList<Integer>> findOperationalPossibilities(ArrayList<ArrayList<Integer>> operationalPossibilities, int damagedSpringGroupsCount, boolean isFirst, int totalOperational) {
        if (isFirst) {
            for (int i = 0; i <= totalOperational - damagedSpringGroupsCount + 1; i++) {
                operationalPossibilities.add(new ArrayList<>());
                operationalPossibilities.getLast().add(i);
                findOperationalPossibilities(operationalPossibilities, damagedSpringGroupsCount - 1, false, totalOperational - i);
            }
            return operationalPossibilities;
        }
        if (damagedSpringGroupsCount == 0) {
            operationalPossibilities.getLast().add(totalOperational);
            return operationalPossibilities;
        }
        ArrayList<Integer> currentPossibility = new ArrayList<>(operationalPossibilities.getLast());
        for (int i = 1; i <= totalOperational - damagedSpringGroupsCount + 1; i++) {
            if (i != 1)
                operationalPossibilities.add(new ArrayList<>(currentPossibility));
            operationalPossibilities.getLast().add(i);
            findOperationalPossibilities(operationalPossibilities, damagedSpringGroupsCount - 1, false, totalOperational - i);
        }
        return operationalPossibilities;
    }

    public static boolean checkArrangement(char[] arrangement, char[] conditionRecord) {
        for (int i = 0; i < arrangement.length; i++)
            if (arrangement[i] != conditionRecord[i] && conditionRecord[i] != '?')
                return false;
        return true;
    }
}
