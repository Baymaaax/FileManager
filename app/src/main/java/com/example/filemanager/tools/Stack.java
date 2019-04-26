package com.example.filemanager.tools;

import java.util.LinkedList;

/*
将LinkedList封装成栈，实现压栈弹栈和判断是否空栈方法
 */
public class Stack {
    private LinkedList list = new LinkedList();

    //压栈
    public void push(Object item) {
        list.addLast(item);
    }

    //    弹栈
    public Object pop() {
        return list.removeLast();
    }

    //判空
    public boolean isEmpty() {
        return list.isEmpty();
    }
}
