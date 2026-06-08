public class TNode <T extends Comparable<T>> {
    TNode<T> left,right;
    T data;

    TNode(T data){
        this.data=data;
    }

    @Override
    public String toString() {
        return  data + "";
    }
}