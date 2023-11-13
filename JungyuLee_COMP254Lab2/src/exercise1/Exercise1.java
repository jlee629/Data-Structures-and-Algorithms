package exercise1;

// Jungyu Lee, 301236221
class Exercise1 {

    /**
     *  a) O(n) - linear
     *  The method uses a single loop that iterates through the array once,
     *  performing a constant amount of work in each iteration.
     */
    public static int example1(int[] arr) {
        int n = arr.length, total = 0;
        for (int j=0; j < n; j++)
            total += arr[j]; // loop from 0 to n-1
        return total;
    }

    /**
     * b) O(n) - linear
     * The method uses a loop that increments the index by 2 in each iteration.
     * This means it only processes half of the elements in the array O(n/2).
     * However, we ignore constant factors in Big O notation, it is the same as O(n).
     */
    public static int example2(int[] arr) {
        int n = arr.length, total = 0;
        for (int j=0; j < n; j += 2) // note the increment of 2
            total += arr[j];
        return total;
    }

    /** c) O(n^2) - quadratic
     * The method contains nested loops where the inner loop depends on the outer loop.
     * For each element in the array, the method iterates through all the previous elements.
     * So, it is n*(n+1)/2, but we ignore constant factors in Big O notation, it is the same as O(n^2).
     * */
    public static int example3(int[] arr) {
        int n = arr.length, total = 0;
        for (int j=0; j < n; j++)       // loop from 0 to n-1
            for (int k=0; k <= j; k++)    // loop from 0 to j
                total += arr[j];
        return total;
    }

    /** d) O(n) - linear
     * The method uses a single loop that iterates through the array once.
     * In each iteration, it performs a constant amount of work.
     *   prefix += arr[j];
     *   total += prefix;
     * It is O(2n), but we ignore constant factors in Big O notation, it is the same as O(n).
     */
    public static int example4(int[] arr) {
        int n = arr.length, prefix = 0, total = 0;
        for (int j=0; j < n; j++) {     // loop from 0 to n-1
            prefix += arr[j];
            total += prefix;
        }
        return total;
    }

    /**
     *  e) O(n^3) - cubic
     *  The method contains three nested loops.
     *  The outer loop iterates through the first array, and for each element,
     *  the two inner loops iterate through all the previous elements.
     *  It is n * n * (n*(n+1)/2), but we drop the constants, which becomes O(n^3).
     */
    public static int example5(int[] first, int[] second) { // assume equal-length arrays
        int n = first.length, count = 0;
        for (int i=0; i < n; i++) {     // loop from 0 to n-1
            int total = 0;
            for (int j=0; j < n; j++)     // loop from 0 to n-1
                for (int k=0; k <= j; k++)  // loop from 0 to j
                    total += first[k];
            if (second[i] == total) count++;
        }
        return count;
    }
}
