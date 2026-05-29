class QueueUStack<T> {

    private Stack<T> stack;

    public QueueUStack() {
        stack = new Stack<>();
    }

    // O(1)
    public void enqueue(T data) {
        stack.push(data);
    }

    // O(n)
    public T dequeue() {

        if (stack.isEmpty())
            return null;

        Stack<T> temp = new Stack<>();

        while (!stack.isEmpty()) {
            temp.push(stack.pop());
        }

        T front = temp.pop();

        while (!temp.isEmpty()) {
            stack.push(temp.pop());
        }

        return front;
    }

    // O(n)
    public T getFront() {

        if (stack.isEmpty())
            return null;

        Stack<T> temp = new Stack<>();

        while (!stack.isEmpty()) {
            temp.push(stack.pop());
        }

        T front = temp.peek();

        while (!temp.isEmpty()) {
            stack.push(temp.pop());
        }

        return front;
    }
    public boolean isEmpty() {
        return stack.isEmpty();
    }
}