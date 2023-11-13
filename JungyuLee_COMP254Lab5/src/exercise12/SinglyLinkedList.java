package exercise12;

public class SinglyLinkedList<E> implements Cloneable {

    // 301236221, Jungyu Lee
    private Node<E> head = null;

    private Node<E> tail = null;

    private int size = 0;

    public SinglyLinkedList() { }

    public int getSize() { return size; }

    private static class Node<E> {
        private E element;
        private Node<E> next;

        public Node(E e, Node<E> n) {
            element = e;
            next = n;
        }

        public E getElement() { return element; }

        public Node<E> getNext() { return next; }

        public void setNext(Node<E> n) { next = n; }
    }

    public boolean isEmpty() { return size == 0; }

    public E getFirstElement() {
        if (isEmpty()) return null;
        return head.getElement();
    }

    public E getLastElement() {
        if (isEmpty()) return null;
        return tail.getElement();
    }

    public void addFirst(E e) {
        head = new Node<>(e, head);
        if (size == 0)
            tail = head;
        size++;
    }

    public void addLast(E e) {
        Node<E> newest = new Node<>(e, null);
        if (isEmpty())
            head = newest;
        else
            tail.setNext(newest);
        tail = newest;
        size++;
    }

    public E removeFirst() {
        if (isEmpty()) return null;
        E answer = head.getElement();
        head = head.getNext();
        size--;
        if (size == 0)
            tail = null;
        return answer;
    }

    @SuppressWarnings({"unchecked"})
    public boolean equals(Object o) {
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        SinglyLinkedList other = (SinglyLinkedList) o;   // use nonparameterized type
        if (size != other.size) return false;
        Node<E> walkA = head;                               // traverse the primary comp254.list
        Node walkB = other.head;                         // traverse the secondary comp254.list
        while (walkA != null) {
            if (!walkA.getElement().equals(walkB.getElement())) return false; //mismatch
            walkA = walkA.getNext();
            walkB = walkB.getNext();
        }
        return true;
    }

    @SuppressWarnings({"unchecked"})
    public SinglyLinkedList<E> clone() throws CloneNotSupportedException {
        // always use inherited Object.clone() to create the initial copy
        SinglyLinkedList<E> other = new SinglyLinkedList<>(); // safe cast
        if (size > 0) {                    // we need independent chain of nodes
            other.head = new Node<>(head.getElement(), null);
            Node<E> walk = head.getNext();      // walk through remainder of original comp254.list
            Node<E> otherTail = other.head;     // remember most recently created node
            while (walk != null) {              // make a new node storing same element
                Node<E> newest = new Node<>(walk.getElement(), null);
                otherTail.setNext(newest);     // link previous node to this one
                otherTail = newest;
                walk = walk.getNext();
            }
        }
        return other;
    }

    public int hashCode() {
        int h = 0;
        for (Node<E> walk = head; walk != null; walk = walk.getNext()) {
            h ^= walk.getElement().hashCode();      // bitwise exclusive-or with element's code
            h = (h << 5) | (h >>> 27);              // 5-bit cyclic shift of composite code
        }
        return h;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        Node<E> walk = head;
        while (walk != null) {
            sb.append(walk.getElement());
            if (walk.getNext() != null)
                sb.append(" -> ");
            walk = walk.getNext();
        }
        sb.append(")");
        return sb.toString();
    }

    private Node<E> searchForNode(String s) {
        Node<E> walk=head;
        while(walk.getNext()!=null) {
            walk=walk.getNext();
            if(walk.getElement().equals(s))
            {
                return walk;
            }
        }
        return null;
    }

    public Node getNodeByIndex(int index) {
        Node walk = head;
        for (int i = 0; i < index; i++) {
            walk = walk.next;
        }
        return walk;
    }

    public Node<E> getNodeByString(String s) {
        Node<E> temp = head;
        for (int i = 0; i < size; i++) {
            if (temp.getElement().equals(s)) {
                return temp;
            }
            temp = temp.getNext();
        }
        return null;
    }

    public void swapTwoNodes(Node<E> node1, Node<E> node2) {
        // no swap
        if (node1 == null || node2 == null || node1 == node2)
            return;

        // curr1 != null means the loop continues until we find node1 or reaches the end of the linked comp254.list
        // if the loop reaches the end, prev1 will be the tail node and curr1 will be null
        Node<E> prev1 = null;
        Node<E> curr1 = head;
        while (curr1 != null && curr1 != node1) {
            prev1 = curr1;
            curr1 = curr1.getNext();
        }

        // curr2 != null means the loop continues until we find node2 or reach the end of the linked comp254.list
        // if the loop reaches the end, prev2 will be the tail node and curr2 will be null
        Node<E> prev2 = null;
        Node<E> curr2 = head;
        while (curr2 != null && curr2 != node2) {
            prev2 = curr2;
            curr2 = curr2.getNext();
        }

        // node1, 2 not found -> no swap
        if (curr1 == null || curr2 == null)
            return;

        // update the prev nodes
        if (prev1 != null)
            prev1.setNext(curr2);
            // prev1 == null -> curr2 is the new head
        else
            head = curr2;

        if (prev2 != null)
            prev2.setNext(curr1);
            // prev2 == null -> curr1 is the new head
        else
            head = curr1;

        // update the next nodes
        Node<E> temp = curr1.getNext();
        curr1.setNext(curr2.getNext());
        curr2.setNext(temp);
    }


