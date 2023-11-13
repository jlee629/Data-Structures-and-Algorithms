package exercise2;

import java.util.Scanner;

public class Exercise2 {
    public static boolean isPalindrome(String s) {
        // base case: an empty string or a string with a single character is a palindrome
        if (s.length() <= 1) {
            return true;
        }

        // check the equality of the first and last characters
        if (s.charAt(0) != s.charAt(s.length() - 1)) {
            return false;
        }

        // check recursively if the substring excluding the first and last characters is a palindrome
        String subString = s.substring(1, s.length() - 1);
        return isPalindrome(subString);
    }

    public static void main(String[] args) {
        // "", "a", "racecar", "level", "hello"
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String input = scanner.nextLine();

        if (isPalindrome(input)) {
            System.out.println("A palindrome");
        } else {
            System.out.println("Not a palindrome");
        }

        scanner.close();
    }
}
