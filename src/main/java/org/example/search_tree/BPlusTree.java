package org.example.search_tree;

import java.util.ArrayList;
import java.util.List;

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

    // search the key
    public SearchResult search(long k) {
        if (null == root) {
            return null;
        }

        Node parent = null;
        Node node = root;

        while (true) {
            List<Long> keys = node.getKeys();
            int childPointer = keys.size() - 1 + 1;

            for (int i = 0; i < keys.size(); i++) {
                if (k < keys.get(i)) {
                    childPointer = i;
                    break;
                }
            }

            if (node instanceof LeafNode) {
                SearchResult searchResult = new SearchResult(false, parent, (LeafNode) node, childPointer);
                if (childPointer == 0) {
                    return searchResult;
                } else if (k != keys.get(childPointer - 1)) {
                    return searchResult;
                } else {
                    searchResult.setFound(true);
                    return searchResult;
                }
            }

            parent = node;
            node = node.getChildren().get(childPointer);
        }
    }

    // insert a key
    public boolean insert(long k) {
        if (null == root) {
            root = new LeafNode();
            root.setKeys(new ArrayList<Long>(){{
                add(k);
            }});

            return true;
        }

        SearchResult searchResult = search(k);
        if (searchResult.isFound()) {
            return false;
        }
        Node parent = searchResult.getParent();
        LeafNode leaf = searchResult.getLeaf();
        int index = searchResult.getIntervalIndex();
        // insert
        leaf.getKeys().add(index, k);

        if (overflow(leaf)) {
            // split
            split(parent, leaf);
            // todo perfect
        }

        return true;
    }

    private void split(Node parent, Node node) {
        // todo perfect
        List<Long> keys = node.getKeys();

        if (node instanceof LeafNode) {
            LeafNode rightNode = new LeafNode();

            int medianIndex = keys.size() / 2 + 1 - 1;
            Long median = keys.get(medianIndex);
            List<Long> keys1 = keys.subList(0, medianIndex);
            List<Long> keys2 = keys.subList(medianIndex, keys.size());

            node.setKeys(keys1);
            rightNode.setKeys(keys2);
            ((LeafNode) node).setRight(rightNode);
            rightNode.setLeft(node);

            if (null == parent) {
                parent = new Node();
                parent.setKeys(new ArrayList<Long>(){{add(median);}});
                parent.setChildren(new ArrayList<Node>(){{add(node);add(rightNode);}});

                root = parent;
            } else {

            }
        } else {

        }

    }

}
