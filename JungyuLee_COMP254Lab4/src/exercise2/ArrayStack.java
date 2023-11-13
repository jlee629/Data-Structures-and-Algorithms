package exercise2;

public class ArrayStack<E> implements Stack<E> {

    /**
     * Implement a method with signature transfer(S, T) that transfers all elements from stack S onto stack T,
     * so that the element that starts at the top of S is the first to be inserted onto T,
     * and the element at the bottom of S ends up at the top of T.
     * Write the necessary code to test the method.
     */
    public static <E> void transferElements(Stack<E> S, Stack<E> T) {
        while (!S.isEmpty()) {
            T.push(S.pop());
        }
    }

    public static void main(String[] args) {
        ArrayStack<String> initialStack = new ArrayStack<>();
        initialStack.push("A");
        initialStack.push("B");
        initialStack.push("C");
        initialStack.push("D");
        initialStack.push("E");

        ArrayStack<String> resultStack = new ArrayStack<>();

        System.out.println("Before:");
        System.out.println("Initial Stack: " + initialStack);
        System.out.println("Result Stack: " + resultStack);

        transferElements(initialStack, resultStack);

        System.out.println("After:");
        System.out.println("Initial Stack: " + initialStack);
        System.out.println("Result Stack: " + resultStack);
    }

    public static final int CAPACITY = 1000; // default array capacity

    private E[] data; // generic array used for storage

    private int t = -1; // index of the top element in comp254.stack

    // calls the second constructor ArrayStack(int capacity) with the default capacity as the argument.
    public ArrayStack() { this(CAPACITY); } // constructs comp254.stack with default capacity

    @SuppressWarnings({"unchecked"})
    public ArrayStack(int capacity) {
        data = (E[]) new Object[capacity]; // safe cast; compiler may give warning
    }

    @Override
    public int size() {
        return t + 1;
    }

    @Override
    public boolean isEmpty() {
        return t == -1;
    }

    @Override
    public void push(E e) throws IllegalStateException {
        if (size() == data.length) throw new IllegalStateException("Stack is full");
        data[++t] = e; // increment t before storing new item
    }

    @Override
    public E top() {
        if (isEmpty()) return null;
        return data[t];
    }

    @Override
    public E pop() {
        if (isEmpty()) return null;
        E answer = data[t];
        data[t] = null; // dereference to help garbage collection
        t--;
        return answer;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        for (int j = t; j >= 0; j--) {
            sb.append(data[j]);
            if (j > 0) sb.append(", ");
        }
        sb.append(")");
        return sb.toString();
    }
}