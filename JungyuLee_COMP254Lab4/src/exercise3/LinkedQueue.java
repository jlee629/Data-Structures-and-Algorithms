package exercise3;

/**
 * Implement a method with signature concatenate(LinkedQueue<E> Q2) for the LinkedQueue<E> class
 * that takes all elements of Q2 and appends them to the end of the original queue.
 * The operation should run in O(1) time and should result in Q2 being an empty queue.
 * Write the necessary code to test the method.
 * Hint: You may just modify the SinglyLinkedList class to add necessary support.
 */
public class LinkedQueue<E> implements Queue<E> {

    public void concatenate(LinkedQueue<E> Q2) {
        list.concatenate(Q2.list);
    }

    public static void main(String[] args) {
        LinkedQueue<String> queue1 = new LinkedQueue<>();
        queue1.enqueue("A");
        queue1.enqueue("B");
        queue1.enqueue("C");

        LinkedQueue<String> queue2 = new LinkedQueue<>();
        queue2.enqueue("D");
        queue2.enqueue("E");

        System.out.println("Before: ");
        System.out.println("Queue1: " + queue1);
        System.out.println("Queue2: " + queue2);

        queue1.concatenate(queue2);

        System.out.println("After: ");
        System.out.println("Queue1: " + queue1);
        System.out.println("Queue2: " + queue2);
    }
    private SinglyLinkedList<E> list = new SinglyLinkedList<>();
    public LinkedQueue() {}

    @Override
    public int size() {
        return list.getSize();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void enqueue(E e) {
        list.addLast(e);
    }

    @Override
    public E first() {
        return list.getFirstElement();
    }

    @Override
    public E dequeue() {
        return list.removeFirst();
    }

    public String toString() {
        return list.toString();
    }
}
