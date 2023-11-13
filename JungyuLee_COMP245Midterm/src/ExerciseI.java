
/**
 * Write and test a recursive Java method that takes a character string s and outputs its reverse.
 * For example, the reverse of 'java' would be 'avaj'. Hint: use charAt method of String class.
 */

public class ExerciseI {
    public static String reverseString(String s) {
        if (s.length() <= 1)
            return s;
        else
            return reverseString(s.substring(1)) + s.charAt(0);
    }

        public static void main(String[] args) {
            String s = "java";
            System.out.println(reverseString(s));
        }
}