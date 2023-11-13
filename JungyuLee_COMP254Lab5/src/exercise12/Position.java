package exercise12;

/**
 * an interface for a position which is an abstraction
 * for the location at which a single element is stored in a positional container
 */
public interface Position<E> {
    // returns the element stored at this position
    E getElement() throws IllegalArgumentException;

}
