package com.example.filemanager.tools;

/*
UnitConversion类封装了单位换算相关方法
可以将long类型的Byte换算成float类型的KB、MB、GB
 */
public class UnitConversion {

    public static float getKB(long byteSize) {
        return (float)(Math.round(byteSize / 1024*10))/10;
//        return (float) (byteSize / 1024);
    }

    public static float getMB(long byteSize) {
        return (float)(Math.round(getKB(byteSize) / 1024*10))/10;
//        return getKB(byteSize) / 1024;
    }

    public static float getGB(long byteSize) {
        return (float)(Math.round(getMB(byteSize) / 1024*10))/10;
//        return getMB(byteSize) / 1024;
    }
}
