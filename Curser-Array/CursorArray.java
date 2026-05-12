public class CursorArray<T extends Comparable<T>> {
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

    void merge(int l1, int l2) {
        int p1 = l1;
        int c1 = ca[l1].next;
        int c2 = ca[l2].next;
        while (c1 != 0 && c2 != 0) {
            if (ca[c2].data.compareTo(ca[c1].data) < 0) {
                int nextC2 = ca[c2].next;
                ca[p1].next = c2;
                ca[c2].next = c1;
                p1 = c2;
                c2 = nextC2;
            } else {
                p1 = c1;
                c1 = ca[c1].next;
            }
        }
        if (c2 != 0) {
            ca[p1].next = c2;
        }
        ca[l2].next = 0;
    }

    boolean isSubset(int l1, int l2) {
        int p1 = ca[l1].next;
        while (p1 != 0) {
            boolean found = false;
            int p2 = ca[l2].next;
            while (p2 != 0) {
                if (ca[p1].data.compareTo(ca[p2].data) == 0) {
                    found = true;
                    break;
                }
                p2 = ca[p2].next;
            }
            if (!found) {
                return false;
            }
            p1 = ca[p1].next;
        }
        return true;
    }

    void reverse(int l) {
        int prev = 0;
        int curr = ca[l].next;
        while (curr != 0) {
            int next = ca[curr].next;
            ca[curr].next = prev;
            prev = curr;
            curr = next;
        }
        ca[l].next = prev;
    }

    void swap(int l, T x, T y) {

        if (x.compareTo(y) == 0)
            return;

        int prevX = l;
        int currX = ca[l].next;

        while (currX != 0 &&
                ca[currX].data.compareTo(x) != 0) {

            prevX = currX;
            currX = ca[currX].next;
        }

        int prevY = l;
        int currY = ca[l].next;

        while (currY != 0 &&
                ca[currY].data.compareTo(y) != 0) {

            prevY = currY;
            currY = ca[currY].next;
        }

        if (currX == 0 || currY == 0)
            return;

        ca[prevX].next = currY;
        ca[prevY].next = currX;

        int temp = ca[currX].next;
        ca[currX].next = ca[currY].next;
        ca[currY].next = temp;
    }


}


