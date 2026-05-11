public class DoubleLinkedList<T extends Comparable<T>> {
    private Node<T> head;
    private boolean ascending;

    public DoubleLinkedList(boolean ascending) {
        this.ascending = ascending;
        head = new Node<>(null);
        head.setNext(head);
        head.setPrev(head);
    }

    public void insert(T data) {
        Node<T> newData = new Node<>(data);
        Node<T> curr = head.getNext();

        if (head.getNext() == head) {
            head.setNext(newData);
            newData.setPrev(head);
            newData.setNext(head);
            head.setPrev(newData);
            return;
        }

        if (ascending) {

            if (head.getNext().getData().compareTo(data) > 0) {
                newData.setNext(head.getNext());
                newData.setPrev(head);
                head.setNext(newData);
                newData.getNext().setPrev(newData);
            } else if (head.getPrev().getData().compareTo(data) < 0) {
                newData.setPrev(head.getPrev());
                newData.setNext(head);
                newData.getPrev().setNext(newData);
                head.setPrev(newData);
            } else {
                while (curr.getNext() != head && curr.getData().compareTo(data) < 0) {
                    curr = curr.getNext();
                }
                newData.setNext(curr);
                newData.setPrev(curr.getPrev());
                curr.setPrev(newData);
                newData.getPrev().setNext(newData);
            }
        } else {
            if (head.getNext().getData().compareTo(data) < 0) {
                newData.setNext(head.getNext());
                newData.setPrev(head);
                head.setNext(newData);
                newData.getNext().setPrev(newData);
            } else if (head.getPrev().getData().compareTo(data) > 0) {
                newData.setPrev(head.getPrev());
                newData.setNext(head);
                newData.getPrev().setNext(newData);
                head.setPrev(newData);
            } else {
                while (curr.getNext() != head && curr.getData().compareTo(data) > 0) {
                    curr = curr.getNext();
                }
                newData.setNext(curr);
                newData.setPrev(curr.getPrev());
                curr.setPrev(newData);
                newData.getPrev().setNext(newData);
            }
        }
    }

    public void delete(T data) {
        Node<T> curr = head.getNext();
        if (curr == head) return;

        while (curr != head && curr.getData().compareTo(data) != 0) {
            curr = curr.getNext();
        }

        if (curr != head && curr.getData().compareTo(data) == 0) {
            curr.getPrev().setNext(curr.getNext());
            curr.getNext().setPrev(curr.getPrev());
        }
    }

    public void traverse() {
        Node<T> curr = head.getNext();
        if (curr == head) {
            System.out.println("List is empty");
            return;
        }
        System.out.print("head <-> ");
        while (curr != head) {
            System.out.print(curr.getData());
            if (curr.getNext() != head) {
                System.out.print(" <-> ");
            }
            curr = curr.getNext();
        }
        System.out.println();
    }

    public void traverseRev() {
        Node<T> prev = head.getPrev();
        if (prev == head) {
            System.out.println("List is empty");
            return;
        }
        while (prev != head) {
            System.out.print(prev.getData());
            if (prev.getPrev() != head) {
                System.out.print(" <-> ");
            }
            prev = prev.getPrev();
        }
        System.out.print("<-> head");
        System.out.println();
    }

    public void removeDuplicate() {
        Node<T> curr = head.getNext();
        if (curr == head) return;
        Node<T> temp = new Node<>(curr.getData());
        while (curr != head) {
            if (curr.getData().compareTo(temp.getData()) == 0) {
                temp.setNext(curr.getNext());
                curr.getNext().setPrev(temp);
            } else {
                temp = curr;
            }
            curr = curr.getNext();
        }
    }

    public T find(T data) {
        Node<T> curr = head.getNext();
        while (curr != head) {
            if (curr.getData().compareTo(data) == 0)
                return curr.getData();
            curr = curr.getNext();
        }
        return null;
    }

    public Node<T> getHead() {
        return head;
    }

    public int size() {
        if (head.getNext() == head) return 0;
        int size = 0;
        Node<T> curr = head.getNext();
        while (curr != head) {
            size++;
            curr = curr.getNext();
        }
        return size;
    }
}