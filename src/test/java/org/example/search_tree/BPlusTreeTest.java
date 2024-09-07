package org.example.search_tree;

import org.junit.jupiter.api.Test;

import java.util.Random;

class BPlusTreeTest {

    @Test
    void insert() {
        BPlusTree tree = new BPlusTree(3);

        for (int i = 0; i < 10; i++) {
            long k = new Random().nextInt(10);
            tree.insert(k);
            System.out.println();
        }
    }
}