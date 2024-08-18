package pl.mzadrozny;

public class NilNode<T extends Comparable<T>> extends Node<T> {

    public NilNode() {
        this.setColor(BLACK);
    }
}
