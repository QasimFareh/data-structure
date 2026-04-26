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
            newData.next = curr;
            newData.prev = curr.prev;
            curr.prev = newData;
            newData.prev.next = newData;
        }
    }

    public void delete(T data) {
        Node<T> curr = head.next;

// if(head.next == head) {
// return;
// }
//
// else if(head.next.data.compareTo(data) > 0) {
// return;
// }
//
// else if(head.prev.data.compareTo(data) < 0){
// return;
// }

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

    public void traverseRev() {
        Node<T> prev = head.prev;
        if (prev == head) {
            System.out.println("List is empty");
            return;
        }
        while (prev != head) {
            System.out.print(prev.data);
            if (prev.prev != head) {
                System.out.print(" <-> ");
            }
            prev = prev.prev;
        }
        System.out.print("<-> head");
        System.out.println();
    }

    public void removeDublicate() {
        Node<T> curr = head.next;
        if (curr == head)
            return;
        Node<T> temp = new Node<>(curr.data);
        while (curr != head) {
            if (curr.data.compareTo(temp.data) == 0) {
                temp.next = curr.next;
                curr.next.prev=temp;
            }
            else
                temp=curr;

            curr = curr.next;
        }
    }

    public boolean isPalendrom(){
        Node<T> first = head.next;
        Node<T> last = head.prev;
        boolean zozo = false;

        while(first!=last&&first.next!=last){
            if(first.data.compareTo(last.data)!=0)
                return false;
        }
        first=first.next;
        last=last.prev;

        return true;
    }

    public void rotateBy(int k) {
        if (head.next == head || k == 0) return;

        Node<T> curr = head.next;

        for (int i = 0; i < k; i++) {
            curr = curr.next;
        }

        Node<T> newHead = curr;
        Node<T> oldFirst = head.next;
        Node<T> oldLast = head.prev;
        Node<T> beforeNewHead = newHead.prev;

        oldLast.next = oldFirst;
        oldFirst.prev = oldLast;
        beforeNewHead.next = head;
        head.prev = beforeNewHead;
        head.next = newHead;
        newHead.prev = head;
    }

}
