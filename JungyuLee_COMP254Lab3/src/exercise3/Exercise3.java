package exercise3;

import java.util.Scanner;

public class Exercise3 {

    public static boolean hasMoreVowels(String s) {
        int vowelCount = countVowels(s);
        int consonantCount = s.length() - vowelCount;
        return vowelCount > consonantCount;
    }

    private static int countVowels(String s) {
        // base case
        if (s.isEmpty()) {
            return 0;
        }

        char firstChar = Character.toLowerCase(s.charAt(0));
        int count = isVowel(firstChar) ? 1 : 0;

        // recursively count vowels in the remaining substring
        return count + countVowels(s.substring(1));
    }

    private static boolean isVowel(char c) {
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String input = "";
        boolean isValid = false;

        // allow alpha characters only
        while (!isValid) {
            System.out.print("Enter a string: ");
            input = scanner.nextLine();

            if (input.matches("[a-zA-Z]+")) {
                isValid = true;
            } else {
                System.out.println("Invalid input! Please enter only alphabetic characters.");
            }
        }

        if (hasMoreVowels(input)) {
            System.out.println("The string has more vowels than consonants.");
        } else {
            System.out.println("The string does not have more vowels than consonants.");
        }

        scanner.close();
    }
}


