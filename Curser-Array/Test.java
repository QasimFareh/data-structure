public class Test {
    static void main(String[] args) {
        CursorArray ca = new CursorArray(11);
        int num = ca.createList();

        ca.insertSorted(10,num);
        ca.insertSorted(5 , num);
        ca.insertSorted(60 ,num);
        ca.traverse(num);

    }
}
