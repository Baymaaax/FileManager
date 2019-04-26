package com.example.filemanager.tools;

/*
UnitConversion类封装了单位换算相关方法
可以将long类型的Byte换算成int类型的KB、MB、GB
 */
public class UnitConversion {

    public static int getKB(long byteSize) {
        return (int) (byteSize / 1024);
    }

    public static int getMB(long byteSize) {
        return getKB(byteSize) / 1024;
    }

    public static int getGB(long byteSize) {
        return getMB(byteSize) / 1024;
    }
}
