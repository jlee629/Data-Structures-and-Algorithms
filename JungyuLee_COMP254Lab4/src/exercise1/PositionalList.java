package exercise1;

import java.util.Iterator;

/**
 * Exercise 1 Method
 */
public interface PositionalList<E> extends Iterable<E> {
    int size();

    boolean isEmpty();

    /**
     * Suppose we want to extend the exercise1.PositionalList ADT with a method, indexOf(p),
     * that returns the current index of the element stored at position p.
     * Write this method using only other methods of the exercise1.PositionalList interface (not details of our exercise1.LinkedPositionalList implementation).
     * Write the necessary code to test the method.
     * Hint: Count the steps while traversing the list until encountering position p.
     */
    public default int indexOfPosition(Position<E> p) {

        int count = 0;
        Position<E> walk = first();

        while (walk != null && !walk.equals(p)) {
            walk = after(walk);
            count++;
        }

        if (walk == null) {
            return -1; // not found
        }

        return count;
    }

    // returns the first position in the list
    Position<E> first();

    // returns the last position in the list
    Position<E> last();

    // returns the position immediately before exercise1.Position p
    Position<E> before(Position<E> p) throws IllegalArgumentException;

    // returns the exercise1.Position immediately after exercise1.Position p
    Position<E> after(Position<E> p) throws IllegalArgumentException;

    // inserts an element at the back of the list
    Position<E> addFirst(E e);

    // inserts an element at the back of the list
    Position<E> addLast(E e);

    // inserts an element immediately before the given position
    Position<E> addBefore(Position<E> p, E e) throws IllegalArgumentException;

    // inserts an element immediately after the given position
    Position<E> addAfter(Position<E> p, E e) throws IllegalArgumentException;

    // replaces the element stored at the given position and returns the replaced element
    E set(Position<E> p, E e) throws IllegalArgumentException;

    // removes the element stored at the given exercise1.Position and returns it
    // the given position is invalidated as a result
    E remove(Position<E> p) throws IllegalArgumentException;

    // returns an iterator of the elements stored in the list
    Iterator<E> iterator();

    // returns the positions of the list in an iterable form from first to last
    Iterable<Position<E>> positions();



}
