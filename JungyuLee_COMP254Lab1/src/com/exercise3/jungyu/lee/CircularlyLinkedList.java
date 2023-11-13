package com.exercise3.jungyu.lee;
/**
 Exercise 3 - clone
 */
public class CircularlyLinkedList<E> implements Cloneable {
    //301236221, Jungyu Lee
    public static void main(String[] args) throws CloneNotSupportedException {
        CircularlyLinkedList<String> list = new CircularlyLinkedList<String>();
        list.addLast("London");
        list.addLast("Paris");
        list.addLast("Seoul");
        list.addLast("New York");
        list.addLast("Toronto");
        System.out.println(list);

        CircularlyLinkedList<String> clonedList = list.clone();
        list.tail.element = "Tokyo";
        System.out.println(list);

        System.out.println(clonedList);

/*
        CircularlyLinkedList<String> list2 = new CircularlyLinkedList<String>();

        CircularlyLinkedList<String> clonedList2 = list2.clone();
        System.out.println(clonedList2);

*/
    }



    @SuppressWarnings({"unchecked"})
    public CircularlyLinkedList<E> clone() throws CloneNotSupportedException {

        CircularlyLinkedList<E> other = new CircularlyLinkedList<>();

        if (size > 0) {
            Node<E> otherTail = new Node<>(tail.getElement(), null);
            other.tail = otherTail;
            Node<E> walk = tail.getNext();
            Node<E> otherTailNext = otherTail;

            while (walk != tail) {
                Node<E> newNode = new Node<>(walk.getElement(), null);
                otherTailNext.setNext(newNode);
                otherTailNext = newNode;
                walk = walk.getNext();
            }
            otherTailNext.setNext(otherTail);
        }
        return other;
    }

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

    private Node<E> tail = null;
    private int size = 0;
    public CircularlyLinkedList() { }

    public int getSize() { return size; }

    public boolean isEmpty() { return size == 0; }

    public E first() {
        if (isEmpty()) return null;
        return tail.getNext().getElement();
    }

    public E last() {
        if (isEmpty()) return null;
        return tail.getElement();
    }

    public void rotate() {
        if (tail != null)
            tail = tail.getNext();
    }

    public void addFirst(E e) {
        if (size == 0) {
            tail = new Node<>(e, null);
            tail.setNext(tail);
        } else {
            Node<E> newest = new Node<>(e, tail.getNext());
            tail.setNext(newest);
        }
        size++;
    }

    public void addLast(E e) {
        addFirst(e);
        tail = tail.getNext();
    }

    public E removeFirst() {
        if (isEmpty()) return null;
        Node<E> head = tail.getNext();
        if (head == tail) tail = null;
        else tail.setNext(head.getNext());
        size--;
        return head.getElement();
    }

    public String toString() {
        if (tail == null) return "()";
        StringBuilder sb = new StringBuilder("(");
        Node<E> walk = tail;
        do {
            walk = walk.getNext();
            sb.append(walk.getElement());
            if (walk != tail)
                sb.append(" -> ");
        } while (walk != tail);
        sb.append(")");
        return sb.toString();
    }

    private int sizeCustom() {
        // write logic to traverse and counter to maintain count of node in the list
        Node walk = this.tail.getNext();
        int sizeEx = 0;
        do {
            walk=walk.getNext();
            sizeEx++;
        } while (walk != tail.getNext());

        return sizeEx;
    }
}

