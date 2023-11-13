import java.util.PriorityQueue;

/**
 * Write and test an efficient Java/Python method for finding the ten largest elements in an array of size n.
 * What is the running time of your algorithm?
 * Hint: Use an auxiliary array to store indices of largest elements and ignore previous found elements.
 * Note that 10 is a constant
 */

public class ExerciseIII {
    public static int[] findTenLargestElements(int[] arr) {
        if (arr.length < 10) {
            throw new IllegalArgumentException("array size should be at least 10");
        }

        PriorityQueue<Integer> pq = new PriorityQueue<>();

        for (int num : arr) {
            if (pq.size() < 10) { // insertion: 10 - constant
                pq.offer(num);
            } else if (num > pq.peek()) { // insertion: log n
                pq.poll();
                pq.offer(num);
            }
        }

        int[] largestElements = new int[10];
        for (int i = 9; i >= 0; i--) {
            largestElements[i] = pq.poll(); // removal: log n
        }

        return largestElements;
    }


    public static void main(String[] args) {
        int[] array = {100, 99, 34, 22, 11, 90, 87, 27, 63, 5, 20, 30, 45, 22, 11, 10, 8, 37, 27};
        int[] tenLargest = findTenLargestElements(array);
        System.out.println("Ten Largest Elements: ");
        for (int num : tenLargest) {
            System.out.print(num + " ");
        }
    }
}
