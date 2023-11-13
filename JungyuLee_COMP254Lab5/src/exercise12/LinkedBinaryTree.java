package exercise12;



public class LinkedBinaryTree<E> extends AbstractBinaryTree<E> {

    //---------------- nested Node class ----------------
    /** Nested static class for a binary tree node. */
    protected static class Node<E> implements Position<E> {
        private E element;          // an element stored at this node
        private Node<E> parent;     // a reference to the parent node (if any)
        private Node<E> left;       // a reference to the left child (if any)
        private Node<E> right;      // a reference to the right child (if any)

        /**
         * Constructs a node with the given element and neighbors.
         *
         * @param e  the element to be stored
         * @param above       reference to a parent node
         * @param leftChild   reference to a left child node
         * @param rightChild  reference to a right child node
         */
        public Node(E e, Node<E> above, Node<E> leftChild, Node<E> rightChild) {
            element = e;
            parent = above;
            left = leftChild;
            right = rightChild;
        }

        //get and set methods
        //
        // accessor methods
        public E getElement() { return element; }
        public Node<E> getParent() { return parent; }
        public Node<E> getLeft() { return left; }
        public Node<E> getRight() { return right; }

        // update methods
        public void setElement(E e) { element = e; }
        public void setParent(Node<E> parentNode) { parent = parentNode; }
        public void setLeft(Node<E> leftChild) { left = leftChild; }
        public void setRight(Node<E> rightChild) { right = rightChild; }
    } //----------- end of nested Node class -----------

    /** Factory function to create a new node storing element e. */
    protected Node<E> createNode(E e, Node<E> parent,
                                 Node<E> left, Node<E> right) {
        return new Node<E>(e, parent, left, right);
    }

    // LinkedBinaryTree instance variables
    /** The root of the binary tree */
    protected Node<E> root = null;     // root of the tree

    /** The number of nodes in the binary tree */
    private int size = 0;              // number of nodes in the tree

    // constructor
    /** Construts an empty binary tree. */
    public LinkedBinaryTree() { }      // constructs an empty binary tree

    // nonpublic utility
    /**
     * Verifies that a Position belongs to the appropriate class, and is
     * not one that has been previously removed. Note that our current
     * implementation does not actually verify that the position belongs
     * to this particular list instance.
     *
     * @param p   a Position (that should belong to this tree)
     * @return    the underlying Node instance for the position
     * @throws IllegalArgumentException if an invalid position is detected
     */
    protected Node<E> validate(Position<E> p) throws IllegalArgumentException {
        if (!(p instanceof Node))
            throw new IllegalArgumentException("Not valid position type");
        Node<E> node = (Node<E>) p;       // safe cast
        if (node.getParent() == node)     // our convention for defunct node
            throw new IllegalArgumentException("p is no longer in the tree");
        return node;
    }

    // accessor methods (not already implemented in AbstractBinaryTree)
    /**
     * Returns the number of nodes in the tree.
     * @return number of nodes in the tree
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the root Position of the tree (or null if tree is empty).
     * @return root Position of the tree (or null if tree is empty)
     */
    @Override
    public Position<E> root() {
        return root;
    }

    /**
     * Returns the Position of p's parent (or null if p is root).
     *
     * @param p    A valid Position within the tree
     * @return Position of p's parent (or null if p is root)
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    @Override
    public Position<E> parent(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return node.getParent();
    }

    /**
     * Returns the Position of p's left child (or null if no child exists).
     *
     * @param p A valid Position within the tree
     * @return the Position of the left child (or null if no child exists)
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     */
    @Override
    public Position<E> left(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return node.getLeft();
    }

    /**
     * Returns the Position of p's right child (or null if no child exists).
     *
     * @param p A valid Position within the tree
     * @return the Position of the right child (or null if no child exists)
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     */
    @Override
    public Position<E> right(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return node.getRight();
    }

    // update methods supported by this class
    /**
     * Places element e at the root of an empty tree and returns its new Position.
     *
     * @param e   the new element
     * @return the Position of the new element
     * @throws IllegalStateException if the tree is not empty
     */
    public Position<E> addRoot(E e) throws IllegalStateException {
        if (!isEmpty()) throw new IllegalStateException("Tree is not empty");
        root = createNode(e, null, null, null);
        size = 1;
        return root;
    }

    /**
     * Creates a new left child of Position p storing element e and returns its Position.
     *
     * @param p   the Position to the left of which the new element is inserted
     * @param e   the new element
     * @return the Position of the new element
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     * @throws IllegalArgumentException if p already has a left child
     */
    public Position<E> addLeft(Position<E> p, E e)
            throws IllegalArgumentException {
        Node<E> parent = validate(p);
        if (parent.getLeft() != null)
            throw new IllegalArgumentException("p already has a left child");
        Node<E> child = createNode(e, parent, null, null);
        parent.setLeft(child);
        size++;
        return child;
    }

