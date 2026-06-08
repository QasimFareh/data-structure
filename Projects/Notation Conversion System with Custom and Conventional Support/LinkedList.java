public class LinkedList<T extends Comparable<T>> {

    private Node<T> head;

    public void insert(T data) {
        Node<T> newNode = new Node<>(data);

        if (head == null) {
            head = newNode;
            return;
        }

        Node<T> current = head;
        while (current.getNext() != null) {
            current = current.getNext();
        }
        current.setNext(newNode);
    }

    public void delete(T data) {
        if (head == null)
            return;

        if (head.getData().equals(data)) {
            head = head.getNext();
            return;
        }

        Node<T> current = head;
        while (current.getNext() != null) {
            if (current.getNext().getData().equals(data)) {
                current.setNext(current.getNext().getNext());
                return;
            }
            current = current.getNext();
        }
    }

    public void traverse() {
        Node<T> current = head;
        System.out.print("Head -> ");
        while (current != null) {
            System.out.print(current.getData() + " -> ");
            current = current.getNext();
        }
        System.out.println("null");
    }

    public int length() {
        int length = 0;
        Node<T> current = head;
        while (current != null) {
            length++;
            current = current.getNext();
        }
        return length;
    }

    public boolean find(T data) {
        Node<T> current = head;
        while (current != null) {
            if (current.getData().compareTo(data) == 0)
                return true;
            current = current.getNext();
        }
        return false;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public Node<T> getHead() {
        return head;
    }

}
