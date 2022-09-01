package org;

/**
 * 节点类
 */
public class Node {
    int val;  // 存放的数据

    int level;

    Node next; // 下一个节点

    Node down; // 下层节点

    public Node() {
    }

    public Node(int val, int level) {
        this.val = val;
        this.level = level;
    }
}
