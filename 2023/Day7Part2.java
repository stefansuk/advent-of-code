import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day7Part2 {
    public static void main(String[] args) throws IOException {
        String input = Files.readString(Path.of("2023/Day7Input"));
        Map<Character, Integer> cardMap = new HashMap<>() {{
            put('A', 12);
            put('K', 11);
            put('Q', 10);
            put('T', 9);
            put('9', 8);
            put('8', 7);
            put('7', 6);
            put('6', 5);
            put('5', 4);
            put('4', 3);
            put('3', 2);
            put('2', 1);
            put('J', 0);
        }};
        Pattern p = Pattern.compile("(\\w{5})\\s(\\d+)");
        Matcher m = p.matcher(input);
        record Pair(int score, int bid) {}
        Pair[] pairs = new Pair[1000];
        for (int i = 0; i < 1000; i++) {
            if (m.find()) {
                String cards = m.group(1);
                Map<Character, Integer> cardCount = new HashMap<>();
                cardCount.put('J', 0);
                cards.chars().forEach(c -> cardCount.put((char) c, cardCount.getOrDefault((char) c, 0) + 1));
                int score = 0;
                if (cardCount.containsValue(5)
                        || cardCount.containsValue(4) && cardCount.get('J') == 1
                        || cardCount.containsValue(3) && cardCount.get('J') == 2
                        || cardCount.containsValue(2) && cardCount.get('J') == 3
                        || cardCount.containsValue(1) && cardCount.get('J') == 4)
                    score = 2227758;
                else if (cardCount.containsValue(4)
                        || cardCount.containsValue(3) && cardCount.get('J') == 1
                        || cardCount.values().stream().filter(count -> count == 2).count() == 2 && cardCount.get('J') == 2
                        || cardCount.containsValue(1) && cardCount.get('J') == 3)
                    score = 1856465;
                else if (cardCount.containsValue(3) && cardCount.containsValue(2)
                        || cardCount.values().stream().filter(count -> count == 2).count() == 2 && cardCount.get('J') == 1)
                    score = 1485172;
                else if (cardCount.containsValue(3)
                        || cardCount.containsValue(2) && cardCount.get('J') == 1
                        || cardCount.containsValue(1) && cardCount.get('J') == 2)
                    score = 1113879;
                else if (cardCount.values().stream().filter(count -> count == 2).count() == 2)
                    score = 742586;
                else if (cardCount.containsValue(2)
                        || cardCount.containsValue(1) && cardCount.get('J') == 1)
                    score = 371293;
                int[] cardValues = cards.chars().mapToObj(c -> (char) c).mapToInt(cardMap::get).toArray();
                pairs[i] = new Pair(score + cardValues[0] * 28561 + cardValues[1] * 2197 + cardValues[2] * 169 + cardValues[3] * 13 + cardValues[4], Integer.parseInt(m.group(2)));
            }
        }
        Arrays.sort(pairs, Comparator.comparingInt(pair -> pair.score));
        AtomicInteger i = new AtomicInteger(1);
        int sum = Arrays.stream(pairs).mapToInt(pair -> pair.bid * i.getAndIncrement()).sum();
        System.out.println(sum);
    }
}
