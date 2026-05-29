class Stack<T> {

    private LinkedList<T> list;

    public Stack() {
        list = new LinkedList<>();
    }

    // O(1)
    public void push(T data) {
        list.insertFirst(data);
    }

    // O(1)
    public T pop() {
        return list.removeFirst();
    }

    // O(1)
    public T peek() {
        return list.getFirst();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }
}