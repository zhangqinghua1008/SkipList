package org;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String[] args) {
        // 测试基本功能
        // testSkipListFunction();

        // 多线程测试性能
        skipListPerformanceTest();
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

    /**
     * 测试跳表性能测试
     */
    private static void skipListPerformanceTest() {
        final SkipList skipList = new SkipList();
        int[] arrayCounts = new int[]{10000, 100000, 500000, 1000000};  //静态数组的标准分配

        // 单线程随机插入测试
        for (int arrayCount : arrayCounts) {
            oneThreadAdd(skipList, arrayCount);
            skipList.clean();
        }

        // 多线程随机插入测试
        for (int arrayCount : arrayCounts) {
            multiThreadAdd(skipList, 4, arrayCount);
            skipList.clean();
        }

        // 查询新能测试:100W数据中查询10W次
        searchTest(skipList, 1000000, 1000);
    }

    private static void oneThreadAdd(SkipList skipList, int count) {
        Date startTime = new Date();
        Random random = new Random();
        // 单线程插入
        for (int i = 1; i <= count; i++) {
            skipList.add(random.nextInt(Integer.MAX_VALUE));
        }
        System.out.printf("跳表大小：%d ; ", skipList.size());
        System.out.printf("单线程插入数量%2dW， 花费时间(秒): %.3f\n", count / 10000, (double) (new Date().getTime() - startTime.getTime()) / 1000);
    }

    // 多线程插入性能测试
    private static synchronized void multiThreadAdd(SkipList skipList, int threadNumber, int count) {
        Date startTime = new Date();

        /**
         * java同步类CountDownLatch, 允许一个或多个线程等待其他线程操作完成之后才执行
         * CountDownLatch是通过计数器的方式来实现，计数器的初始值为线程的数量。每当一个线程完成了自己的任务之后, 就会对计数器减1
         * 当计数器的值为0时，表示所有线程完成了任务，此时等待在闭锁上的线程才继续执行，从而达到等待其他线程完成任务之后才继续执行的目的。
         */
        final CountDownLatch cdl = new CountDownLatch(threadNumber);  //参数为线程个数
        int threadCount = count / threadNumber;  // 每个线程添加多少数据

        // 创建线程
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadNumber; i++) {
            threads.add(new Thread(new MyThread(skipList, threadCount, cdl)));
        }
        // 启动线程
        for (Thread thread : threads) {
            thread.start();
        }

        //线程启动后调用countDownLatch方法
        try {
            cdl.await();  //需要捕获异常，当其中线程数为0时这里才会继续运行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("跳表大小：%d ; ", skipList.size());
        System.out.printf("多线程随机插入花费时间(秒): %.3f\n", (double) (new Date().getTime() - startTime.getTime()) / 1000);
    }

    private static void searchTest(SkipList skipList, int countNum, int searchCount) {
        int nextInt = 3000000; // 随机数范围
        // 新增countNum哥数据
        addData(skipList, countNum, nextInt);

        Date startTime = new Date();
        int ans = 0;
        Random random = new Random();
        for (int i = 0; i < searchCount; i++) {
            // 查询
            if (skipList.find(random.nextInt(nextInt)) != null)
                ans++;
        }
        System.out.println("查询次数：" + searchCount + "  找到次数：" + ans);
        System.out.printf("查询时间(s): %3f", (double) (new Date().getTime() - startTime.getTime()) / 1000);
    }

    private static void addData(SkipList skipList, int count, int nextInt) {
        Date startTime = new Date();
        Random random = new Random();
        // 单线程插入
        for (int i = 1; i <= count; i++) {
            skipList.add(random.nextInt(nextInt));
        }
        System.out.printf("跳表大小：%d ; ", skipList.size());
        System.out.printf("单线程插入数量%2dW， 花费时间(秒): %.3f\n", count / 10000, (double) (new Date().getTime() - startTime.getTime()) / 1000);
    }

}