    // need copy lists to preserve the original lists
    public void concatenateLists(SinglyLinkedList<E> list1, SinglyLinkedList<E> list2) throws CloneNotSupportedException {
        SinglyLinkedList<E> copyList1 = list1.clone();
        SinglyLinkedList<E> copyList2 = list2.clone();

        if (copyList1.isEmpty()) {
            head = copyList2.head;
            tail = copyList2.tail;
        } else if (copyList2.isEmpty()) {
            head = copyList1.head;
            tail = copyList1.tail;
        } else {
            head = copyList1.head;
            copyList1.tail.setNext(copyList2.head);
            tail = copyList2.tail;
        }
        size = copyList1.getSize() + copyList2.getSize();
    }

    public Node<E> findSecondToLastNode() {
        if (size < 2) {
            return null;  // List has less than 2 nodes
        }

        Node<E> current = head;
        while (current.getNext().getNext() != null) {
            current = current.getNext();
        }

        return current;
    }



    public static void main(String[] args) throws CloneNotSupportedException {

        /*
        SinglyLinkedList<String> list = new SinglyLinkedList<>();
        list.addLast("London");
        list.addLast("Paris");
        list.addLast("Seoul");
        list.addLast("New York");
        list.addLast("Toronto");

        // Before swapping
        System.out.println("Before swapping:");
        System.out.println(list);

        Node node1 = list.getNodeByString("London");
        Node node2 = list.getNodeByString("Toronto");

        list.swapTwoNodes(node1, node2);

        // After swapping
        System.out.println("After swapping:");
        System.out.println(list);


        SinglyLinkedList<String> list1 = new SinglyLinkedList<>();
        list1.addLast("London");
        list1.addLast("Paris");

        SinglyLinkedList<String> list2 = new SinglyLinkedList<>();
        list2.addLast("Seoul");
        list2.addLast("New York");
        list2.addLast("Toronto");

        SinglyLinkedList<String> concatenatedList = new SinglyLinkedList<>();
        concatenatedList.concatenateLists(list1, list2);

        System.out.println(concatenatedList);

        SinglyLinkedList<String> clonedList = concatenatedList.clone();
        System.out.println(concatenatedList.head.element);
        concatenatedList.head.element = "Tokyo";
        System.out.println(concatenatedList);
        System.out.println(clonedList);

        SinglyLinkedList<String> list3 = new SinglyLinkedList<>();
        list3.addLast("London");
        list3.addLast("Paris");
        list3.addLast("Seoul");
        list3.addLast("New York");
        list3.addLast("Toronto");

        Node<String> secondToLastNode = list3.findSecondToLastNode();
        if (secondToLastNode != null) {
            System.out.println("Second-to-last node: " + secondToLastNode.getElement());
        } else {
            System.out.println("List has less than 2 nodes.");
        }
         */

        SinglyLinkedList<Character> palindromeList = new SinglyLinkedList<>();
        palindromeList.addLast('m');
        palindromeList.addLast('o');
        palindromeList.addLast('m');


        boolean isPalindrome = palindromeList.isPalindrome();
        System.out.println("Is palindrome? " + isPalindrome); // Output: Is palindrome? true


    }


    public void insertionSort() {
        if (size <= 1) {
            return; // No need to sort if the list is empty or has only one element
        }

        Node<E> sortedHead = null; // Head of the sorted portion of the list

        Node<E> current = head; // Current node to be inserted into the sorted portion
        while (current != null) {
            Node<E> next = current.getNext(); // Keep a reference to the next node

            if (sortedHead == null || compare(current.getElement(), sortedHead.getElement()) < 0) {
                // If the current node is smaller than the sorted head, make it the new sorted head
                current.setNext(sortedHead);
                sortedHead = current;
            } else {
                // Find the correct position to insert the current node in the sorted portion
                Node<E> sortedCurrent = sortedHead;
                while (sortedCurrent.getNext() != null && compare(current.getElement(), sortedCurrent.getNext().getElement()) >= 0) {
                    sortedCurrent = sortedCurrent.getNext();
                }

                // Insert the current node in the sorted portion
                current.setNext(sortedCurrent.getNext());
                sortedCurrent.setNext(current);
            }

            current = next; // Move to the next node
        }

        head = sortedHead; // Update the head of the list
    }

    @SuppressWarnings("unchecked")
    private int compare(E a, E b) {
        Comparable<? super E> comparableA = (Comparable<? super E>) a;
        return comparableA.compareTo(b);
    }



    public boolean isPalindrome() {
        if (size <= 1) {
            return true; // An empty list or a list with one element is considered a palindrome
        }

        Node<E> slow = head;
        Node<E> fast = head;
        Node<E> prev = null;

        // Find the middle node using the two-pointer technique
        while (fast != null && fast.getNext() != null) {
            fast = fast.getNext().getNext();
            Node<E> next = slow.getNext();
            slow.setNext(prev);
            prev = slow;
            slow = next;
        }

        // If the list has odd number of nodes, move slow pointer one step further
        if (fast != null) {
            slow = slow.getNext();
        }

        // Compare the first half of the list (prev) with the second half (slow)
        while (prev != null && slow != null) {
            if (!prev.getElement().equals(slow.getElement())) {
                return false; // Not a palindrome
            }
            prev = prev.getNext();
            slow = slow.getNext();
        }

        return true; // List is a palindrome
    }
    public void concatenate(SinglyLinkedList<E> list2) {
        if (!list2.isEmpty()) { // no need to concatenate if q2 is empty
            if (isEmpty()) {
                head = list2.head; // q1 is empty
            } else {
                tail.setNext(list2.head);
            }
            tail = list2.tail;
            size += list2.size;
            list2.head = null;
            list2.tail = null;
            list2.size = 0;
        }
    }
}
