public class BST<T extends Comparable<T>> {
    TNode<T> root;

    public void insert(T data) {
        if (root == null)
            root = new TNode<>(data);
        else
            insert(data, root);
    }

    private void insert(T data, TNode node) {
        if (node != null) {
            if (node.data.compareTo(data) > 0) {
                if (node.left == null)
                    node.left = new TNode<>(data);
                else
                    insert(data, node.left);
            } else {
                if (node.right == null)
                    node.right = new TNode<>(data);
                else
                    insert(data, node.right);
            }
        }
    }

    public void inOderTraversal() {
        if (root != null)
            inOderTraversal(root);
    }

    private void inOderTraversal(TNode node) {
        if (node != null) {
            inOderTraversal(node.left);
            System.out.print(node + " ");
            inOderTraversal(node.right);
        }
    }

    public boolean search(T data) {
        if (root != null)
            return search(data, root);
        else
            return false;
    }

    private boolean search(T data, TNode node) {
        if (node != null) {
            if (node.data.compareTo(data) == 0)
                return true;
            if (node.data.compareTo(data) > 0)
                return search(data, node.left);
            else
                return search(data, node.right);
        } else
            return false;
    }

    public int height() {
        return height(root);
    }

    int height(TNode node) {
        if (node == null)
            return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    public int size() {
        return size(root);
    }

    private int size(TNode node) {
        if (node == null)
            return 0;
        return 1 + size(node.left) + size(node.right);
    }

    public void printLeafs() {
        if (root != null)
            printLeafs(root);
    }

    private void printLeafs(TNode node) {
        if (node != null) {
            printLeafs(node.left);
            if (node.left == null && node.right == null)
                System.out.print(node + " ");
            printLeafs(node.right);
        }
    }

    public boolean isEmpty() {
        return root == null;
    }
}