package com.example.filemanager.tools;

public class UnitConversion {

    public static int getKB(long byteSize){
        return (int)(byteSize/1024);
    }
    public static int getMB(long byteSize){
        return getKB(byteSize)/1024;
    }
    public static int getGB(long byteSize){
        return getMB(byteSize)/1024;
    }
}
