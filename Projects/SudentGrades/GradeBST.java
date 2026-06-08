public class GradeBST {
    BST<GradeRange> tree = new BST<>();

    public GradeBST() {
        for (int i = 0; i < 100; i += 10) {
            tree.insert(new GradeRange(i, i + 9));
        }
    }

    public void addStudent(Student s) {
        addStudent(s, tree.root);
    }

    private void addStudent(Student s, TNode<GradeRange> node) {
        if (node == null) return;

        int low = node.data.low;
        int high = low + 9;

        if (s.grade >= low && s.grade <= high) {
            node.data.addStudent(s);
        } else if (s.grade < low) {
            addStudent(s, node.left);
        } else {
            addStudent(s, node.right);
        }
    }

    public void printAll() {
        printAll(tree.root);
    }

    private void printAll(TNode<GradeRange> node) {
        if (node == null) return;
        printAll(node.left);
        System.out.print(node.data);
        node.data.students.traverse();
        printAll(node.right);
    }
}