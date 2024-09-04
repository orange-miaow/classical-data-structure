package org.example.search_tree;

/**
 * A B+Tree is an M-way search tree with the following properties:
 * It is perfectly balanced (i.e., every leaf node is at the same depth).
 * Every inner node with k keys has k+1 non-null children.
 * Every inner node other than the root is at least half full.
 * (ceil(M/2) <= num of children <= M)
 * (ceil(M/2) − 1 <= num of keys <= M − 1)
 */
public class BPlusTree {

    // M-way
    private int M;
    // upper bound of num of keys
    private int kUpperBound;
    // lower bound of num of keys
    private int kLowerBound;
    // root node
    private Node root;

    public BPlusTree(int m) {
        this.M = m;
        this.kUpperBound = m - 1;
        this.kLowerBound = (int) (Math.ceil((double) m / 2) - 1);
    }

    // whether this node is overflow
    public boolean overflow(Node node) {
        return node.getKeys().size() > kUpperBound;
    }

    // whether this node is underflow
    public boolean underflow(Node node) {
        return node.getKeys().size() < kLowerBound;
    }

}
