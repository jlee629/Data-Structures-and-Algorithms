package exercise1;/*
 * Copyright 2014, Michael T. Goodrich, Roberto Tamassia, Michael H. Goldwasser
 *
 * Developed for use with the book:
 *
 *    Data Structures and Algorithms in Java, Sixth Edition
 *    Michael T. Goodrich, Roberto Tamassia, and Michael H. Goldwasser
 *    John Wiley & Sons, 2014
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.util.ArrayList;
import java.util.Comparator;

public class TreeMapExercise1<K,V> extends AbstractSortedMap<K,V> {

    //---------------- nested BalanceableBinaryTree class ----------------
    protected static class BalanceableBinaryTree<K,V>
            extends LinkedBinaryTree<Entry<K,V>> {
        //-------------- nested BSTNode class --------------
        // this extends the inherited LinkedBinaryTree.Node class
        protected static class BSTNode<E> extends Node<E> {
            int aux=0;
            BSTNode(E e, Node<E> parent, Node<E> leftChild, Node<E> rightChild) {
                super(e, parent, leftChild, rightChild);
            }
            public int getAux() { return aux; }
            public void setAux(int value) { aux = value; }
        } //--------- end of nested BSTNode class ---------

        // positional-based methods related to aux field
        public int getAux(Position<Entry<K,V>> p) {
            return ((BSTNode<Entry<K,V>>) p).getAux();
        }

        public void setAux(Position<Entry<K,V>> p, int value) {
            ((BSTNode<Entry<K,V>>) p).setAux(value);
        }

        // Override node factory function to produce a BSTNode (rather than a Node)
        @Override
        protected
        Node<Entry<K,V>> createNode(Entry<K,V> e, Node<Entry<K,V>> parent,
                                    Node<Entry<K,V>> left, Node<Entry<K,V>> right) {
            return new BSTNode<>(e, parent, left, right);
        }

        private void relink(Node<Entry<K,V>> parent, Node<Entry<K,V>> child,
                            boolean makeLeftChild) {
            child.setParent(parent);
            if (makeLeftChild)
                parent.setLeft(child);
            else
                parent.setRight(child);
        }

        public void rotate(Position<Entry<K,V>> p) {
            Node<Entry<K,V>> x = validate(p);
            Node<Entry<K,V>> y = x.getParent();        // we assume this exists
            Node<Entry<K,V>> z = y.getParent();        // grandparent (possibly null)
            if (z == null) {
                root = x;                                // x becomes root of the tree
                x.setParent(null);
            } else
                relink(z, x, y == z.getLeft());          // x becomes direct child of z
            // now rotate x and y, including transfer of middle subtree
            if (x == y.getLeft()) {
                relink(y, x.getRight(), true);           // x's right child becomes y's left
                relink(x, y, false);                     // y becomes x's right child
            } else {
                relink(y, x.getLeft(), false);           // x's left child becomes y's right
                relink(x, y, true);                      // y becomes left child of x
            }
        }

        public Position<Entry<K,V>> restructure(Position<Entry<K,V>> x) {
            Position<Entry<K,V>> y = parent(x);
            Position<Entry<K,V>> z = parent(y);
            if ((x == right(y)) == (y == right(z))) {   // matching alignments
                rotate(y);                                // single rotation (of y)
                return y;                                 // y is new subtree root
            } else {                                    // opposite alignments
                rotate(x);                                // double rotation (of x)
                rotate(x);
                return x;                                 // x is new subtree root
            }
        }
    } //----------- end of nested BalanceableBinaryTree class -----------

    protected BalanceableBinaryTree<K,V> tree = new BalanceableBinaryTree<>();

    public TreeMapExercise1() {
        super();                  // the AbstractSortedMap constructor
        tree.addRoot(null);       // create a sentinel leaf as root
    }

    public TreeMapExercise1(Comparator<K> comp) {
        super(comp);              // the AbstractSortedMap constructor
        tree.addRoot(null);       // create a sentinel leaf as root
    }


    @Override
    public int size() {
        return (tree.size() - 1) / 2;        // only internal nodes have entries
    }


    private void expandExternal(Position<Entry<K,V>> p, Entry<K,V> entry) {
        tree.set(p, entry);            // store new entry at p
        tree.addLeft(p, null);         // add new sentinel leaves as children
        tree.addRight(p, null);
    }


    // Some notational shorthands for brevity (yet not efficiency)
    protected Position<Entry<K,V>> root() { return tree.root(); }
    protected Position<Entry<K,V>> parent(Position<Entry<K,V>> p) { return tree.parent(p); }
    protected Position<Entry<K,V>> left(Position<Entry<K,V>> p) { return tree.left(p); }
    protected Position<Entry<K,V>> right(Position<Entry<K,V>> p) { return tree.right(p); }
    protected Position<Entry<K,V>> sibling(Position<Entry<K,V>> p) { return tree.sibling(p); }
    protected boolean isRoot(Position<Entry<K,V>> p) { return tree.isRoot(p); }
    protected boolean isExternal(Position<Entry<K,V>> p) { return tree.isExternal(p); }
    protected boolean isInternal(Position<Entry<K,V>> p) { return tree.isInternal(p); }
    protected void set(Position<Entry<K,V>> p, Entry<K,V> e) { tree.set(p, e); }
    protected Entry<K,V> remove(Position<Entry<K,V>> p) { return tree.remove(p); }
    protected void rotate(Position<Entry<K,V>> p) { tree.rotate(p); }
    protected Position<Entry<K,V>> restructure(Position<Entry<K,V>> x) { return tree.restructure(x); }




    protected Position<Entry<K,V>> treeMin(Position<Entry<K,V>> p) {
        Position<Entry<K,V>> walk = p;
        while (isInternal(walk))
            walk = left(walk);
        return parent(walk);              // we want the parent of the leaf
    }


    protected Position<Entry<K,V>> treeMax(Position<Entry<K,V>> p) {
        Position<Entry<K,V>> walk = p;
        while (isInternal(walk))
            walk = right(walk);
        return parent(walk);              // we want the parent of the leaf
    }

    @Override
    public V get(K key) throws IllegalArgumentException {
        checkKey(key);                          // may throw IllegalArgumentException
        Position<Entry<K,V>> p = treeSearch(root(), key);
        rebalanceAccess(p);                     // hook for balanced tree subclasses
        if (isExternal(p)) return null;         // unsuccessful search
        return p.getElement().getValue();       // match found
    }

    @Override
    public V put(K key, V value) throws IllegalArgumentException {
        checkKey(key);                          // may throw IllegalArgumentException
        Entry<K,V> newEntry = new MapEntry<>(key, value);
        Position<Entry<K,V>> p = treeSearch(root(), key);
        if (isExternal(p)) {                    // key is new
            expandExternal(p, newEntry);
            rebalanceInsert(p);                   // hook for balanced tree subclasses
            return null;
        } else {                                // replacing existing key
            V old = p.getElement().getValue();
            set(p, newEntry);
            rebalanceAccess(p);                   // hook for balanced tree subclasses
            return old;
        }
    }

    @Override
    public V remove(K key) throws IllegalArgumentException {
        checkKey(key);                          // may throw IllegalArgumentException
        Position<Entry<K,V>> p = treeSearch(root(), key);
        if (isExternal(p)) {                    // key not found
            rebalanceAccess(p);                   // hook for balanced tree subclasses
            return null;
        } else {
            V old = p.getElement().getValue();
            if (isInternal(left(p)) && isInternal(right(p))) { // both children are internal
                Position<Entry<K,V>> replacement = treeMax(left(p));
                set(p, replacement.getElement());
                p = replacement;
            } // now p has at most one child that is an internal node
            Position<Entry<K,V>> leaf = (isExternal(left(p)) ? left(p) : right(p));
            Position<Entry<K,V>> sib = sibling(leaf);
            remove(leaf);
            remove(p);                            // sib is promoted in p's place
            rebalanceDelete(sib);                 // hook for balanced tree subclasses
            return old;
        }
    }

    // additional behaviors of the SortedMap interface
    @Override
    public Entry<K,V> firstEntry() {
        if (isEmpty()) return null;
        return treeMin(root()).getElement();
    }

    @Override
    public Entry<K,V> lastEntry() {
        if (isEmpty()) return null;
        return treeMax(root()).getElement();
    }

    @Override
    public Entry<K,V> ceilingEntry(K key) throws IllegalArgumentException {
        checkKey(key);                              // may throw IllegalArgumentException
        Position<Entry<K,V>> p = treeSearch(root(), key);
        if (isInternal(p)) return p.getElement();   // exact match
        while (!isRoot(p)) {
            if (p == left(parent(p)))
                return parent(p).getElement();          // parent has next greater key
            else
                p = parent(p);
        }
        return null;                                // no such ceiling exists
    }

    @Override
    public Entry<K,V> floorEntry(K key) throws IllegalArgumentException {
        checkKey(key);                              // may throw IllegalArgumentException
        Position<Entry<K,V>> p = treeSearch(root(), key);
        if (isInternal(p)) return p.getElement();   // exact match
        while (!isRoot(p)) {
            if (p == right(parent(p)))
                return parent(p).getElement();          // parent has next lesser key
            else
                p = parent(p);
        }
        return null;                                // no such floor exists
    }

    @Override
    public Entry<K,V> lowerEntry(K key) throws IllegalArgumentException {
        checkKey(key);                              // may throw IllegalArgumentException
        Position<Entry<K,V>> p = treeSearch(root(), key);
        if (isInternal(p) && isInternal(left(p)))
            return treeMax(left(p)).getElement();     // this is the predecessor to p
        // otherwise, we had failed search, or match with no left child
        while (!isRoot(p)) {
            if (p == right(parent(p)))
                return parent(p).getElement();          // parent has next lesser key
            else
                p = parent(p);
        }
        return null;                                // no such lesser key exists
    }

    @Override
    public Entry<K,V> higherEntry(K key) throws IllegalArgumentException {
        checkKey(key);                               // may throw IllegalArgumentException
        Position<Entry<K,V>> p = treeSearch(root(), key);
        if (isInternal(p) && isInternal(right(p)))
            return treeMin(right(p)).getElement();     // this is the successor to p
        // otherwise, we had failed search, or match with no right child
        while (!isRoot(p)) {
            if (p == left(parent(p)))
                return parent(p).getElement();           // parent has next lesser key
            else
                p = parent(p);
        }
        return null;                                 // no such greater key exists
    }

    @Override
    public Iterable<Entry<K,V>> entrySet() {
        ArrayList<Entry<K,V>> buffer = new ArrayList<>(size());
        for (Position<Entry<K,V>> p : tree.inorder())
            if (isInternal(p)) buffer.add(p.getElement());
        return buffer;
    }


    @Override
    public Iterable<Entry<K,V>> subMap(K fromKey, K toKey) throws IllegalArgumentException {
        checkKey(fromKey);                                // may throw IllegalArgumentException
        checkKey(toKey);                                  // may throw IllegalArgumentException
        ArrayList<Entry<K,V>> buffer = new ArrayList<>(size());
        if (compare(fromKey, toKey) < 0)                  // ensure that fromKey < toKey
            subMapRecurse(fromKey, toKey, root(), buffer);
        return buffer;
    }

    // utility to fill subMap buffer recursively (while maintaining order)
    private void subMapRecurse(K fromKey, K toKey, Position<Entry<K,V>> p,
                               ArrayList<Entry<K,V>> buffer) {
        if (isInternal(p))
            if (compare(p.getElement(), fromKey) < 0)
                // p's key is less than fromKey, so any relevant entries are to the right
                subMapRecurse(fromKey, toKey, right(p), buffer);
            else {
                subMapRecurse(fromKey, toKey, left(p), buffer); // first consider left subtree
                if (compare(p.getElement(), toKey) < 0) {       // p is within range
                    buffer.add(p.getElement());                      // so add it to buffer, and consider
                    subMapRecurse(fromKey, toKey, right(p), buffer); // right subtree as well
                }
            }
    }

    // Stubs for balanced search tree operations (subclasses can override)
    protected void rebalanceInsert(Position<Entry<K,V>> p) { }

    protected void rebalanceDelete(Position<Entry<K,V>> p) { }

    protected void rebalanceAccess(Position<Entry<K,V>> p) { }

    // remainder of class is for debug purposes only
    protected void dump() {
        dumpRecurse(root(), 0);
    }

    private void dumpRecurse(Position<Entry<K,V>> p, int depth) {
        String indent = (depth == 0 ? "" : String.format("%" + (2*depth) + "s", ""));
        if (isExternal(p))
            System.out.println(indent + "leaf");
        else {
            System.out.println(indent + p.getElement());
            dumpRecurse(left(p), depth+1);
            dumpRecurse(right(p), depth+1);
        }
    }

        /*
    private Position<Entry<K,V>> treeSearch(Position<Entry<K,V>> p, K key) {
        if (isExternal(p))
            return p;                          // key not found; return the final leaf
        int comp = compare(key, p.getElement());
        if (comp == 0)
            return p;                          // key found; return its position
        else if (comp < 0)
            return treeSearch(left(p), key);   // search left subtree
        else
            return treeSearch(right(p), key);  // search right subtree
    }
    */


    private Position<Entry<K,V>> treeSearch(Position<Entry<K,V>> p, K key) {
        while (true) {
            if (isExternal(p)) {
                return p; // key not found; return the final leaf
            }
            int comp = compare(key, p.getElement());
            if (comp == 0) {
                return p; // key found; return its position
            } else if (comp < 0) {
                p = left(p); // search left subtree
            } else {
                p = right(p); // search right subtree
            }
        }
    }

    public static void main (String[] args)
    {
        // create a Binary Search Tree and make a search
        TreeMap<Integer,String> map = new TreeMap<Integer,String> ();
        map.put(44, "A");
        map.put(17, "B");
        map.put(88, "C");
        map.put(8, "D");
        map.put(32, "E");
        map.put(65, "F");
        map.put(97, "G");
        map.put(28, "H");
        map.put(54, "I");
        map.put(82, "J");
        map.put(93, "K");
        map.put(21, "L");
        map.put(29, "M");
        map.put(76, "N");
        map.put(80, "O");

        // drawing Tree Structure
        for (Position<Entry<Integer,String>> p : map.tree.inorder())
            System.out.println(p.getElement());

        // testing get
        System.out.println("Testing get method:");
        System.out.println("Expected: M, Actual: " + map.get(29));
        System.out.println("Expected: null, Actual: " + map.get(100));

        // testing remove
        System.out.println("\nTesting remove method:");
        System.out.println("Expected: A, Actual: " + map.remove(93)); // Should remove "K"
        System.out.println("Expected: null, Actual: " + map.remove(93)); // "K" no longer exists

        // testing get again after removal
        System.out.println("\nTesting get method after removal:");
        System.out.println("Expected: null, Actual: " + map.get(93)); // "A" should have been removed

        /*
        TreeMap<Integer,String> map2 = new TreeMap<Integer,String> ();
        map2.put(10, "A");
        map2.put(20, "B");
        map2.put(30, "C");
        map2.put(40, "D");

        // Drawing Tree Structure
        for (Position<Entry<Integer,String>> p : map2.tree.inorder())
            System.out.println(p.getElement());


        // Testing get
        System.out.println("Testing get method:");
        System.out.println("Expected: B, Actual: " + map2.get(20));
        System.out.println("Expected: null, Actual: " + map2.get(100));

        // Testing remove
        System.out.println("\nTesting remove method:");
        System.out.println("Expected: C, Actual: " + map2.remove(30)); // remove "C"
        System.out.println("Expected: null, Actual: " + map2.remove(30)); // "C" no longer exists

        // Testing get again after removal
        System.out.println("\nTesting get method after removal:");
        System.out.println("Expected: null, Actual: " + map2.get(30)); // "C" should have been removed
        */
    }
}