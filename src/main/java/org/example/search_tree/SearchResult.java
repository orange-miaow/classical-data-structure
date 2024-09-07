package org.example.search_tree;

public class SearchResult {

    private boolean found;
    private LeafNode leaf;
    private int intervalIndex;

    public SearchResult(boolean found, LeafNode leaf, int intervalIndex) {
        this.found = found;
        this.leaf = leaf;
        this.intervalIndex = intervalIndex;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public LeafNode getLeaf() {
        return leaf;
    }

    public void setLeaf(LeafNode leaf) {
        this.leaf = leaf;
    }

    public int getIntervalIndex() {
        return intervalIndex;
    }

    public void setIntervalIndex(int intervalIndex) {
        this.intervalIndex = intervalIndex;
    }
}
