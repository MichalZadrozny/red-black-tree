package pl.mzadrozny;


import static pl.mzadrozny.Node.BLACK;
import static pl.mzadrozny.Node.RED;

public class RedBlackTree<T extends Comparable<T>> implements BinarySearchTree<T> {

    private Node<T> root;


    public RedBlackTree() {
        this.root = NilNode.getInstance();
    }

    @Override
    public Node<T> getRoot() {
        return root;
    }

    //    O(log n)
    @Override
    public Node<T> searchNode(T key) {
        Node<T> node = root;

        while (node != null && node.getData() != null) {

            if (key.compareTo(node.getData()) == 0) {
                return node;
            } else if (key.compareTo(node.getData()) < 0) {
                node = node.getLeft();
            } else {
                node = node.getRight();
            }
        }

        return null;
    }

    //    O(log n)
    @Override
    public void insertNode(T data) {

        Node<T> newNode = new Node<>(data);
        Node<T> parent = null;
        Node<T> current = root;

        while (current != NilNode.getInstance()) {
            parent = current;

            if (newNode.getData().compareTo(current.getData()) < 0) {
                current = current.getLeft();
            } else if (newNode.getData().compareTo(current.getData()) > 0) {
                current = current.getRight();
            } else {
                throw new IllegalArgumentException("Tree already contains a node with value: " + data);
            }
        }

        newNode.setParent(parent);

        if (parent == null) {
            root = newNode;
        } else if (newNode.getData().compareTo(parent.getData()) < 0) {
            parent.setLeft(newNode);
        } else {
            parent.setRight(newNode);
        }

        insertFixup(newNode);
    }

    private void insertFixup(Node<T> node) {

        while (node.getParent() != null && node.getParent().getColor() == RED) {

            if (node.getParent() == node.getParent().getParent().getLeft()) {
                Node<T> current = node.getParent().getParent().getRight();

                if (current.getColor() == RED) {
                    node.getParent().setColor(BLACK);
                    current.setColor(BLACK);
                    node.getParent().getParent().setColor(RED);
                    node = node.getParent().getParent();
                } else {
                    if (node == node.getParent().getRight()) {
                        node = node.getParent();
                        rotateLeft(node);
                    }

                    node.getParent().setColor(BLACK);
                    node.getParent().getParent().setColor(RED);
                    rotateRight(node.getParent().getParent());
                }
            } else {
                Node<T> current = node.getParent().getParent().getLeft();

                if (current.getColor() == RED) {
                    node.getParent().setColor(BLACK);
                    current.setColor(BLACK);
                    node.getParent().getParent().setColor(RED);
                    node = node.getParent().getParent();
                } else {
                    if (node == node.getParent().getLeft()) {
                        node = node.getParent();
                        rotateRight(node);
                    }

                    node.getParent().setColor(BLACK);
                    node.getParent().getParent().setColor(RED);
                    rotateLeft(node.getParent().getParent());
                }
            }

            if (node == root) {
                break;
            }
        }

        root.setColor(BLACK);
    }


    @Override
    public void deleteNode(T key) {

    }

    private void rotateRight(Node<T> node) {
        Node<T> parent = node.getParent();
        Node<T> leftChild = node.getLeft();

        node.setLeft(leftChild.getRight());
        if (leftChild.getRight() != null) {
            leftChild.getRight().setParent(node);
        }

        leftChild.setRight(node);
        node.setParent(leftChild);

        replaceParentsChild(parent, node, leftChild);
    }

    private void rotateLeft(Node<T> node) {
        Node<T> parent = node.getParent();
        Node<T> rightChild = node.getRight();

        node.setRight(rightChild.getLeft());
        if (rightChild.getLeft() != null) {
            rightChild.getLeft().setParent(node);
        }

        rightChild.setLeft(node);
        node.setParent(rightChild);

        replaceParentsChild(parent, node, rightChild);
    }

    private void replaceParentsChild(Node<T> parent, Node<T> oldChild, Node<T> newChild) {
        if (parent == null) {
            root = newChild;
        } else if (parent.getLeft() == oldChild) {
            parent.setLeft(newChild);
        } else if (parent.getRight() == oldChild) {
            parent.setRight(newChild);
        } else {
            throw new IllegalStateException("Node is not a child of its parent");
        }

        if (newChild != null) {
            newChild.setParent(parent);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        appendNodeToStringRecursive(getRoot(), builder);
        return builder.toString();
    }

    private void appendNodeToStringRecursive(Node<T> node, StringBuilder builder) {
        builder.append(node.getData());

        if (node.getLeft() != null) {
            builder.append(" L{");
            appendNodeToStringRecursive(node.getLeft(), builder);
            builder.append('}');
        }
        if (node.getRight() != null) {
            builder.append(" R{");
            appendNodeToStringRecursive(node.getRight(), builder);
            builder.append('}');
        }
    }
}
