// InputValidator.java
import java.util.Scanner;

public class InputValidator {
    private static final Scanner scanner = new Scanner(System.in);

    // recursive integer reader
    public static int getInt(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("That's not a valid integer. Try again.");
            return getInt(prompt); // recursive re-prompt
        }
    }

    // recursive non-empty string reader
    public static String getNonEmptyString(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            System.out.println("Input cannot be empty. Try again.");
            return getNonEmptyString(prompt);
        }
        return input;
    }

    // recursive choice within a range [min, max]
    public static int getIntInRange(String prompt, int min, int max) {
        int val = getInt(prompt);
        if (val < min || val > max) {
            System.out.printf("Please enter a number between %d and %d.%n", min, max);
            return getIntInRange(prompt, min, max);
        }
        return val;
    }
}

