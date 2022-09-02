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

    public static int MAX_LEVEL = 10;

    // 跳表头节点
    private Node head;

    private int size;

    // 初始化
    public SkipList() {
        this.head = new Node(0, MAX_LEVEL);
        this.size = 0;

        // 初始化每一层的头节点
        Node pHead = head;
        for (int i = MAX_LEVEL - 1; i >= 0; i--) {
            Node levelHead = new Node(0, i);
            pHead.down = levelHead;
            pHead = levelHead;
        }
    }

    public SkipList(int num) {
        /**
         * 从另一个构造函数调用构造函数, 必须使用关键字this从另一个构造函数调用构造函数
         * 如果构造函数调用另一个构造函数：必须是构造函数体中的第一个可执行语句
         */
        this();

        for (int i = 1; i < num; i++) {
            add(i);
        }
        this.size = num;
    }

    /**
     * skipList的大小
     */
    public int size() {
        return this.size;
    }

    /**
     * 增
     */
    public void add(int data) {
        int addLevel = getRandomLevel();  // 抛硬币算法 获得层数

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

            // 新建节点
            Node newNode = new Node(data, i);
            newNode.next = addLocal.next;
            addLocal.next = newNode;
            levelsNode.add(newNode);

            addLevelLocal = addLevelLocal.down;
        }

        // 构建down指针
        for (int i = 0; i < levelsNode.size() - 1; i++) {
            levelsNode.get(i).down = levelsNode.get(i + 1);
        }

        this.size++;
    }

    // 抛硬币算法 获得层数
    private int getRandomLevel() {
        Random r = new Random();
        int level = 0;
        while (r.nextInt(2) == 1) {
            level++;
        }
        return Math.min(level, MAX_LEVEL);
    }

    /**
     * 查找
     * 找到元素 val 第一个节点（最高层）
     */
    public Node find(int data) {
        Node preNode = findPrevious(data);
        if (preNode != null)
            return preNode.next;
        return null;
    }

    /**
     * 查找
     * 找到元素 val 前一个节点 即 最高层第一次出现的同一行的前一个元素
     */
    private Node findPrevious(int data) {
        Node tempP = this.head;
        // 索引层查找
        while (tempP.level > 0) {
            // 当前层结束 或 下一个节点值大于目标值, 到下一索引层擦护照
            if (tempP.next == null || tempP.next.val > data) {
                tempP = tempP.down;
            } else if (tempP.next.val == data) {
                return tempP;
            } else {
                tempP = tempP.next;
            }
        }

        // 最底层
        while (tempP.next != null) {
            if (tempP.next.val == data) {
                return tempP;
            }
            tempP = tempP.next;
        }

        return null;
    }

    /**
     * 删除节点
     */
    public void remove(int data) {
        Node levelPreNode = findPrevious(data);  // 找到数据所在最高层的前一个节点
        if (levelPreNode == null) {
            System.out.println("跳表中没有该数据");
            return;
        }

        // 删除当前层的节点
        levelPreNode.next = levelPreNode.next.next;
        while (levelPreNode.level > 0) {
            // 找到下一层的前一个节点
            Node nextLevelPreNode = levelPreNode.down;
            while (nextLevelPreNode.next != null && nextLevelPreNode.next.val < data) {
                nextLevelPreNode = nextLevelPreNode.next;
            }
            levelPreNode = nextLevelPreNode;
            levelPreNode.next = levelPreNode.next.next;
        }
        this.size--;
    }

    /**
     * 清空跳表
     */
    public void clean() {
        this.size = 0;
        Node node = this.head;
        // 逐层删除
        for (int i = MAX_LEVEL; i >= 0; i--) {
            node.next = null;
            node = node.down;
        }
    }

    /**
     * 展示
     */
    public void showSkipList() {
        Node levelP = this.head;
        // 按层展示
        for (int i = MAX_LEVEL; i >= 0; i--) {
            System.out.printf("第%2d 层: ", i);
            Node p = levelP;
            while (p != null) {
                System.out.printf("%2d", p.val);
                if (p.next != null) {
                    System.out.print(" -> ");
                }
                p = p.next;
            }
            System.out.println();
            levelP = levelP.down;  // 指向下一层
        }
    }

}
