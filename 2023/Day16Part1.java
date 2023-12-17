import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day16Part1 {
    private static char[][] contraption;
    private static char[][] visitedTiles;
    private static final int up = 0, left = 1, down = 2, right = 3;

    public static void main(String[] args) throws IOException {
        String input = Files.readString(Path.of("2023/Day16Input"));
        contraption = input.lines().map(String::toCharArray).toArray(char[][]::new);
        visitedTiles = new char[contraption.length][contraption[0].length];
        move(0, 0, right);
        int sum = 0;
        for (char[] line : visitedTiles)
            for (char c : line)
                if (c != '\0')
                    sum++;
        System.out.println(sum);
    }

    private static void move(int x, int y, int direction) {
        while (true) {
            if (y < 0 || x < 0 || y >= visitedTiles.length || x >= visitedTiles[0].length
                    || visitedTiles[y][x] == '#'
                    || visitedTiles[y][x] == 'v' && (direction == up || direction == down)
                    || visitedTiles[y][x] == 'h' && (direction == left || direction == right))
                return;
            switch (contraption[y][x]) {
                case '/' -> direction ^= 3;
                case '\\' -> direction ^= 1;
                case '|' -> {
                    if (direction == left || direction == right) {
                        visitedTiles[y][x] = '#';
                        move(x, y - 1, up);
                        move(x, y + 1, down);
                        return;
                    }
                }
                case '-' -> {
                    if (direction == up || direction == down) {
                        visitedTiles[y][x] = '#';
                        move(x - 1, y, left);
                        move(x + 1, y, right);
                        return;
                    }
                }
            }
            if (contraption[y][x] == '/' || contraption[y][x] == '\\')
                visitedTiles[y][x] = 'm';
            else if (visitedTiles[y][x] == '\0')
                visitedTiles[y][x] = direction == up || direction == down ? 'v' : 'h';
            else if (visitedTiles[y][x] != '#')
                visitedTiles[y][x] = '#';
            x += (direction - 2) & -(direction & 1);
            y += (direction - 1) & -(~direction & 1);
        }
    }
}
