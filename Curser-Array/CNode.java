public class CNode < T extends Comparable<CNode>> {
    T data;
    int next;

    public CNode(T data, int next) {
        this.data = data;
        this.next = next;
    }
}

