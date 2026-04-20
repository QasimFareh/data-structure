package lab6;

public class Test {
    public static void main(String[] args) {
        DoubleLinkedList<Integer> lolo = new DoubleLinkedList<>();
        lolo.insert(55);
        lolo.insert(50);
        lolo.insert(60);
        lolo.insert(58);

        lolo.delete(50);

        lolo.traverse();
    }
}
