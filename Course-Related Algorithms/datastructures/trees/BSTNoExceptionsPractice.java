/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computerscience.algorithms.datastructures.trees;

import edu.princeton.cs.algs4.Queue;
import java.util.NoSuchElementException;

/**
 *
 * @author Eduardo
 * @param <Key>
 * @param <Value>
 */
public class BSTNoExceptionsPractice<Key extends Comparable<Key>, Value> {

    private Node root;

    private class Node {

        private Key key;
        private Value val;
        private Node left;
        private Node right;
        private int size;

        public Node(Key key, Value val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) {
            return 0;
        } else {
            return x.size;
        }
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        if (x == null) {
            return null;
        }

        int cmp = key.compareTo(x.key);

        if (cmp < 0) {
            return get(x.left, key);
        } else if (cmp > 0) {
            return get(x.right, key);
        } else {
            return x.val;
        }
    }

    public void put(Key key, Value val) {
        root = put(root, key, val);
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null) {
            return new Node(key, val, 1);
        }

        int cmp = key.compareTo(x.key);

        if (cmp < 0) {
            x.left = put(x.left, key, val);
        } else if (cmp > 0) {
            x.right = put(x.right, key, val);
        } else {
            x.val = val;
        }

        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void deleteMin() {
        root = deleteMin(root);
    }

    private Node deleteMin(Node x) {
        if (x.left == null) {
            return x.right;
        }

        x.left = deleteMin(x.left);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void deleteMax() {
        root = deleteMax(root);
    }

    private Node deleteMax(Node x) {
        if (x.right == null) {
            return x.left;
        }
        x.right = deleteMax(x.right);
        x.size = size(x.left) + size(x.right);
        return x;
    }

    public void delete(Key key) {
        root = delete(root, key);
    }

    private Node delete(Node x, Key key) {
        if (x == null) {
            return null;
        }

        int cmp = key.compareTo(x.key);

        if (cmp < 0) {
            x.left = delete(x.left, key);
        } else if (cmp > 0) {
            x.right = delete(x.right, key);
        } else {
            if (x.right == null) {
                return x.left;
            }
            if (x.left == null) {
                return x.right;
            }

            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }

        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    public Key min() {
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.left == null) {
            return x;
        } else {
            return min(x.left);
        }
    }

    public Key max() {
        return max(root).key;
    }

    private Node max(Node x) {
        if (x.right == null) {
            return x;
        } else {
            return min(x.right);
        }
    }

    public Key floor(Key key) {
        Node x = floor(root, key);
        if (x.key == null) {
            throw new NoSuchElementException("argument to floor() is too small");
        } else {
            return x.key;
        }
    }

    private Node floor(Node x, Key key) {
        if (x == null) {
            return null;
        }

        int cmp = key.compareTo(x.key);

        if (cmp == 0) {
            return x;
        }
        if (cmp < 0) {
            return floor(x.left, key);
        }

        Node t = floor(x.right, key);
        if (t != null) {
            return t;
        } else {
            return x;
        }
    }

    public Key ceiling(Key key) {
        Node x = ceiling(root, key);
        if (x.key == null) {
            throw new NoSuchElementException("argument to ceiling() is too large");
        } else {
            return x.key;
        }
    }

    private Node ceiling(Node x, Key key) {
        if (x == null) {
            return null;
        }

        int cmp = key.compareTo(x.key);

        if (cmp == 0) {
            return x;
        } else if (cmp < 0) {
            Node t = ceiling(x.left, key);
            if (t != null) {
                return t;
            } else {
                return x;
            }
        }

        return ceiling(x.right, key);
    }

    public Key select(int rank) {
        if (rank < 0 || rank >= size()) {
            throw new IllegalArgumentException("argument of select() is invalid");
        }

        return select(root, rank);
    }

    private Key select(Node x, int rank) {
        if (x == null) {
            return null;
        }

        int leftSize = size(x.left);

        if (leftSize > rank) {
            return select(x.left, rank);
        } else if (leftSize < rank) {
            return select(x.right, rank - leftSize - 1);
        } else {
            return x.key;
        }
    }

    public int rank(Key key) {
        return rank(root, key);
    }

    private int rank(Node x, Key key) {
        if (x == null) {
            return 0;
        }

        int cmp = key.compareTo(x.key);

        if (cmp < 0) {
            return rank(x.left, key);
        } else if (cmp > 0) {
            return size(x.left) + rank(x.right, key) + 1;
        } else {
            return size(x.left);
        }
    }

    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    private Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> queue = new Queue<>();
        keys(root, queue, lo, hi);
        return queue;
    }

