package TestLinkedList;

import org.SkipList;

import java.util.LinkedList;

/**
 * 测试Java 链表功能
 */

public class Main {
    public static void main(String[] args) {
        System.out.println(" --------- 测试 --------- ");

        LinkedList<String> linkedList = new LinkedList<>();

        linkedList.add("1");
        linkedList.add("2");
        linkedList.add("3");
        System.out.println("添加完后："+linkedList);


        linkedList.remove("3");
        System.out.println("remove完后："+linkedList);

        String nodeVal = linkedList.get(1);

    }
}