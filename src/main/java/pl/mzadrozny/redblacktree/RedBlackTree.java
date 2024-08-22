package pl.mzadrozny.redblacktree;


public class RedBlackTree<T extends Comparable<T>> implements BinarySearchTree<T> {

    private Node<T> root;

    private int size;

    public RedBlackTree() {
        this.root = new NilNode<>();
        this.size = 0;
    }

    @Override
    public Node<T> getRoot() {
        return root;
    }

    //    O(1)
    @Override
    public int size() {
        return this.size;
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

        while (current.getClass() != NilNode.class) {
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

        this.size++;
    }

    private void insertFixup(Node<T> node) {

        while (node.getParent() != null && node.getParent().getColor() == Node.RED) {

            if (node.getParent() == node.getParent().getParent().getLeft()) {
                Node<T> current = node.getParent().getParent().getRight();

                if (current.getColor() == Node.RED) {
                    node.getParent().setColor(Node.BLACK);
                    current.setColor(Node.BLACK);
                    node.getParent().getParent().setColor(Node.RED);
                    node = node.getParent().getParent();
                } else {
                    if (node == node.getParent().getRight()) {
                        node = node.getParent();
                        rotateLeft(node);
                    }

                    node.getParent().setColor(Node.BLACK);
                    node.getParent().getParent().setColor(Node.RED);
                    rotateRight(node.getParent().getParent());
                }
            } else {
                Node<T> current = node.getParent().getParent().getLeft();

                if (current.getColor() == Node.RED) {
                    node.getParent().setColor(Node.BLACK);
                    current.setColor(Node.BLACK);
                    node.getParent().getParent().setColor(Node.RED);
                    node = node.getParent().getParent();
                } else {
                    if (node == node.getParent().getLeft()) {
                        node = node.getParent();
                        rotateRight(node);
                    }

                    node.getParent().setColor(Node.BLACK);
                    node.getParent().getParent().setColor(Node.RED);
                    rotateLeft(node.getParent().getParent());
                }
            }

            if (node == root) {
                break;
            }
        }

        root.setColor(Node.BLACK);
    }

    // O(log n)
    @Override
    public void deleteNode(T key) {

        Node<T> nodeToDelete = searchNode(key);

        if (nodeToDelete == null) {
            return;
        }

        Node<T> y = nodeToDelete;
        Node<T> nodeToDeleteChild;
        boolean deletedNodeColor = y.getColor();

        if (nodeToDelete.getLeft().getClass() == NilNode.class) {
            nodeToDeleteChild = nodeToDelete.getRight();
            transplant(nodeToDelete, nodeToDelete.getRight());
        } else if (nodeToDelete.getRight().getClass() == NilNode.class) {
            nodeToDeleteChild = nodeToDelete.getLeft();
            transplant(nodeToDelete, nodeToDelete.getLeft());
        } else {
            y = minimum(nodeToDelete.getRight());
            deletedNodeColor = y.getColor();
            nodeToDeleteChild = y.getRight();

            if (y.getParent() == nodeToDelete) {
                nodeToDeleteChild.setParent(y);
            } else {
                transplant(y, y.getRight());
                y.setRight(nodeToDelete.getRight());
                y.getRight().setParent(y);
            }

            transplant(nodeToDelete, y);
            y.setLeft(nodeToDelete.getLeft());
            y.getLeft().setParent(y);
            y.setColor(nodeToDelete.getColor());
        }

        if (deletedNodeColor == Node.BLACK) {
            deleteFixup(nodeToDeleteChild);
        }

        this.size--;
    }

    private Node<T> minimum(Node<T> node) {
        while (node.getLeft().getClass() != NilNode.class) {
            node = node.getLeft();
        }

        return node;
    }

    private void transplant(Node<T> nodeToDelete, Node<T> nodeToMove) {
        if (nodeToDelete.getParent() == null) {
            root = nodeToMove;
        } else if (nodeToDelete == nodeToDelete.getParent().getLeft()) {
            nodeToDelete.getParent().setLeft(nodeToMove);
        } else {
            nodeToDelete.getParent().setRight(nodeToMove);
        }

        nodeToMove.setParent(nodeToDelete.getParent());
    }

    private void deleteFixup(Node<T> child) {
        while (child != root && child.getColor() == Node.BLACK) {
            if (child == child.getParent().getLeft()) {
                Node<T> w = child.getParent().getRight();

//                Type 1
                if (w.getColor() == Node.RED) {
                    w.setColor(Node.BLACK);
                    child.getParent().setColor(Node.RED);
                    rotateLeft(child.getParent());
                    w = child.getParent().getRight();
                }

//                Type 2
                if (w.getLeft().getColor() == Node.BLACK && w.getRight().getColor() == Node.BLACK) {
                    w.setColor(Node.RED);
                    child = child.getParent();
                } else {

//                    Type 3
                    if (w.getRight().getColor() == Node.BLACK) {
                        w.getLeft().setColor(Node.BLACK);
                        w.setColor(Node.RED);
                        rotateRight(w);
                        w = child.getParent().getRight();
                    }

//                    Type 4
                    w.setColor(child.getParent().getColor());
                    child.getParent().setColor(Node.BLACK);
                    w.getRight().setColor(Node.BLACK);
                    rotateLeft(child.getParent());
                    child = root;
                }
            } else {
                Node<T> w = child.getParent().getLeft();

//                Type 1
                if (w.getColor() == Node.RED) {
                    w.setColor(Node.BLACK);
                    child.getParent().setColor(Node.RED);
                    rotateRight(child.getParent());
                    w = child.getParent().getLeft();
                }

//                Type 2
                if (w.getRight().getColor() == Node.BLACK && w.getLeft().getColor() == Node.BLACK) {
                    w.setColor(Node.RED);
                    child = child.getParent();
                } else {

//                    Type 3
                    if (w.getLeft().getColor() == Node.BLACK) {
                        w.getRight().setColor(Node.BLACK);
                        w.setColor(Node.RED);
                        rotateLeft(w);
                        w = child.getParent().getLeft();
                    }

//                    Type 4
                    w.setColor(child.getParent().getColor());
                    child.getParent().setColor(Node.BLACK);
                    w.getLeft().setColor(Node.BLACK);
                    rotateRight(child.getParent());
                    child = root;
                }
            }

            child.setColor(Node.BLACK);
        }
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
