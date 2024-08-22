package pl.mzadrozny.redblacktree;

public class NilNode<T extends Comparable<T>> extends Node<T> {

    public NilNode() {
        this.setColor(BLACK);
    }
}
