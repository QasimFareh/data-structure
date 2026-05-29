class LinkedList<T> {

    private Node<T> head;

    // إضافة بالبداية
    public void insertFirst(T data) {

        Node<T> newNode = new Node<>(data);

        newNode.next = head;
        head = newNode;
    }

    // حذف أول عنصر
    public T removeFirst() {

        if (isEmpty())
            return null;

        T data = head.data;

        head = head.next;

        return data;
    }

    // جلب أول عنصر
    public T getFirst() {

        if (isEmpty())
            return null;

        return head.data;
    }

    public boolean isEmpty() {
        return head == null;
    }
}