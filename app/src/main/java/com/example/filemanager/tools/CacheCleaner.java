package com.example.filemanager.tools;

import android.os.Environment;
import android.util.Log;

import java.io.File;

public class CacheCleaner {
    private long cleanedCacheSize;
    private String dataPath;
    public CacheCleaner(){
        cleanedCacheSize=0;
        dataPath=Environment.getExternalStorageDirectory().toString() + "/Android/data";
    }

    public String getDataPath() {
        return dataPath;
    }

    public long getCleanedCacheSize() {
        return cleanedCacheSize;
    }

    private long getTotalSize(File dir) {
        long totalSize = 0;
        if (dir.isFile()) {
            totalSize = dir.length();
            return dir.length();
        } else if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            if (children != null) {
                for (File child : children)
                    totalSize += getTotalSize(child);
            }
        }
        return totalSize;
    }
    public void clean(){
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
            cleanedCacheSize += getTotalSize(cacheDir);
            FileDeleter.deleteInner(cacheDir);
        }

    }

}
