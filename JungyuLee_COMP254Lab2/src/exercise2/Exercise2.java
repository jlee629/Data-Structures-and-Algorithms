package exercise2;

public class Exercise2 {
    public static void main(String[] args) {
        int[] inputSizes = {50000, 100000, 200000, 400000, 800000, 1600000}; // Input sizes to test
        // prefixAverage2
        System.out.println("***prefixAverage2 Test***");
        for (int n : inputSizes) {
            double[] arr = new double[n];
            for (int i = 0; i < n; i++) {
                arr[i] = i + 1; // input values from 1 to n
            }

            long startTime2 = System.nanoTime();
            prefixAverage2(arr);
            long timeTaken2 = System.nanoTime() - startTime2;
            System.out.println("n: " + n + ", Time taken:" + timeTaken2 + " nanoseconds");
        }
        // prefixAverage1
        System.out.println();
        System.out.println("***prefixAverage1 Test***");
        for (int n : inputSizes) {
            double[] arr = new double[n];
            for (int i = 0; i < n; i++) {
                arr[i] = i + 1; // input values from 1 to n
            }

            long startTime1 = System.nanoTime();
            prefixAverage1(arr);
            long timeTaken1 = System.nanoTime() - startTime1;

            System.out.println("n: " + n + ", Time taken:" + timeTaken1 + " nanoseconds");
        }
    }

    // O(n^2)
    public static double[] prefixAverage1(double[] x) {
        int n = x.length;
        double[] a = new double[n];    // filled with zeros by default
        for (int j=0; j < n; j++) {
            double total = 0;            // begin computing x[0] + ... + x[j]
            for (int i=0; i <= j; i++)
                total += x[i];
            a[j] = total / (j+1);        // record the average
        }
        return a;
    }

    // O(n)
    public static double[] prefixAverage2(double[] x) {
        int n = x.length;
        double[] a = new double[n];    // filled with zeros by default
        double total = 0;              // compute prefix sum as x[0] + x[1] + ...
        for (int j=0; j < n; j++) {
            total += x[j];               // update prefix sum to include x[j]
            a[j] = total / (j+1);        // compute average based on current sum
        }
        return a;
    }
}
