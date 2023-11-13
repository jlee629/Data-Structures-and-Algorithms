package exercise3;

// Jungyu Lee, 301236221
public class Exercise3 {
    public static int findMissingNumber(int[] A) {
        int i = A.length + 1; // include the missing number
        int sum = (i * (i - 1)) / 2; // the sum of the arithmetic progression (including the missing number)
        int arraySum = 0; // the sum of the A (excluding the missing number)

        for (int number : A) {
            arraySum += number; // get the sum of the A
        }

        return sum - arraySum; // the sum of the arithmetic progression - the sum of the A = the missing number
    }

    public static void main(String[] args) {
        int[] array = {0, 1, 2, 3, 4, 5, 7, 8, 9}; // Example array with a missing number
        System.out.println("Missing number: " + findMissingNumber(array));
    }
}

