package Lab;

public class LinkedList<T extends Comparable<T>> {

    Node<T> head;

    public void insert(T data) {
        Node<T> newNode = new Node<>(data);

        Node<T> prev = null;
        Node<T> curr = head;

        if (head == null) {
            head = newNode;
            return;
        }

        while ((curr != null) && (data.compareTo(curr.getData())) > 0) {
            prev = curr;
            curr = curr.next;
        }

        if (prev == null) {
            newNode.next = head;
            head = newNode;
        } else if (curr == null) {
            prev.next = newNode;
        } else {
            newNode.next = curr;
            prev.next = newNode;
        }
    }

    public void delete(T data) {
        if (head == null)
            return;

        if (head.data.equals(data)) {
            head = head.next;
            return;
        }

        Node<T> current = head;

        while (current.next != null) {
            if (current.next.data.equals(data)) {
                current.next = current.next.next;
                return;
            }
            current = current.next;
        }
    }

    public void deleteAllET(T data) {
        if (data == null)
            return;

        while (head != null && head.data.equals(data)) {
            head = head.next;
        }

        Node<T> curr = head;
        Node<T> prev = null;

        while (curr != null) {
            if (curr.data.equals(data)) {
                prev.next = curr.next;
            } else {
                prev = curr;
            }
            curr = curr.next;
        }

        System.out.println("all data = " + data + " deleted successfully!");
    }

    public void deleteAllRec(T data) {
        deleteRec(head, null, data);
    }

    private void deleteRec(Node<T> curr, Node<T> prev, T data) {
        if (curr == null)
            return;

        if (curr.data.equals(data)) {
            if (prev == null) {
                head = curr.next;
                deleteRec(head, null, data);
            } else {
                prev.next = curr.next;
                deleteRec(curr.next, prev, data);
            }
        } else {
            deleteRec(curr.next, curr, data);
        }
    }

    public void insertAtPosition(T data, int n) {
        if (n < 0) {
            System.out.println("ro7 el3b b3ed 3mo !");
            return;
        }
        if (n == 0) {
            Node<T> newNode = new Node<>(data);
            newNode.next = head;
            head = newNode;
        }
        Node<T> current = head;
        for (int i = 0; i < n - 1 && current != null; i++) {
            current = current.next;
        }

        if (current != null) {
            Node<T> newNode = new Node<>(data);
            newNode.next = current.next;
            current.next = newNode;
        }

    }


    public void traverse() {
        Node<T> current = head;
        System.out.print("Head -----> ");
        while (current != null) {
            System.out.print(current.data + " -----> ");
            current = current.next;
        }
        System.out.print(null + "");
    }

    public void traverseReversedRec() {
        System.out.print("null <----- ");
        if (this.head != null)
            traverseReversedRec(head);
        System.out.print(" head ");
    }

    private void traverseReversedRec(Node<T> current) {
        if (current == null)
            return;

        traverseReversedRec(current.next);
        System.out.print(current.data + " <----- ");

    }

    public int length() {
        int length = 0;
        Node<T> current = head;
        while (current != null) {
            length++;
            current = current.next;
        }
        return length;
    }

    public boolean find(T data) {
        Node<T> current = head;
        while (current != null) {
            int dodo = current.data.compareTo(data);
            if (dodo == 0) return true;
            if (dodo > 0) return false;
            current = current.next;
        }
        return false;
    }

    public int countET(T data) {

        if (data == null)
            return -1;
        if (head == null)
            return 0;

        Node<T> curr = head;
        int count = 0;
        while (curr != null) {
            if (curr.data.equals(data))
                count = count + 1;
            curr = curr.next;

        }
        return count;
    }
    public int countRec(T data) {
     return countRec(data,head,0);
    }
    private int countRec(T data , Node<T> curr ,int count) {
        if (data == null)
            return -1;
        if (curr == null)
            return count;
        if(curr.data.equals(data))
            return countRec(data,curr.next,count+1);
        else return countRec(data,curr.next,count);

    }

    public T findMiddle() {
        if (head == null)
            return null;
        Node<T> fast = head;
        Node<T> slow = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow.data;
    }

}
