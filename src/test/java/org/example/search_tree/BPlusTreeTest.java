package org.example.search_tree;

import org.junit.jupiter.api.Test;

import java.util.Random;

class BPlusTreeTest {

    @Test
    void insert() {
        BPlusTree tree = new BPlusTree(10);

        for (int i = 0; i < 9; i++) {
            long k = new Random().nextInt(10);
            boolean result = tree.insert(k);
            System.out.printf("insert %s\t: %s\n", k, result);
        }

        System.out.println("DONE");
    }
}