    /**
     * Creates a new right child of Position p storing element e and returns its Position.
     *
     * @param p   the Position to the right of which the new element is inserted
     * @param e   the new element
     * @return the Position of the new element
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     * @throws IllegalArgumentException if p already has a right child
     */
    public Position<E> addRight(Position<E> p, E e)
            throws IllegalArgumentException {
        Node<E> parent = validate(p);
        if (parent.getRight() != null)
            throw new IllegalArgumentException("p already has a right child");
        Node<E> child = createNode(e, parent, null, null);
        parent.setRight(child);
        size++;
        return child;
    }

    /**
     * Replaces the element at Position p with element e and returns the replaced element.
     *
     * @param p   the relevant Position
     * @param e   the new element
     * @return the replaced element
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    public E set(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> node = validate(p);
        E temp = node.getElement();
        node.setElement(e);
        return temp;
    }

    /**
     * Attaches trees t1 and t2, respectively, as the left and right subtree of the
     * leaf Position p. As a side effect, t1 and t2 are set to empty trees.
     *
     * @param p   a leaf of the tree
     * @param t1  an independent tree whose structure becomes the left child of p
     * @param t2  an independent tree whose structure becomes the right child of p
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     * @throws IllegalArgumentException if p is not a leaf
     */
    public void attach(Position<E> p, LinkedBinaryTree<E> t1,
                       LinkedBinaryTree<E> t2) throws IllegalArgumentException {
        Node<E> node = validate(p);
        if (isInternal(p)) throw new IllegalArgumentException("p must be a leaf");
        size += t1.size() + t2.size();
        if (!t1.isEmpty()) {                  // attach t1 as left subtree of node
            t1.root.setParent(node); //the node becomes parent of the root of left subtree
            node.setLeft(t1.root);
            t1.root = null;
            t1.size = 0;
        }
        if (!t2.isEmpty()) {                  // attach t2 as right subtree of node
            t2.root.setParent(node);
            node.setRight(t2.root);
            t2.root = null;
            t2.size = 0;
        }
    }

    /**
     * Removes the node at Position p and replaces it with its child, if any.
     *
     * @param p   the relevant Position
     * @return element that was removed
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     * @throws IllegalArgumentException if p has two children.
     */
    public E remove(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        if (numChildren(p) == 2)
            throw new IllegalArgumentException("p has two children");
        Node<E> child = (node.getLeft() != null ? node.getLeft() : node.getRight() );
        if (child != null)
            child.setParent(node.getParent());  // child's grandparent becomes its parent
        if (node == root)
            root = child;                       // child becomes root
        else {
            Node<E> parent = node.getParent();
            if (node == parent.getLeft())
                parent.setLeft(child);
            else
                parent.setRight(child);
        }
        size--;
        E temp = node.getElement();
        node.setElement(null);                // help garbage collection
        node.setLeft(null);
        node.setRight(null);
        node.setParent(node);                 // our convention for defunct node
        return temp;
    }

    public static void main(String[] args)
    {
        LinkedBinaryTree lbt = new LinkedBinaryTree();

        Position<String> root = lbt.addRoot("A");
        Position<String> bpos = lbt.addLeft(root, "B");
        Position<String> cpos = lbt.addRight(root, "C");
        Position<String> epos = lbt.addLeft(bpos, "E");
        Position<String> fpos = lbt.addRight(bpos, "F");
        Position<String> kpos = lbt.addLeft(fpos, "K");
        Position<String> gpos = lbt.addRight(cpos, "G");

        // Create an array for easy iteration over all nodes

        Position<String>[] nodes1 = new Position[] {root, bpos, cpos, epos, fpos, kpos, gpos};

        System.out.println("Preorder traversal:");
        for (Position<String> node : nodes1) {
            Position<String> next = lbt.preorderNext(node);
            if (next != null) {
                System.out.println("Next node after " + node.getElement() + " is " + next.getElement());
            } else {
                System.out.println("Next node after " + node.getElement() + " is null");
            }
        }

        printPreorder(lbt);
        System.out.println();
        parenthesize(lbt, root);
        System.out.println();


        // Create an array for easy iteration over all nodes
        Position<String>[] nodes2 = new Position[] {root, bpos, cpos, epos, fpos, kpos, gpos};
        System.out.println("Inorder traversal:");
        for (Position<String> node : nodes2) {
            Position<String> next = lbt.inorderNext(node);
            if (next != null) {
                System.out.println("Next node after " + node.getElement() + " is " + next.getElement());
            } else {
                System.out.println("Next node after " + node.getElement() + " is null");
            }
        }

        parenthesize(lbt, root);
        System.out.println();
        printInorder(lbt);
        System.out.println();


        //Print the element and height of each position
        System.out.println("Element and height of each position:");
        lbt.printElementAndHeight();


    }

