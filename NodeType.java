public class NodeType<T extends Comparable<T>>{
    public T info;
    public NodeType<T> left;
    public NodeType<T> right;

    public NodeType(T info) {
        this.info = info;
        this.left = null;
        this.right = null;
    }
}
