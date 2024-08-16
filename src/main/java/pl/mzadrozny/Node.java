package pl.mzadrozny;

public class Node<T extends Comparable<T>> {
    private T data;
    private boolean color;

    private Node<T> left;
    private Node<T> right;
    private Node<T> parent;

    protected static final boolean RED = true;
    protected static final boolean BLACK = false;

    public Node(T data) {
        this.data = data;
        this.color = RED;

        this.parent = null;
        this.left = NilNode.getInstance();
        this.right = NilNode.getInstance();
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean getColor() {
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }

    public Node<T> getLeft() {
        return left;
    }

    public void setLeft(Node<T> left) {
        this.left = left;
    }

    public Node<T> getRight() {
        return right;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }

    public Node<T> getParent() {
        return parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                '}';
    }
}
