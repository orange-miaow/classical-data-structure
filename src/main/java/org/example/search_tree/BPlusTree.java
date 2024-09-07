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

    public BPlusTree(int M) {
        if (M < 3) {
            throw new RuntimeException("M is at least 3.");
        }

        this.M = M;
        this.kUpperBound = M - 1;
        this.kLowerBound = (int) (Math.ceil((double) M / 2) - 1);

        System.out.println("----------.----------.----------.----------.----------.----------.----------");
        System.out.printf("initialize a B+ tree which is an %s-way search tree\n", M);
        System.out.printf("number of children (of every inner node other than the root): [%s, %s]\n", kLowerBound + 1, kUpperBound + 1);
        System.out.printf("number of keys (of every node other than the root): [%s, %s]\n", kLowerBound, kUpperBound);
        System.out.println("----------.----------.----------.----------.----------.----------.----------");
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

        Node node = root;

        while (true) {
            List<Long> keys = node.getKeys();
            int childPointer = intervalIndex(keys, k);

            if (node instanceof LeafNode) {
                SearchResult searchResult = new SearchResult(false, (LeafNode) node, childPointer);
                if (childPointer == 0) {
                    return searchResult;
                } else if (k != keys.get(childPointer - 1)) {
                    return searchResult;
                } else {
                    searchResult.setFound(true);
                    return searchResult;
                }
            }

            node = node.getChildren().get(childPointer);
        }
    }

    // determine the index of k in the intervals of keys
    private int intervalIndex(List<Long> keys, long k) {
        int intervalIndex = keys.size() - 1 + 1;

        for (int i = 0; i < keys.size(); i++) {
            if (k < keys.get(i)) {
                intervalIndex = i;
                break;
            }
        }

        return intervalIndex;
    }

    // insert a key
    public boolean insert(long k) {
        System.out.printf("[INSERT] k: %s\n", k);
        if (null == root) {
            root = new LeafNode();
            root.setKeys(new ArrayList<Long>() {{
                add(k);
            }});

            System.out.printf("[INSERTED] leaf: %s\n", root.getKeys().toString());
            System.out.println("[DONE]");
            return true;
        }

        SearchResult searchResult = search(k);
        if (searchResult.isFound()) {
            System.out.println("[REFUSED]");
            return false;
        }
        LeafNode leaf = searchResult.getLeaf();
        int index = searchResult.getIntervalIndex();
        // insert
        leaf.getKeys().add(index, k);
        System.out.printf("[INSERTED] leaf: %s\n", leaf.getKeys().toString());

        // split and rebalance when the leaf is overflow
        splitAndRebalanceWhenOverflow(leaf);

        System.out.println("[DONE]");
        return true;
    }

    // split and rebalance when the node is overflow
    private void splitAndRebalanceWhenOverflow(Node node) {
        if (!overflow(node)) {
            return;
        }
        System.out.printf("[OVERFLOW] node: %s\n", node.getKeys().toString());

        Node rightNode;
        if (node instanceof LeafNode) {
            rightNode = new LeafNode();

            ((LeafNode) node).setRight(rightNode);
            ((LeafNode) rightNode).setLeft(node);
        } else {
            rightNode = new Node();
        }

        int medianIndex = node.getKeys().size() / 2 + 1 - 1;
        Long median = node.getKeys().get(medianIndex);
        List<Long> keys1 = new ArrayList<>(node.getKeys().subList(0, medianIndex));
        List<Long> keys2 = new ArrayList<>(node.getKeys().subList((node instanceof LeafNode) ? medianIndex : (medianIndex + 1), node.getKeys().size()));
        System.out.printf("[SPLIT] left keys: %s\n", keys1);
        System.out.printf("[SPLIT] right keys: %s\n", keys2);

        node.setKeys(keys1);
        rightNode.setKeys(keys2);

        if (!(node instanceof LeafNode)) {
            List<Node> children1 = new ArrayList<>(node.getChildren().subList(0, medianIndex + 1));
            List<Node> children2 = new ArrayList<>(node.getChildren().subList(medianIndex + 1, node.getChildren().size()));

            node.setChildren(children1);
            rightNode.setChildren(children2);
        }

        Node parent = node.getParent();
        if (null == parent) {
            parent = new Node();
            parent.setKeys(new ArrayList<Long>() {{
                add(median);
            }});
            parent.setChildren(new ArrayList<Node>() {{
                add(node);
                add(rightNode);
            }});

            node.setParent(parent);
            rightNode.setParent(parent);

            root = parent;
            System.out.printf("[NEW-ROOT] root: %s\n", root.getKeys().toString());
        } else {
            int index = intervalIndex(parent.getKeys(), median);
            parent.getKeys().add(index, median);
            parent.getChildren().add(index + 1, rightNode);

            rightNode.setParent(parent);
            System.out.printf("[INSERTED-MEDIAN-INTO-PARENT] parent: %s\n", parent.getKeys().toString());

            // split and rebalance when the parent is overflow
            splitAndRebalanceWhenOverflow(parent);
        }
    }

}
