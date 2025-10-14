import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Lotto {
    public static void main(String[] args) {
        Random random = new Random();
        Set<Integer> numbers = new HashSet<>();


        while (numbers.size() < 6) {
            int number = random.nextInt(49) + 1;
            numbers.add(number);
        }


        System.out.println("Wylosowane liczby Lotto: " + numbers);
    }
}