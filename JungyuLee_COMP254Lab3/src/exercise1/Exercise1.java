package exercise1;

public class Exercise1 {
    public static int recursiveMultiply1(int m, int n) {
        if (n == 0) { // base case
            return 0;
        } else if (n > 0) {
            return m + recursiveMultiply(m, n - 1);
        } else { // n < 0
            return -m + recursiveMultiply(m, n + 1);
        }
    }

    public static int recursiveMultiply(int m, int n) {
        if (n == 0) { // base case
            return 0;
        } else if (n > 0) {
            if (n > m) {
                // Swap the values of m and n if n is greater than m
                int temp = m;
                m = n;
                n = temp;
            }
            return m + recursiveMultiply(m, n - 1);
        } else { // n < 0
            return -recursiveMultiply(m, -n);
        }
    }

    public static void main(String[] args) {
        // first test
        int m1 = 4, n1 = 0;
        int result1 = recursiveMultiply(m1, n1);
        System.out.println("Result 1: " + result1);

        // second test
        int m2 = 0, n2 = 3;
        int result2 = recursiveMultiply(m2, n2);
        System.out.println("Result 2: " + result2);

        // third test
        int m3 = -3, n3 = -4;
        int result3 = recursiveMultiply(m3, n3);
        System.out.println("Result 3: " + result3);

        // fourth test
        int m4 = -3, n4 = 3;
        int result4 = recursiveMultiply(m4, n4);
        System.out.println("Result 4: " + result4);
    }
}



