package exercise2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class UnsortedTableMap<K,V> extends AbstractMap<K,V> {
    /** Underlying storage for the map of entries. */
    private ArrayList<MapEntry<K,V>> table = new ArrayList<>();

    /** Constructs an initially empty map. */
    public UnsortedTableMap() { }

    // private utility
    /** Returns the index of an entry with equal key, or -1 if none found. */
    private int findIndex(K key) {
        int n = table.size();
        for (int j=0; j < n; j++)
            if (table.get(j).getKey().equals(key))
                return j;
        return -1;                                   // special value denotes that key was not found
    }

    public boolean containsKey(K key) {
        int index = findIndex(key);
        if (index == -1)
            return false;  // key not found
        return true;  // key found
    }

    // public methods
    /**
     * Returns the number of entries in the map.
     * @return number of entries in the map
     */
    @Override
    public int size() { return table.size(); }

    /**
     * Returns the value associated with the specified key, or null if no such entry exists.
     * @param key  the key whose associated value is to be returned
     * @return the associated value, or null if no such entry exists
     */
    @Override // O(n)
    public V get(K key) {
        int j = findIndex(key);
        if (j == -1) return null;                         // not found
        return table.get(j).getValue();
    }

    /**
     * Associates the given value with the given key. If an entry with
     * the key was already in the map, this replaced the previous value
     * with the new one and returns the old value. Otherwise, a new
     * entry is added and null is returned.
     * @param key    key with which the specified value is to be associated
     * @param value  value to be associated with the specified key
     * @return the previous value associated with the key (or null, if no such entry)
     */
    @Override // O(n)
    public V put(K key, V value) {
        int j = findIndex(key);
        if (j == -1) {
            table.add(new MapEntry<>(key, value));          // add new entry
            return null;
        } else                                            // key already exists
            return table.get(j).setValue(value);            // replaced value is returned
    }

    /**
     * Removes the entry with the specified key, if present, and returns its value.
     * Otherwise does nothing and returns null.
     * @param key  the key whose entry is to be removed from the map
     * @return the previous value associated with the removed key, or null if no such entry exists
     */
    @Override // O(n)
    public V remove(K key) {
        int j = findIndex(key);
        int n = size();
        if (j == -1) return null;                         // not found
        V answer = table.get(j).getValue();
        if (j != n - 1)
            table.set(j, table.get(n-1));                   // relocate last entry to 'hole' created by removal
        table.remove(n-1);                                // remove last entry of table
        return answer;
    }

    //---------------- nested EntryIterator class ----------------
    private class EntryIterator implements Iterator<Entry<K,V>> {
        private int j=0;
        public boolean hasNext() { return j < table.size(); }
        public Entry<K,V> next() {
            if (j == table.size()) throw new NoSuchElementException("No further entries");
            return table.get(j++);
        }
        public void remove() { throw new UnsupportedOperationException("remove not supported"); }
    } //----------- end of nested EntryIterator class -----------

    //---------------- nested EntryIterable class ----------------
    private class EntryIterable implements Iterable<Entry<K,V>> {
        public Iterator<Entry<K,V>> iterator() { return new EntryIterator(); }
    } //----------- end of nested EntryIterable class -----------

    /**
     * Returns an iterable collection of all key-value entries of the map.
     *
     * @return iterable collection of the map's entries
     */
    @Override
    public Iterable<Entry<K,V>> entrySet() { return new EntryIterable(); }

    /**
     * The remove(k,v) method is used to remove an entry from a map.
     * This method spends time to locate an existing item with the given key.
     * Write an efficient version of this method,
     * removeOnlyIfNull(k,v), that removes the entry from the map only if there is null value with a key k.
     * Provide Java/Python implementation of removeOnlyIfNull(k,v) method for the UnSortedTableMap class.
     * Write the testing code in the main method.
     * Hint: Your solution should check if the value associated with key is null then remove,
     * otherwise return the existing value and say value not null .
     */
    public V removeOnlyIfNull(K key) {
        int j = findIndex(key);
        if (j == -1) { // key not found
            System.out.println("key not found and nothing to remove");
            return null;
        }
        V value = table.get(j).getValue();
        if (value == null) { // when the value is null
            int n = size() - 1;
            if (j != n) {
                table.set(j, table.get(n)); // fill the hole with the last entry
            }
            table.remove(n-1); // remove last entry of table O(1)
            // arrayList takes O(n), so replacing is better
            System.out.println("value null and removed");
            return null;
        } else {
            System.out.println("value not null");
            return value;
        }
    }

    public static void main(String[] args) {
        UnsortedTableMap<String, String> unsortedTableMap = new UnsortedTableMap<>();

        unsortedTableMap.put("A", null);
        unsortedTableMap.put("B", "5");

        System.out.println("Testing (A, null) ");
        System.out.println(unsortedTableMap.removeOnlyIfNull("A"));
        System.out.println();

        System.out.println("Testing ('B', '5')");
        System.out.println(unsortedTableMap.removeOnlyIfNull("B"));
        System.out.println();

        System.out.println("Testing ('C', '3') (not in map)");
        System.out.println(unsortedTableMap.removeOnlyIfNull("C"));
        System.out.println();

        System.out.println("Testing ('D', null) (not in map)");
        System.out.println(unsortedTableMap.removeOnlyIfNull("D"));
    }


    public V removeOnlyIfNull(K key, V value) {
        int j = findIndex(key);
        if (j == -1) { // key not found
            System.out.println("key not found and nothing to remove");
            return null;
        }
        V actualValue = table.get(j).getValue();
        if (actualValue == null && value == null) {
            int n = size() - 1;
            if (j != n) {
                table.set(j, table.get(n)); // fill the hole with the last entry
            }
            table.remove(n-1); // remove last entry of table O(1)
            System.out.println("value null and removed");
            return null; // return null since that was the value associated with key
        } else {
            System.out.println("value not null");
            return actualValue;
        }
    }


    /*
    public static void main(String[] args) {
        UnsortedTableMap<String, String> unsortedTableMap = new UnsortedTableMap<>();

        unsortedTableMap.put("A", null);
        unsortedTableMap.put("B", "5");

        System.out.println("Testing (A, null) ");
        System.out.println(unsortedTableMap.removeOnlyIfNull("A"));
        System.out.println(unsortedTableMap.removeOnlyIfNull("A", null));
        System.out.println();

        System.out.println("Testing ('B', '5')");
        System.out.println(unsortedTableMap.removeOnlyIfNull("B"));
        System.out.println(unsortedTableMap.removeOnlyIfNull("B", "5"));
        System.out.println();

        System.out.println("Testing ('C', '3') (not in map)");
        System.out.println(unsortedTableMap.removeOnlyIfNull("C"));
        System.out.println();

        System.out.println("Testing ('D', null) (not in map)");
        System.out.println(unsortedTableMap.removeOnlyIfNull("D"));
    }

     */
}
