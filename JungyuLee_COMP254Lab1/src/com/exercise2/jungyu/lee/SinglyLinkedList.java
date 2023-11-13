package com.exercise2.jungyu.lee;
/**
 Exercise 2 - concatenateLists
 */
public class SinglyLinkedList<E> implements Cloneable {

    // 301236221, Jungyu Lee
    public static void main(String[] args) throws CloneNotSupportedException {
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
        System.out.println(clonedList);
    }

    public void concatenateLists(SinglyLinkedList<E> list1, SinglyLinkedList<E> list2) {
        if (list1.isEmpty()) {
            head = list2.head;
            tail = list2.tail;
        } else if (list2.isEmpty()) {
            head = list1.head;
            tail = list1.tail;
        } else {
            head = list1.head;
            list1.tail.setNext(list2.head);
            tail = list2.tail;
        }
        size = list1.getSize() + list2.getSize();
    }

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
        Node<E> walkA = head;                               // traverse the primary list
        Node walkB = other.head;                         // traverse the secondary list
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
            Node<E> walk = head.getNext();      // walk through remainder of original list
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

        // curr1 != null means the loop continues until we find node1 or reaches the end of the linked list
        // if the loop reaches the end, prev1 will be the tail node and curr1 will be null
        Node<E> prev1 = null;
        Node<E> curr1 = head;
        while (curr1 != null && curr1 != node1) {
            prev1 = curr1;
            curr1 = curr1.getNext();
        }

        // curr2 != null means the loop continues until we find node2 or reach the end of the linked list
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


}
