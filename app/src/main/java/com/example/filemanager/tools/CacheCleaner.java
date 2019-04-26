package com.example.filemanager.tools;

import android.os.Environment;
import android.util.Log;

import java.io.File;

//CacheCleaner类封装了清除缓存可能用到的相关方法，以及缓存大小和/data的路径
public class CacheCleaner {
    //被清除的缓存大小，初始为0，在调用clean方法后为实际清除缓存的大小
    private long cleanedCacheSize;
    //存放/data路径，cache文件夹搜索的入口
    private String dataPath;

    //public 构造方法，可在tools包外使用。
    public CacheCleaner() {
        cleanedCacheSize = 0;
        dataPath = Environment.getExternalStorageDirectory().toString() + "/Android/data";
    }

    //获取入口/data的路径
    public String getDataPath() {
        return dataPath;
    }

    //获取被清理的缓存大小
    public long getCleanedCacheSize() {
        return cleanedCacheSize;
    }

    /*
    清理缓存方法
    搜索/data下的各个包，有cache文件夹的话压栈
    搜索完毕后，将栈内cache文件弹出，并调用deleteInner()方法删除内部缓存文件
    同时统计缓存大小
     */
    public void clean() {
        File dataFile = new File(dataPath);
        File[] packages = dataFile.listFiles();
        Stack cacheStack = new Stack();
        for (File file : packages) {
            File[] packageInnerDir = file.listFiles();
            if (!(packageInnerDir == null)) {
                for (File f : packageInnerDir) {
                    if (f.getName().equals("cache")) {
                        cacheStack.push(f);
                    }
                }
            }
        }
        while (!cacheStack.isEmpty()) {
            File cacheDir = (File) cacheStack.pop();
            Log.i("/cleaner", cacheDir.getAbsolutePath());
            cleanedCacheSize += FileTools.getTotalSize(cacheDir);
            FileDeleter.deleteInner(cacheDir);
        }

    }

}
