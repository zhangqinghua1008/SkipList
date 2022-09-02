package org;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * 多线程类
 *
 * 实现多线程第一种方式: 编写一个类，直接继承 Thread累, 重写 run 方法
 * 第二种方式:  实现 Runnable接口, 实现 run 方法
 */

public class MyThread implements Runnable {

    private SkipList skipList;

    private int count;

    private CountDownLatch cdl;

    public MyThread(SkipList skipList, int count, CountDownLatch cdl) {
        this.skipList = skipList;
        this.count = count;
        this.cdl = cdl;
    }

    @Override
    public void run() {
        Random random = new Random();

        // 多线程插入
        for (int i = 1; i <= this.count; i++) {
            int num = random.nextInt(Integer.MAX_VALUE);
            synchronized (this.skipList) {
                this.skipList.add(num);
            }
        }
        cdl.countDown();  //此方法是让CountDownLatch的线程数-1
    }
}
