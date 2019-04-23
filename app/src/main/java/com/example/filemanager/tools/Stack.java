package com.example.filemanager.tools;

import java.util.LinkedList;

public class Stack {
    private LinkedList list =new LinkedList();
    public void push(Object item){
        list.addLast(item);
    }
    public Object pop(){
        return list.removeLast();
    }
    public boolean isEmpty(){
        return list.isEmpty();
    }
}
