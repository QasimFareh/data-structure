public class Stack<T extends Comparable<T>> {

    private CursorArray<T> ca;
    private int top;

    public Stack(int capacity) {
        ca = new CursorArray<>(capacity);
        top = ca.createList();
    }

    public void push(T data) {
        ca.insertFirst(data, top);
    }

    public T pop() {
        if (isEmpty())
            return null;
        return ca.removeFirst(top);
    }

    public T peek() {
        if (isEmpty())
            return null;
        return ca.getFirst(top);
    }

    public void clear() {
        while (!isEmpty()) {
            pop();
        }
    }

    public boolean isEmpty() {
        return ca.isEmpty(top);
    }

}