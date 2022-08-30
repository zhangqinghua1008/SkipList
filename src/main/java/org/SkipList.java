package org;

import lombok.Data;

/**
 * 跳表
 *
 * @author Zhang Qinghua
 * @Date 2022/8/30
 */
@Data
public class SkipList {

    private Node head;

    // 节点内部类
    private class Node {
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

    // 初始化
    public SkipList() {
        this.head = new Node(0, 0);
        Node p = this.head;
        for (int i = 1; i < 5; i++) {
            p.next = new Node(i, 0);
            p = p.next;
        }
    }

    /**
     * 增
     */


    /**
     * 删
     */

    /**
     * 改
     */

    /**
     * 查
     */


    /**
     * 展示
     */
    void showSkipList() {
        System.out.println("展示跳表：");
        Node p = this.head;
        while (p.next != null) {
            System.out.println(p.val);
            p = p.next;
        }
    }

}
