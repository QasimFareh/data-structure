package lab_2;


public class LinkedList<T extends Comparable<T>> {
    Node<T> head;

    public LinkedList() {
        head = new Node<>(null);
        head.next = head;

    }

    public LinkedList<T> marrTL(LinkedList<T> list1, LinkedList<T> list2) {
        LinkedList<T> list3 = new LinkedList<>();

        Node<T> c1 = list1.head.next;
        Node<T> c2 = list2.head.next;
        Node<T> c3 = list3.head;

        while (c1 != list1.head && c2 != list2.head) {
            if (c1.data.compareTo(c2.data) < 0) {
                c3.next = new Node<>(c1.data);
                c1 = c1.next;
            } else {
                c3.next = new Node<>(c2.data);
                c2 = c2.next;
            }
            c3 = c3.next;
        }

        while (c1 != list1.head) {
            c3.next = new Node<>(c1.data);
            c3 = c3.next;
            c1 = c1.next;
        }

        while (c2 != list2.head) {
            c3.next = new Node<>(c2.data);
            c3 = c3.next;
            c2 = c2.next;
        }

        c3.next = list3.head;

        return list3;
    }

    public LinkedList<T> marrTLRec(LinkedList<T> list1, LinkedList<T> list2) {

        LinkedList<T> list3 = new LinkedList<>();
        Node<T> c1 = list1.head.next;
        Node<T> c2 = list2.head.next;
        Node<T> c3 = list3.head;


        return pMarrTLRec(list1, list2, list3, c1, c2, c3);
    }

    private LinkedList<T> pMarrTLRec(LinkedList<T> list1,
                                     LinkedList<T> list2,
                                     LinkedList<T> list3,
                                     Node<T> c1,
                                     Node<T> c2,
                                     Node<T> c3) {

        if (c1 != list1.head && c2 != list2.head) {
            if (c1.data.compareTo(c2.data) < 0) {
                c3.next = new Node<>(c1.data);
                c3=c3.next;
                return pMarrTLRec(list1, list2, list3, c1.next, c2, c3);
            } else {
                c3.next = new Node<>(c2.data);
                c3=c3.next;
                return pMarrTLRec(list1, list2, list3, c1, c2.next, c3);
            }
        }

        if (c1 != list1.head) {
            c3.next = new Node<>(c1.data);
            c3=c3.next;
            return pMarrTLRec(list1, list2, list3, c1.next, c2, c3);
        }

        if (c2 != list2.head) {
            c3.next = new Node<>(c2.data);
            c3=c3.next;
            return pMarrTLRec(list1, list2, list3, c1, c2.next, c3);

        }


        if (c3 != list3.head) {
            c3.next = list3.head;
        } else {
            list3.head.next = list3.head;
        }
        return list3;
    }


    public void insert(T data) {
        Node<T> newNode = new Node<>(data);

        Node<T> prev = head;
        Node<T> curr = head.next;

        for (; curr != head && curr.data.compareTo(data) < 0; prev = curr, curr = curr.next)
            ;

        newNode.next = curr;
        prev.next = newNode;

    }

    public void delete(T data) {
        if (head == null || head.next == head) {
            return;
        }

        Node<T> current = head;

        while (current.next != head) {
            if (current.next.data.equals(data)) {
                current.next = current.next.next;
                return;
            }
            current = current.next;
        }
    }

    public void traverse() {
        Node curr = head.next;
        System.out.print("Head -->");
        while (curr != head) {
            System.out.print(curr + " --> ");
            curr = curr.next;
        }
        System.out.println();
    }

    public String toString() {
        Node curr = head.next;
        String s = " -->";
        while (curr != head) {
            s += curr + " --> ";
            curr = curr.next;
        }
        return s;
    }

    public void traverseReversedRec() {
        traverseReversedRec(head.next);
        System.out.print(" head ");
        System.out.println();
    }

    private void traverseReversedRec(Node<T> current) {
        if (current == head)
            return;

        traverseReversedRec(current.next);
        System.out.print(current.data + " <-- ");

    }

    public boolean find(T data) {
        Node<T> current = head.next;
        while (current != head) {
            int dodo = current.data.compareTo(data);
            if (dodo == 0)
                return true;
            if (dodo > 0)
                return false;
            current = current.next;
        }
        return false;
    }

    private boolean pFindRec(T data, Node curr) {
        if (curr == head)
            return false;
        if (curr.data.equals(data))
            return true;

        return pFindRec(data, curr.next);
    }

}