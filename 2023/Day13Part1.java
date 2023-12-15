import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day13Part1 {
    private record MirrorCandidate(int lineNumber, char type) {}
    private static List<MirrorCandidate> mirrorCandidates = new ArrayList<>();
    private static String[] rows;
    private static String[] columns;
    private static boolean mirrorFound = false;

    public static void main(String[] args) throws IOException {
        String input = Files.readString(Path.of("2023/Day13Input"));
        String[] patterns = input.split("\n\n");
        String[] transposedPatterns = Arrays.stream(patterns).map(Day13Part1::transpose).toArray(String[]::new);
        int sum = 0;
        for (int i = 0; i < patterns.length; i++) {
            rows = patterns[i].lines().toArray(String[]::new);
            columns = transposedPatterns[i].lines().toArray(String[]::new);
            findMirrorCandidates(rows, 'r');
            findMirrorCandidates(columns, 'c');
            int offset = 1;
            while (mirrorCandidates.size() > 1) {
                for (int j = 0; j < mirrorCandidates.size(); j++) {
                    MirrorCandidate mirrorCandidate = mirrorCandidates.get(j);
                    if (checkCandidate(mirrorCandidate, offset, mirrorCandidate.type)) {
                        mirrorCandidates = new ArrayList<>() {{ add(mirrorCandidate); }};
                        break;
                    }
                }
                offset++;
            }
            sum += mirrorCandidates.getFirst().type == 'r' ? mirrorCandidates.getFirst().lineNumber * 100 : mirrorCandidates.getFirst().lineNumber;
            mirrorCandidates.clear();
            mirrorFound = false;
        }
        System.out.println(sum);
    }

    private static String transpose(String string) {
        char[][] inputChars = string.lines().map(String::toCharArray).toArray(char[][]::new);
        char[][] outputChars = new char[inputChars[0].length][inputChars.length];
        for (int i = 0; i < inputChars.length; i++)
            for (int j = 0; j < inputChars[0].length; j++)
                outputChars[j][i] = inputChars[i][j];
        return Arrays.stream(outputChars).map(String::new).collect(Collectors.joining("\n"));
    }

    private static void findMirrorCandidates(String[] lines, char type) {
        if (!mirrorFound)
            for (int j = 1; j < lines.length; j++)
                if (lines[j].equals(lines[j - 1])) {
                    if (j == 1 || j == lines.length - 1) {
                        mirrorCandidates.clear();
                        mirrorCandidates.add(new MirrorCandidate(j, type));
                        mirrorFound = true;
                        return;
                    }
                    mirrorCandidates.add(new MirrorCandidate(j, type));
                }
    }

    private static boolean checkCandidate(MirrorCandidate mirrorCandidate, int offset, char type) {
        if (mirrorCandidate.lineNumber - offset < 1 || mirrorCandidate.lineNumber + offset >= (type == 'r' ? rows : columns).length)
            return true;
        if (!(type == 'r' ? rows : columns)[mirrorCandidate.lineNumber - 1 - offset].equals((type == 'r' ? rows : columns)[mirrorCandidate.lineNumber + offset])) {
            mirrorCandidates.remove(mirrorCandidate);
            return false;
        }
        return false;
    }
}
