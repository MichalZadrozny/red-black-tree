package pl.mzadrozny;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RedBlackTreeTest {

    @Test
    void should_addAsRoot_when_addingFirstElement() {
//        given
        RedBlackTree<Integer> tree = new RedBlackTree<>();

//        when
        tree.insertNode(5);

//        then
        assertEquals(Integer.valueOf(5), tree.getRoot().getData());
    }

    @Test
    void should_addRootAsBlack_when_addingFirstElement() {
//        given
        RedBlackTree<Integer> tree = new RedBlackTree<>();

//        when
        tree.insertNode(5);

//        then
        assertFalse(tree.getRoot().getColor());
    }

    @Test
    void should_addToTheLeft_when_addingSmallerElement() {
//        given
        RedBlackTree<Integer> tree = new RedBlackTree<>();

//        when
        tree.insertNode(5);
        tree.insertNode(1);

//        then
        assertEquals(Integer.valueOf(1), tree.getRoot().getLeft().getData());
    }

    @Test
    void should_addToTheRight_when_addingBiggerElement() {
//        given
        RedBlackTree<Integer> tree = new RedBlackTree<>();

//        when
        tree.insertNode(5);
        tree.insertNode(6);

//        then
        assertEquals(Integer.valueOf(6), tree.getRoot().getRight().getData());
    }

    @Test
    void should_fixTree_when_addingSecondSmallerElement() {
//        given
        RedBlackTree<Integer> tree = new RedBlackTree<>();

//        when
        tree.insertNode(5);
        tree.insertNode(3);
        tree.insertNode(1);

//        then
        assertEquals(Integer.valueOf(3), tree.getRoot().getData());
        assertEquals(Integer.valueOf(1), tree.getRoot().getLeft().getData());
        assertEquals(Integer.valueOf(5), tree.getRoot().getRight().getData());
    }

    @Test
    void should_fixTree_when_addingSecondBiggerElement() {
//        given
        RedBlackTree<Integer> tree = new RedBlackTree<>();

//        when
        tree.insertNode(5);
        tree.insertNode(7);
        tree.insertNode(9);

//        then
        assertEquals(Integer.valueOf(7), tree.getRoot().getData());
        assertEquals(Integer.valueOf(5), tree.getRoot().getLeft().getData());
        assertEquals(Integer.valueOf(9), tree.getRoot().getRight().getData());
    }

    @Test
    void should_fixTree_when_addingBiggerAndThenSmallerElement() {
//        given
        RedBlackTree<Integer> tree = new RedBlackTree<>();

//        when
        tree.insertNode(5);
        tree.insertNode(10);
        tree.insertNode(9);

//        then
        assertEquals(Integer.valueOf(9), tree.getRoot().getData());
        assertEquals(Integer.valueOf(5), tree.getRoot().getLeft().getData());
        assertEquals(Integer.valueOf(10), tree.getRoot().getRight().getData());
    }

    @Test
    void should_fixTree_when_addingSmallerAndThenBiggerElement() {
//        given
        RedBlackTree<Integer> tree = new RedBlackTree<>();

//        when
        tree.insertNode(5);
        tree.insertNode(1);
        tree.insertNode(10);

//        then
        assertEquals(Integer.valueOf(5), tree.getRoot().getData());
        assertEquals(Integer.valueOf(1), tree.getRoot().getLeft().getData());
        assertEquals(Integer.valueOf(10), tree.getRoot().getRight().getData());
    }

    @Test
    void should_throwIllegalArgumentException_when_addingDuplicate() {
//        given
        RedBlackTree<Integer> tree = new RedBlackTree<>();

//        when
        tree.insertNode(5);

//        then
        assertThrows(IllegalArgumentException.class, () -> tree.insertNode(5));
    }

    @Test
    void should_returnNode_when_valueIsInTheTree() {
//        given
        RedBlackTree<Integer> tree = new RedBlackTree<>();

//        when
        tree.insertNode(5);
        tree.insertNode(1);
        tree.insertNode(24);
        tree.insertNode(-5);
        tree.insertNode(85);
        tree.insertNode(32);

//        then
        assertEquals(Integer.valueOf(-5), tree.searchNode(-5).getData());
    }

    @Test
    void should_returnNull_when_valueIsNotInTheTree() {
//        given
        RedBlackTree<Integer> tree = new RedBlackTree<>();

//        when
        tree.insertNode(5);
        tree.insertNode(1);
        tree.insertNode(24);
        tree.insertNode(-5);
        tree.insertNode(85);
        tree.insertNode(32);

//        then
        assertNull(tree.searchNode(-50));
    }

    @Test
    void should_deleteRoot_when_thereIsOnlyRoot() {
//        given
        RedBlackTree<Integer> tree = new RedBlackTree<>();

//        when
        tree.insertNode(5);
        tree.deleteNode(5);
//        then
        assertEquals(NilNode.class, tree.getRoot().getClass());
    }

    @Test
    void should_deleteRoot_when_thereIsNotOnlyRoot() {
//        given
        RedBlackTree<Integer> tree = new RedBlackTree<>();

//        when
        tree.insertNode(5);
        tree.insertNode(-2);
        tree.insertNode(9);
        tree.deleteNode(5);

//        then
        assertEquals(9, tree.getRoot().getData());
        assertEquals(-2, tree.getRoot().getLeft().getData());
        assertEquals(NilNode.class, tree.getRoot().getRight().getClass());
    }

    @Test
    void should_deleteLeftLeaf() {
//        given
        RedBlackTree<Integer> tree = new RedBlackTree<>();

//        when
        tree.insertNode(5);
        tree.insertNode(1);
        tree.insertNode(24);
        tree.insertNode(-5);
        tree.insertNode(85);
        tree.insertNode(32);
        tree.deleteNode(-5);

//        then
        assertEquals(5, tree.getRoot().getData());
        assertEquals(1, tree.getRoot().getLeft().getData());
        assertEquals(NilNode.class, tree.getRoot().getLeft().getLeft().getClass());
        assertEquals(32, tree.getRoot().getRight().getData());
        assertEquals(24, tree.getRoot().getRight().getLeft().getData());
        assertEquals(85, tree.getRoot().getRight().getRight().getData());
    }

    @Test
    void should_deleteRightLeaf() {
//        given
        RedBlackTree<Integer> tree = new RedBlackTree<>();

//        when
        tree.insertNode(5);
        tree.insertNode(1);
        tree.insertNode(24);
        tree.insertNode(-5);
        tree.insertNode(85);
        tree.insertNode(32);
        tree.deleteNode(85);

//        then
        assertEquals(5, tree.getRoot().getData());
        assertEquals(1, tree.getRoot().getLeft().getData());
        assertEquals(-5, tree.getRoot().getLeft().getLeft().getData());
        assertEquals(32, tree.getRoot().getRight().getData());
        assertEquals(24, tree.getRoot().getRight().getLeft().getData());
        assertEquals(NilNode.class, tree.getRoot().getRight().getRight().getClass());
    }

    @Test
    void should_deleteNode_when_rightChildIsNil() {
//        given
        RedBlackTree<Integer> tree = new RedBlackTree<>();

//        when
        tree.insertNode(5);
        tree.insertNode(1);
        tree.insertNode(24);
        tree.insertNode(-5);
        tree.insertNode(85);
        tree.insertNode(32);
        tree.deleteNode(1);

//        then
        assertEquals(5, tree.getRoot().getData());
        assertEquals(-5, tree.getRoot().getLeft().getData());
        assertEquals(32, tree.getRoot().getRight().getData());
        assertEquals(24, tree.getRoot().getRight().getLeft().getData());
        assertEquals(85, tree.getRoot().getRight().getRight().getData());
    }

    @Test
    void should_deleteNode_when_leftChildIsNil() {
//        given
        RedBlackTree<Integer> tree = new RedBlackTree<>();

//        when
        tree.insertNode(5);
        tree.insertNode(-5);
        tree.insertNode(24);
        tree.insertNode(4);
        tree.insertNode(85);
        tree.insertNode(32);
        tree.deleteNode(24);
        tree.deleteNode(32);

//        then
        assertEquals(5, tree.getRoot().getData());
        assertEquals(-5, tree.getRoot().getLeft().getData());
        assertEquals(4, tree.getRoot().getLeft().getRight().getData());
        assertEquals(85, tree.getRoot().getRight().getData());
    }

    @Test
    void should_deleteNode_when_bothChildrenAreNil() {
//        given
        RedBlackTree<Integer> tree = new RedBlackTree<>();

//        when
        tree.insertNode(5);
        tree.insertNode(-5);
        tree.insertNode(24);
        tree.insertNode(85);
        tree.insertNode(32);
        tree.deleteNode(-5);

//        then
        assertEquals(32, tree.getRoot().getData());
        assertEquals(5, tree.getRoot().getLeft().getData());
        assertEquals(24, tree.getRoot().getLeft().getRight().getData());
        assertEquals(85, tree.getRoot().getRight().getData());
    }

    @Test
    void should_return0_when_checkingSize_given_emptyTree() {
//        given
        RedBlackTree<Integer> tree = new RedBlackTree<>();

//        when
//        then
        assertEquals(Integer.valueOf(0), tree.size());
    }

    @Test
    void should_return1_when_checkingSize_given_treeWithOneElement() {
//        given
        RedBlackTree<Integer> tree = new RedBlackTree<>();

//        when
        tree.insertNode(5);

//        then
        assertEquals(Integer.valueOf(1), tree.size());
    }

    @Test
    void should_return5_when_checkingSize_given_treeWithFiveElement() {
//        given
        RedBlackTree<Integer> tree = new RedBlackTree<>();

//        when
        tree.insertNode(5);
        tree.insertNode(-39);
        tree.insertNode(30);
        tree.insertNode(12);
        tree.insertNode(1);

//        then
        assertEquals(Integer.valueOf(5), tree.size());
    }

    @Test
    void should_return4_when_checkingSizeAfterDeletingOneElement_given_treeWithFiveElement() {
//        given
        RedBlackTree<Integer> tree = new RedBlackTree<>();

//        when
        tree.insertNode(5);
        tree.insertNode(-39);
        tree.insertNode(30);
        tree.insertNode(12);
        tree.insertNode(1);
        tree.deleteNode(5);

//        then
        assertEquals(Integer.valueOf(4), tree.size());
    }

    @Test
    void should_return5_when_checkingSizeAfterDeletingOneElementNotPresentInTree_given_treeWithFiveElement() {
//        given
        RedBlackTree<Integer> tree = new RedBlackTree<>();

//        when
        tree.insertNode(5);
        tree.insertNode(-39);
        tree.insertNode(30);
        tree.insertNode(12);
        tree.insertNode(1);
        tree.deleteNode(99);

//        then
        assertEquals(Integer.valueOf(5), tree.size());
    }
}