    /** Prints parenthesized representation of subtree of T rooted at p. */
    public static <E> void parenthesize(Tree<E> T, Position<E> p) {
        System.out.print(p.getElement());
        if (T.isInternal(p)) {
            boolean firstTime = true;
            for (Position<E> c : T.children(p)) {
                System.out.print( (firstTime ? " (" : ", ") ); // determine proper punctuation
                firstTime = false;                             // any future passes will get comma
                parenthesize(T, c);                            // recur on child
            }
            System.out.print(")");
        }
    }

    public static <E> void printPreorder(AbstractTree<E> T) {
        for (Position<E> p : T.preorder())
            System.out.print(p.getElement() + " ");
    }

    public static <E> void printPostorder(AbstractTree<E> T) {
        for (Position<E> p : T.postorder())
            System.out.print(p.getElement() + " ");
    }

    public static <E> void printInorder(AbstractBinaryTree<E> T) {
        for (Position<E> p : T.inorder())
            System.out.print(p.getElement() + " ");
    }


    /** Exercise 1
     * preorderNext(p): Return the position visited after p in a preorder traversal of T
     * (or null if p is the last node visited).
     */
    public Position<E> preorderNext(Position<E> p) {
        if (left(p) != null) {
            // If p has a left child, return it.
            return left(p); // A -> B, B -> E, F -> K
        } else if (right(p) != null) {
            // If p doesn't have a left child, but has a right child, return it.
            return right(p); // C -> G
        } else {
            // If p doesn't have any children, find the first parent that is a left child of its parent but the parent has a right child that is not visited yet.
            Position<E> current = p;
            while (true) {
                if (current == root()) { // G -> null
                    // If we reach the root without finding such a parent, return null
                    return null; // we've traversed the entire tree and there are no more nodes to visit
                }
                if (right(parent(current)) == current) {
                    // If the current node is a right child, continue to the parent since we've finished visiting the current node's subtree
                    current = parent(current); // move up
                    continue;
                }
                if (right(parent(current)) == null) { // K's case
                    // If the parent doesn't have a right child, continue to the parent since we've finished visiting the current node's subtree
                    current = parent(current); // move up
                    continue; // K -> C (later also A's right child is not null we could return C)
                } // When B becomes the current, B is the right left child of A, so the second and third condition are false. So it returns C after K.
                // Otherwise, return the right child of the parent
                return right(parent(current)); // E -> F
            }
        }
    }

    /** Exercise 1
     * inorderNext(p): Return the position visited after p in an inorder traversal of T
     * (or null if p is the last node visited).
     */
    public Position<E> inorderNext(Position<E> p) {
        if (right(p) != null) { // since left is covered
            Position<E> walk = right(p);
            while (left(walk) != null)
                walk = left(walk);
            return walk; // return leftmost child of right subtree, B -> K, A -> C, C -> G
        } else {
            Position<E> walk = p;
            while (parent(walk) != null && walk == right(parent(walk)))
                walk = parent(walk);
            // return parent whose left child is p, E -> B, K -> F, F -> A
            // If we didn't find such a parent, then p is the last node in the inorder traversal
            return (walk == root()) ? null : parent(walk); // G -> null
        }
    }


    /** Exercise 2
     * Give an efficient algorithm that computes and prints, for every position p of a tree T,
     * the element of p followed by the height of p’s subtree. Write a Java/Python to test your solution.
     * Hint: Use a postorder traversal to find the height of each subtree.
     * The height for a subtree at p will be 0 if p is a leaf and otherwise one more than the height of the max child.
     * Print out the element at p and its computed height during the postorder visit.
     */
    public void printElementAndHeight() {
        for (Position<E> p : postorder()) {
            System.out.println(p.getElement() + " " + height(p));
        }
    }
} //----------- end of LinkedBinaryTree class -----------








/**
 *  If p is a leaf (i.e., it has no children), its height is 0.
 *  If p has children, its height is one more than the height of its tallest child.
 *  By visiting the nodes in a postorder manner, the algorithm ensures that it first calculates the heights of all children of a node before computing the height of the node itself.
 *  This way, the height of each subtree rooted at every position p is correctly determined and printed.
 */
