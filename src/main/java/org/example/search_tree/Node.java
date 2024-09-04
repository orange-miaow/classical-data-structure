package org.example.search_tree;

import java.util.List;

/**
 * Node=<child><key><child><key>...<child><key><child>
 */
public class Node {

    private List<Long> keys;
    private Node parent;
    private List<Node> children;

    public List<Long> getKeys() {
        return keys;
    }

    public Node getParent() {
        return parent;
    }

    public List<Node> getChildren() {
        return children;
    }
}
