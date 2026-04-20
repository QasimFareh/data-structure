package Lab;

public class TestLinkedList {
    public static void main(String[] args) {
        Node<Integer> n1 = new Node<Integer>(55);
        Node<Integer> n2 = new Node<Integer>(60);
        Node<Integer> n3 = new Node<Integer>(65);
        Node<Integer> n4 = new Node<Integer>(70);
        Node<Integer> n5 = new Node<Integer>(75);
        Node<Integer> n6 = new Node<Integer>(80);


        LinkedList<Integer> lolo = new LinkedList<>();

        lolo.head = n1;

        n1.setNext(n2);
        n2.setNext(n3);
        n3.setNext(n4);
        n4.setNext(n5);
        n5.setNext(n6);

        lolo.traverse();
        System.out.println();
        System.out.println(lolo.countRec(60));



    }
}
