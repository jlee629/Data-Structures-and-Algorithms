package exercise2;

import java.util.ArrayList;
import java.util.Random;

public class ProbeHashMap<K,V> extends AbstractHashMap<K,V> {
    private MapEntry<K,V>[] table; // a fixed array of entries (all initially null)
    private MapEntry<K,V> DEFUNCT = new MapEntry<>(null, null); //sentinel

    public ProbeHashMap() { super(); }

    public ProbeHashMap(int cap, double maxLoadFactor) { super(cap, maxLoadFactor); }

    public ProbeHashMap(int cap, int p, double maxLoadFactor) { super(cap, p, maxLoadFactor); }
    // Creates an empty table having length equal to current capacity.
    protected void createTable( ) {
        table = (MapEntry<K,V>[ ]) new MapEntry[capacity]; // safe cast
    }

    // Returns true if location is either empty or the ”defunct” sentinel.
    private boolean isAvailable(int j) {
        return (table[j] == null || table[j] == DEFUNCT);
    }

    // Returns index with key k, or −(a+1) such that k could be added at index a.
    private int findSlot(int h, K k) {
        int avail = -1; // no slot available (thus far)
        int j = h; // index while scanning table
        do {
            if (isAvailable(j)) { // may be either empty or defunct
                if (avail == -1) avail = j; // this is the first available slot!
                if (table[j] == null) break; // if empty, search fails immediately
            } else if (table[j].getKey().equals(k))
                return j; // successful match
            j = (j + 1) % capacity; // keep looking (cyclically)
        } while (j != h); // stop if we return to the start
        return -(avail + 1); // search has failed
    }

    // Returns value associated with key k in bucket with hash value h, or else null.
    protected V bucketGet(int h, K k) {
        int j = findSlot(h, k);
        if (j < 0) return null; // no match found
        return table[j].getValue();
    }

    // Associates key k with value v in bucket with hash value h; returns old value.
    protected V bucketPut(int h, K k, V v) {
        int j = findSlot(h, k);
        if (j >= 0) // this key has an existing entry
            return table[j].setValue(v);
        table[-(j + 1)] = new MapEntry<>(k, v); // convert to proper index
        n++;
        return null;
    }

    // Removes entry having key k from bucket with hash value h (if any).
    protected V bucketRemove(int h, K k) {
        int j = findSlot(h, k);
        if (j < 0) return null;
        V answer = table[j].getValue();
        table[j] = DEFUNCT;
        n--;
        return answer;
    }

    // Returns an iterable collection of all key-value entries of the map.
    public Iterable<Entry<K,V>> entrySet( ) {
        ArrayList<Entry<K,V>> buffer = new ArrayList<>( );
        for (int h=0; h < capacity; h++)
            if (!isAvailable(h)) buffer.add(table[h]);
        return buffer;
    }

    /**
     * Exercise 1
     * Our AbstractHashMap class maintains a load factor l ≤ 0.5.
     * Reimplement that class to allow the user to specify the maximum load, and adjust the concrete subclasses accordingly.
     * Perform experiments on our ProbeHashMap classes to measure its efficiency using random key sets and varying limits on the load factor.
     * Do you think ProbeHashMap is better or ChainHashMap? When and how?
     * Hint The load factor can be controlled from within the abstract class, but there must be means for setting the parameter
     * (either through the constructor, or a new method).
     */

    /**
     * Question: Do you think ProbeHashMap is better or ChainHashMap? When and how?
     * It is hard to say which one is better, since both methods have trade-offs.
     * Linear Probing is good in terms of performance when the load factor is kept relatively low.
     * However, as the load factor approaches 1, the number of probes required for each operation can increase dramatically, leading to poor performance.
     *
     * Linear Probing is better than Chaining in the following cases:
     * 1. Better Spatial Locality
     * Since linear probing only uses an array and stores entries directly in it,
     * leading to potentially faster retrieval times.
     *
     * 2. Lower Memory Overhead
     * Linear Probing doesn't use extra data structures like linked lists to store entries that hash to the same bucket.
     *
     *
     * In ChainHashMap,
     * 1.
     * Unlike linear probing, chain hashing performance doesn't degrade as drastically when the load factor increases.
     * Also, the time complexity for put, get, and remove operations remains relatively constant,
     * which can be a major advantage in certain scenarios where time consistency is important.
     *
     * 2.
     * Linear probing suffers from an issue known as primary clustering,
     * where a long probe sequence can be created, increasing the time it takes to find an open slot.
     * Chain hashing doesn't suffer from this problem as it handles collisions differently,
     * maintaining a more evenly distributed structure.
     */

    public static void main(String[] args) {
        Random rand = new Random();
        int numOfKeys = 10000;

        System.out.println("ProbeHashMap");
        for (int factor = 10; factor <= 20; factor++) {
            double loadFactor = factor / 20.0;
            ProbeHashMap<Integer, String> hashMap = new ProbeHashMap<>(numOfKeys, loadFactor);

            long startTime = System.currentTimeMillis();

            for (int i = 0; i < numOfKeys; i++) {
                hashMap.put(rand.nextInt(), "i");
            }

            for (int i = 0; i < numOfKeys; i++) {
                hashMap.get(rand.nextInt());
            }

            for (int i = 0; i < numOfKeys; i++) {
                hashMap.remove(rand.nextInt());
            }

            long endTime = System.currentTimeMillis();

            System.out.println("Load factor = " + loadFactor + "\t Time = " + (endTime - startTime) + "ms");
        }
    }


}
