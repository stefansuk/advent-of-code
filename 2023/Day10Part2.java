import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day10Part2 {
    public static void main(String[] args) throws IOException {
        String input = Files.readString(Path.of("2023/Day10Input"));
        char[][] tiles = input.lines().map(String::toCharArray).toArray(char[][]::new);
        int y = input.indexOf('S') / (tiles[0].length + 1);
        int x = input.indexOf('S') % (tiles[0].length + 1);
        char lastDirection;
        if (y > 0 && (tiles[y - 1][x] == '7' || tiles[y - 1][x] == '|' || tiles[y - 1][x] == 'F')) {
            lastDirection = 'N'; y--;
        } else if (x > 0 && (tiles[y][x - 1] == 'L' || tiles[y][x - 1] == '-' || tiles[y][x - 1] == 'F')) {
            lastDirection = 'W'; x--;
        } else {
            lastDirection = 'E'; x++;
        }
        char startingDirection = lastDirection;
        while (tiles[y][x] != 'S') {
            switch (tiles[y][x]) {
                case '7' -> { tiles[y][x] = '1'; if (lastDirection == 'N') { lastDirection = 'W'; x--; } else { lastDirection = 'S'; y++; } }
                case 'F' -> { tiles[y][x] = '2'; if (lastDirection == 'N') { lastDirection = 'E'; x++; } else { lastDirection = 'S'; y++; } }
                case 'L' -> { tiles[y][x] = '3'; if (lastDirection == 'W') { lastDirection = 'N'; y--; } else { lastDirection = 'E'; x++; } }
                case 'J' -> { tiles[y][x] = '4'; if (lastDirection == 'E') { lastDirection = 'N'; y--; } else { lastDirection = 'W'; x--; } }
                case '|' -> { tiles[y][x] = '5'; if (lastDirection == 'N') { y--; } else { y++; } }
                case '-' -> { tiles[y][x] = '6'; if (lastDirection == 'W') { x--; } else { x++; } }
            }
        }
        switch (lastDirection) {
            case 'N' -> tiles[y][x] = startingDirection == 'W' ? '1' : startingDirection == 'E' ? '2' : '5';
            case 'W' -> tiles[y][x] = startingDirection == 'N' ? '3' : '6';
            case 'E' -> tiles[y][x] = '4';
        }
        Pattern p = Pattern.compile("(5|36*1|26*4)(.*?)(5|36*1|26*4)");
        Matcher m = p.matcher(Arrays.stream(tiles).map(String::new).collect(Collectors.joining("\n")));
        int enclosedTiles = 0;
        while (m.find()) {
            enclosedTiles += m.group(2).replaceAll("[1-6]", "").length();
        }
        System.out.println(enclosedTiles);
    }
}
