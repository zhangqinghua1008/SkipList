package org;

public class Main {
    public static void main(String[] args) {
        testSkipListFunction();
    }

    /**
     * 测试跳表的基本功能
     */
    private static void testSkipListFunction() {
        System.out.println(" --------- Skip List --------- ");

        SkipList skipList = new SkipList(1000);

        System.out.println("\n ---- 查找功能 ---- ");
        int searchData = 10;
        Node node = skipList.find(searchData);
        if (node != null) {
            System.out.printf("节点%d在跳表中第%d层\n", searchData, node.level);
        } else {
            System.out.printf("节点%d在跳表中不存在%n\n", searchData);
        }

        System.out.println("\n ---- 删除功能 ---- ");
        System.out.println(" ---- 删除前 ---- ");
        skipList.showSkipList();
        System.out.println("跳表大小：" + skipList.size());
        skipList.remove(searchData);
        System.out.println("\n需要删除的数据：" + searchData);
        System.out.println(" ---- 删除后 ---- ");
        skipList.showSkipList();
        System.out.println("跳表大小：" + skipList.size());

        System.out.println("\n ---- 清理功能 ---- ");
        skipList.clean();
        skipList.showSkipList();
        System.out.println("跳表大小：" + skipList.size());
    }
}