package org.example.search_tree;

import java.util.List;

/**
 * Node=<child><key><child><key>...<child><key><child>
 */
public class Node {

    private Node parent;
    private List<Long> keys;
    private List<Node> children;

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Long> getKeys() {
        return keys;
    }

    public void setKeys(List<Long> keys) {
        this.keys = keys;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }
}
