import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day10Part1 {
    public static void main(String[] args) throws IOException {
        String input = Files.readString(Path.of("2023/Day10Input"));
        char[][] tiles = input.lines().map(String::toCharArray).toArray(char[][]::new);
        int y = input.indexOf('S') / tiles[0].length;
        int x = input.indexOf('S') % (tiles[0].length + 1);
        char lastDirection;
        if (y > 0 && (tiles[y - 1][x] == '7' || tiles[y - 1][x] == '|' || tiles[y - 1][x] == 'F')) {
            lastDirection = 'N'; y--;
        } else if (x > 0 && (tiles[y][x - 1] == 'L' || tiles[y][x - 1] == '-' || tiles[y][x - 1] == 'F')) {
            lastDirection = 'W'; x--;
        } else {
            lastDirection = 'E'; x++;
        }
        int steps = 1;
        while (tiles[y][x] != 'S') {
            switch (tiles[y][x]) {
                case '7' -> { if (lastDirection == 'N') { lastDirection = 'W'; x--; } else { lastDirection = 'S'; y++; } }
                case 'F' -> { if (lastDirection == 'N') { lastDirection = 'E'; x++; } else { lastDirection = 'S'; y++; } }
                case 'L' -> { if (lastDirection == 'W') { lastDirection = 'N'; y--; } else { lastDirection = 'E'; x++; } }
                case 'J' -> { if (lastDirection == 'E') { lastDirection = 'N'; y--; } else { lastDirection = 'W'; x--; } }
                case '|' -> { if (lastDirection == 'N') { y--; } else { y++; } }
                case '-' -> { if (lastDirection == 'W') { x--; } else { x++; } }
            }
            steps++;
        }
        System.out.println(steps >> 1);
    }
}
