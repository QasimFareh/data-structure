package lab6;


public class Node<T extends Comparable<T>> {
    T data ;
    Node next ;
    Node prev ;

    public Node(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }



    public void setData(T data) {
        this.data = data;
    }



    public Node getNext() {
        return next;
    }



    public void setNext(Node next) {
        this.next = next;
    }

    public Node getPrev() {
        return prev;
    }



    public void setPrev(Node prev) {
        this.prev = prev;
    }



    @Override
    public String toString() {
        return data + "";
    }



}