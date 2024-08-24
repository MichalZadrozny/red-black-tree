package pl.mzadrozny.redblacktree;

public interface BinarySearchTree<T extends Comparable<T>> {

    Node<T> getRoot();

    int size();

    Node<T> searchNode(T key);

    void insertNode(T key);

    void deleteNode(T key);

    void deleteNode(Node<T> node);
}
