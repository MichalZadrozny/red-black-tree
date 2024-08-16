package pl.mzadrozny;

public class NilNode<T extends Comparable<T>> extends Node<T> {

    private static final NilNode<?> instance = new NilNode<>();

    private NilNode() {
        super(null);
        this.setColor(BLACK);
    }

    public static <T extends Comparable<T>> NilNode<T> getInstance() {
        return (NilNode<T>) instance;
    }
}
