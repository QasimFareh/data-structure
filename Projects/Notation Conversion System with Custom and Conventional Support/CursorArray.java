public class CursorArray<T extends Comparable<T>> {

    private CNode[] ca;

    public CursorArray(int capacity) {
        ca = new CNode[capacity];
        for (int i = 0; i < capacity - 1; i++) {
            ca[i] = new CNode(null, i + 1);
        }
        ca[capacity - 1] = new CNode(null, 0);
    }

    private int malloc() {
        int p = ca[0].getNext();
        ca[0].setNext(ca[p].getNext());
        return p;
    }

    private void free(int p) {
        ca[p].setNext(ca[0].getNext());
        ca[0].setNext(p);
    }

    public int createList() {
        int l = malloc();
        if (l != 0) {
            ca[l] = new CNode(null, 0);
            return l;
        }
        return -1;
    }

    public void insertFirst(T data, int l) {
        int p = malloc();
        if (p != 0) {
            ca[p] = new CNode(data, ca[l].getNext());
            ca[l].setNext(p);
        }
    }

    public void traverse(int l) {
        int curr = ca[l].getNext();
        if (curr != 0) {
            System.out.print("Head -> ");
            while (curr != 0) {
                System.out.print(ca[curr].getData() + " -> ");
                curr = ca[curr].getNext();
            }
            System.out.println("null");
        }
    }

    public boolean find(T data, int l) {
        int curr = ca[l].getNext();
        while (curr != 0) {
            if (ca[curr].getData().compareTo(data) == 0)
                return true;
            curr = ca[curr].getNext();
        }
        return false;
    }

    public void delete(T data, int l) {
        int prev = l;
        int curr = ca[l].getNext();
        while (curr != 0) {
            if (ca[curr].getData().compareTo(data) == 0) {
                ca[prev].setNext(ca[curr].getNext());
                free(curr);
                return;
            }
            prev = curr;
            curr = ca[curr].getNext();
        }
    }

    public T getFirst(int l) {
        int first = ca[l].getNext();
        if (first == 0)
            return null;
        return (T) ca[first].getData();
    }

    public T removeFirst(int l) {
        int first = ca[l].getNext();
        if (first == 0)
            return null;
        T data = (T) ca[first].getData();
        ca[l].setNext(ca[first].getNext());
        free(first);
        return data;
    }

    public boolean isEmpty(int l) {
        return ca[l].getNext() == 0;
    }

}
