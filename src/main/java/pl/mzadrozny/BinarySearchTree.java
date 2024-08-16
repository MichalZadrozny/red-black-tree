package pl.mzadrozny;

public interface BinarySearchTree<T extends Comparable<T>> {

    Node<T> getRoot();

    Node<T> searchNode(T key);

    void insertNode(T key);

    void deleteNode(T key);
}
