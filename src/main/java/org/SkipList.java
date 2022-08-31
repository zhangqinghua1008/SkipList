package org;

import lombok.Data;

import java.util.ArrayList;
import java.util.Random;

/**
 * 跳表
 *
 * @author Zhang Qinghua
 * @Date 2022/8/30
 */
@Data
public class SkipList {

    public static int MAX_LEVEL = 8;

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
        //
        this.head = new Node(0, MAX_LEVEL);
        Node pHead = head;
        for (int i = MAX_LEVEL - 1; i >= 0; i--) {
            Node levelHead = new Node(0, i);
            pHead.down = levelHead;
            pHead = levelHead;
        }

        for (int i = 1; i < 20; i++) {
            add(i);
        }
    }

    /**
     * 增
     */
    private void add(int data) {
        Random r = new Random();
        int addLevel = r.nextInt(MAX_LEVEL); // 得到要插入的层数

        Node addLevelLocal = this.head;  // 添加的位置
        // 找到addLevel中要插入的地方
        while (addLevel < addLevelLocal.level) {
            Node levelP = addLevelLocal;
            // 在当前层找到要插入的地方: 插入到levelP之后, levelP.next 之前
            while (levelP.next != null && levelP.next.val < data) {
                levelP = levelP.next;
            }
            addLevelLocal = levelP.down;
        }

        ArrayList<Node> levelsNode = new ArrayList<>(); // 暂存每层新增的节点, 用于构建down指针
        // 将addLevel层及以下层都添加上
        for (int i = addLevel; i >= 0; i--) {
            Node addLocal = addLevelLocal;  // 第i层, 要插入的位置
            // 在当前层找到要插入的地方: 插入到addLocal之后
            while (addLocal.next != null && addLocal.next.val < data) {
                addLocal = addLocal.next;
            }
            Node newNode = new Node(data, i); // 新建节点
            newNode.next = addLocal.next;
            addLocal.next = newNode;
            levelsNode.add(newNode);
            addLevelLocal = addLevelLocal.down;
        }

        // 构建down指针
        for (int i = 0; i < levelsNode.size() - 1; i++) {
            levelsNode.get(i).down = levelsNode.get(i + 1);
        }
    }


    /**
     * 展示
     */
    void showSkipList() {
        System.out.println(" ----- 展示跳表 ----- ");
        Node levelP = this.head;
        // 按层展示
        for (int i = MAX_LEVEL; i >= 0; i--) {
            System.out.printf("第%2d 层: ", i);
            Node p = levelP;
            while (p != null) {
                System.out.printf("%2d", p.val);
                if (p.next != null)
                    System.out.print(" -> ");
                p = p.next;
            }
            System.out.println();
            levelP = levelP.down;  // 指向下一层
        }
    }

}
