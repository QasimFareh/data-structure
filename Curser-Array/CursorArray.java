public class CursorArray<T extends Comparable<CursorArray>> {
    CNode[] ca;

    CursorArray(int capacity) {
        ca = new CNode[capacity];
        for (int i = 0; i < capacity - 1; i++) {
            ca[i] = new CNode(null, i + 1);
        }
        ca[capacity - 1] = new CNode(null, 0);
    }

    int malloc() {
        int p = ca[0].next;
        ca[0].next = ca[p].next;
        return p;
    }

    void free(int p) {
        ca[p].next = ca[0].next;
        ca[0].next = p;
    }

    int createList() {
        int l = malloc();
        if (l != 0) {
            ca[l] = new CNode(null, 0);
            return l;
        }
        return -1;
    }

    void insertFirst(T data, int l) {
        int p = malloc();
        if (p != 0) {
            ca[p] = new CNode(data, ca[l].next);
            ca[l].next = p;
        } else return;
    }

    void traverse(int l) {
        l = ca[l].next;
        if (l != 0) {
            System.out.print("The List --> ");
            while (l != 0) {
                System.out.print(ca[l].data + " --> ");
                l = ca[l].next;
            }
            System.out.print(" null ");
        }
    }

    boolean find(T data, int l) {
        if (l != 0) {
            l = ca[l].next;
            while (l != 0) {
                if (ca[l].data.compareTo(data) == 0) {
                    return true;
                }
                l = ca[l].next;
            }
        }
        return false;
    }

    int findPrev(T data, int l) {
        while (l != 0) {
            int p = ca[l].next;
            if (p != 0 && ca[p].data.compareTo(data) == 0) {
                return l;
            }
            l = p;
        }
        return -1;
    }

    void delete(T data, int l) {
        if (l != 0) {
            int p = ca[l].next;
            while (p != 0) {
                if (ca[p].data.compareTo(data) == 0) {
                    ca[l].next = ca[p].next;
                    free(p);
                    p = ca[l].next;
                } else {
                    l = p;
                    p = ca[p].next;
                }
            }
        }
    }

    void insertSorted(T data, int l) {
        int p = malloc();
        if (p == 0) return;

        int prev = l;
        int curr = ca[l].next;

        while (curr != 0 && ca[curr].data.compareTo(data) < 0) {
            prev = curr;
            curr = ca[curr].next;
        }

        ca[p] = new CNode(data, curr);
        ca[prev].next = p;
    }
}
