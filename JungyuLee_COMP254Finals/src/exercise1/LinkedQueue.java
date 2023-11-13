package exercise1;



public class LinkedQueue<E> implements Queue<E> {
    public void concatenate(LinkedQueue<E> Q2) {
        list.concatenate(Q2.list);
    }

    private SinglyLinkedList<E> list = new SinglyLinkedList<>();
    public LinkedQueue() {} // new comp254.queue relies on the initially empty comp254.list

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

    public static void main(String[] args) {
        LinkedQueue<Integer> queue = new LinkedQueue<>();

        // Testing enqueue method
        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);
        System.out.println("Queue: " + queue); // Output: [10, 20, 30]

        // Testing size and isEmpty methods
        System.out.println("Size: " + queue.size()); // Output: 3
        System.out.println("Is Empty: " + queue.isEmpty()); // Output: false

        // Testing first method
        System.out.println("First element: " + queue.first()); // Output: 10

        // Testing dequeue method
        System.out.println("Dequeued element: " + queue.dequeue()); // Output: 10
        System.out.println("Queue after dequeue: " + queue); // Output: [20, 30]

        // Testing enqueue after dequeue
        queue.enqueue(40);
        System.out.println("Queue after enqueue: " + queue); // Output: [20, 30, 40]

        LinkedQueue<String> queue1 = new LinkedQueue<>();
        queue1.enqueue("A");
        queue1.enqueue("B");
        queue1.enqueue("C");

        System.out.println(queue1);
        System.out.println(queue1.list);

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
}