    private void keys(Node x, Queue<Key> queue, Key lo, Key hi) {
        if (x == null) {
            return;
        }

        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);

        if (cmplo < 0) {
            keys(x.left, queue, lo, hi);
        }

        if (cmplo <= 0 && cmphi >= 0) {
            queue.enqueue(x.key);
        }

        if (cmphi > 0) {
            keys(x.right, queue, lo, hi);
        }
    }

    public int size(Key lo, Key hi) {
        if (lo.compareTo(hi) > 0) {
            return 0;
        }

        if (contains(hi)) {
            return rank(hi) - rank(lo) + 1;
        } else {
            return rank(hi) - rank(lo);
        }
    }

    public int height() {
        return height(root);
    }

    private int height(Node x) {
        if (x == null) {
            return -1;
        }

        return 1 + Math.max(height(x.left), height(x.right));
    }

    /**
     * ***********************************************************************
     * Check integrity of BST data structure.
     * *************************************************************************
     */
    private boolean check() {
        if (!isBST()) {
            System.out.println("Not in symmetric order");
        }
        if (!isSizeConsistent()) {
            System.out.println("Subtree counts not consistent");
        }
        if (!isRankConsistent()) {
            System.out.println("Ranks not consistent");
        }
        return isBST() && isSizeConsistent() && isRankConsistent();
    }

    // does this binary tree satisfy symmetric order?
    // Note: this test also ensures that data structure is a binary tree since order is strict
    private boolean isBST() {
        return isBST(root, null, null);
    }

    // is the tree rooted at x a BST with all keys strictly between min and max
    // (if min or max is null, treat as empty constraint)
    // Credit: Bob Dondero's elegant solution
    private boolean isBST(Node x, Key min, Key max) {
        if (x == null) {
            return true;
        }
        if (min != null && x.key.compareTo(min) <= 0) {
            return false;
        }
        if (max != null && x.key.compareTo(max) >= 0) {
            return false;
        }
        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
    }

    // are the size fields correct?
    private boolean isSizeConsistent() {
        return isSizeConsistent(root);
    }

    private boolean isSizeConsistent(Node x) {
        if (x == null) {
            return true;
        }
        if (x.size != size(x.left) + size(x.right) + 1) {
            return false;
        }
        return isSizeConsistent(x.left) && isSizeConsistent(x.right);
    }

    // check that ranks are consistent
    private boolean isRankConsistent() {
        for (int i = 0; i < size(); i++) {
            if (i != rank(select(i))) {
                return false;
            }
        }
        for (Key key : keys()) {
            if (key.compareTo(select(rank(key))) != 0) {
                return false;
            }
        }
        return true;
    }

    public Iterable<Key> levelOrder() {
        Queue<Key> keys = new Queue<>();
        Queue<Node> queue = new Queue<>();
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            Node x = queue.dequeue();
            if (x == null) {
                continue;
            }
            keys.enqueue(x.key);
            queue.enqueue(x.left);
            queue.enqueue(x.right);
        }

        return keys;
    }

    public static void main(String[] args) {
        BSTNoExceptionsPractice<String, Integer> bst = new BSTNoExceptionsPractice<>();
        System.out.println(bst.isEmpty()); // true
        bst.put("S", 1);
        bst.put("E", 2);
        bst.put("X", 3);
        bst.put("A", 4);
        bst.put("R", 5);
        bst.put("C", 6);
        bst.put("H", 7);
        bst.put("M", 8);
        System.out.println(bst.height()); // 4
        bst.delete("M");
        System.out.println(bst.floor("G")); // E
        System.out.println(bst.ceiling("G")); // H
        System.out.println(bst.height()); // 3
        System.out.println(bst.levelOrder()); // S E X A R C H [M] <-- deleted
        System.out.println(bst.keys()); // A C E H R S X
        System.out.println(bst.select(3)); // H
        System.out.println(bst.size()); // 7
        System.out.println(bst.rank("F")); // 3
        System.out.println(bst.contains("E")); // true
        System.out.println(bst.contains("F")); // false
        System.out.println(bst.get("X")); // 3
        System.out.println("Min: " + bst.min() + " Max: " + bst.max()); // A | X
        bst.deleteMin();
        System.out.println(bst.keys()); // C E H R S X
        bst.deleteMax();
        System.out.println(bst.keys()); // C E H R S
        System.out.println("Min: " + bst.min() + " Max: " + bst.max()); // C | S
    }
}
