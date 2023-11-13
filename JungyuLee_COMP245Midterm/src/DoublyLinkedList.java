/**
 * Write and test an efficient Java/Python method for reversing a doubly linked list L
 * using only a constant amount of additional space.
 * Hint: Add the method to DoublyLinkedList class.
 */

// ExerciseII
public class DoublyLinkedList<E> {
    public void reverseList() {
        // reverse no needed
        if (isEmpty() || getSize() == 1) {
            return;
        }

        Node<E> current = header.getNext();
        Node<E> temp;

        while (current != null) {
            temp = current.getNext();

            // swap the prev and next of the current
            current.setNext(current.getPrev());
            current.setPrev(temp);

            // move to next
            current = temp;
        }

        // swap the header and trailer references
        temp = header;
        header = trailer;
        trailer = temp;
    }

    public static void main(String[] args) {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        list.addLast("A");
        list.addLast("B");
        list.addLast("C");

        // original list
        System.out.println("Before Reversed: " + list);
        list.reverseList();
        System.out.println("After Reversed: " + list);
    }
    private static class Node<E> {

        private E element;               // reference to the element stored at this node

        private Node<E> prev;            // reference to the previous node in the comp254.list

        private Node<E> next;            // reference to the subsequent node in the comp254.list

        public Node(E e, Node<E> p, Node<E> n) {
            element = e;
            prev = p;
            next = n;
        }

        public E getElement() { return element; }

        public Node<E> getPrev() { return prev; }

        public Node<E> getNext() { return next; }

        public void setPrev(Node<E> p) { prev = p; }

        public void setNext(Node<E> n) { next = n; }

    }

    private Node<E> header;
    private Node<E> trailer;
    private int size = 0;
    public DoublyLinkedList() {
        header = new Node<>(null, null, null);
        trailer = new Node<>(null, header, null);
        header.setNext(trailer);
    }

    public int getSize() { return size; }

    public boolean isEmpty() { return size == 0; }

    public E first() {
        if (isEmpty()) return null;
        return header.getNext().getElement();   // first element is beyond header
    }

    public E last() {
        if (isEmpty()) return null;
        return trailer.getPrev().getElement();    // last element is before trailer
    }

    public void addFirst(E e) {
        addBetween(e, header, header.getNext());    // place just after the header
    }

    public void addLast(E e) {
        addBetween(e, trailer.getPrev(), trailer);  // place just before the trailer
    }

    public E removeFirst() {
        if (isEmpty()) return null;                  // nothing to remove
        return remove(header.getNext());             // first element is beyond header
    }

    public E removeLast() {
        if (isEmpty()) return null;                  // nothing to remove
        return remove(trailer.getPrev());            // last element is before trailer
    }

    private void addBetween(E e, Node<E> predecessor, Node<E> successor) {
        // create and link a new node
        Node<E> newest = new Node<>(e, predecessor, successor);
        predecessor.setNext(newest);
        successor.setPrev(newest);
        size++;
    }

    private E remove(Node<E> node) {
        Node<E> predecessor = node.getPrev();
        Node<E> successor = node.getNext();
        predecessor.setNext(successor);
        successor.setPrev(predecessor);
        size--;
        return node.getElement();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> walk = header.getNext();
        while (walk != trailer) {
            sb.append(walk.getElement());
            walk = walk.getNext();
            if (walk != trailer)
                sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
