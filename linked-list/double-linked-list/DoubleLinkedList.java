package lab6;


public class DoubleLinkedList<T extends Comparable<T>> {
    Node<T> head;

    public DoubleLinkedList() {
        head = new Node<>(null);
        head.next = head;
        head.prev = head;

    }

    public void insert(T data) {
        Node<T> newData = new Node<T>(data);
        Node<T> curr = head.next;
        if (head.next == head) {
            head.next = newData;
            newData.prev = head;
            newData.next = head;
            head.prev = newData;
        } else if (head.next.data.compareTo(data) > 0) {
            newData.next = head.next;
            newData.prev = head;
            head.next = newData;
            newData.next.prev = newData;
        } else if (head.prev.data.compareTo(data) < 0) {
            newData.prev = head.prev;
            newData.next = head;
            newData.prev.next = newData;
            head.prev = newData;
        } else {
            while (curr.next != head && curr.data.compareTo(data) < 0) {
                curr = curr.next;
            }
            newData.next=curr;
            newData.prev=curr.prev;
            curr.prev=newData;
            newData.prev.next=newData;
        }


    }

    public void delete(T data){
        Node<T> curr = head.next;

        if(head.next == head) {
            return;
        }

        else if(head.next.data.compareTo(data) > 0) {
            return;
        }

        else if(head.prev.data.compareTo(data) < 0){
            return;
        }

        while (curr != head && curr.data.compareTo(data) < 0) {
            curr = curr.next;
        }

        if (curr.data.compareTo(data) == 0) {
            curr.prev.next = curr.next;
            curr.next.prev = curr.prev;
        }
    }


    public void traverse() {
        Node<T> curr = head.next;
        if (curr == head) {
            System.out.println("List is empty");
            return;
        }
        System.out.print("head <-> ");
        while (curr != head) {
            System.out.print(curr.data);
            if (curr.next != head) {
                System.out.print(" <-> ");
            }
            curr = curr.next;
        }
        System.out.println();
    }


}