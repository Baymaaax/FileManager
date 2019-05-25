package com.example.filemanager.tools;

import android.os.Environment;
import android.util.Log;

import java.io.File;

public class Cleaner {
    private static final String dataPath = Environment.getExternalStorageDirectory()
            .toString() + "/Android/data";
    private static final String rootPath = Environment.getExternalStorageDirectory()
            .toString();
    public static long getCacheSize(){
        long cacheSize;
        FileSearcher cacheSearcher = new FileSearcher(new File(dataPath), FileTools.CACHE_DIR);
        File[] cacheDirs = cacheSearcher.search();
        cacheSize = FileTools.getTotalSize(cacheDirs);
        return cacheSize;
    }
    public static long getTempSize(){
        long tempSize ;
        FileSearcher tempSearcher = new FileSearcher(new File(rootPath), FileTools.TEMP_DIR);
        File[] tempDirs = tempSearcher.search();
        tempSize = FileTools.getTotalSize(tempDirs);
        return tempSize;
    }
    public static long getLogSize(){
        long logSize;
        FileSearcher logSearcher=new FileSearcher(new File(rootPath),FileTools.LOG_FILES);
        File[] logFiles=logSearcher.search();
        logSize=FileTools.getTotalSize(logFiles);
        return logSize;
    }

    public static long cleanCache() {
        long cleanedCacheSize = 0;
        FileSearcher cacheSearcher = new FileSearcher(new File(dataPath), FileTools.CACHE_DIR);
        File[] cacheDirs = cacheSearcher.search();
        cleanedCacheSize = FileTools.getTotalSize(cacheDirs);
        for (File dir : cacheDirs) {
            Log.i("CleanCache", "path:" + dir.getPath());
            FileDeleter.deleteInner(dir);
        }
        return cleanedCacheSize;
    }

    public static long cleanTemp() {
        long cleanedTempSize = 0;
        FileSearcher tempSearcher = new FileSearcher(new File(rootPath), FileTools.TEMP_DIR);
        File[] tempDirs = tempSearcher.search();
        cleanedTempSize = FileTools.getTotalSize(tempDirs);
        for (File dir : tempDirs) {
            Log.i("CleanTemp", "path:" + dir.getPath());
            FileDeleter.deleteInner(dir);
        }
        Log.i("CleanTemp", "Total Size" +cleanedTempSize);
        return cleanedTempSize;
    }
    public static long cleanLog(){
        long cleanedLogSize=0;
        FileSearcher logSearcher=new FileSearcher(new File(rootPath),FileTools.LOG_FILES);
        File[] logFiles=logSearcher.search();
        cleanedLogSize=FileTools.getTotalSize(logFiles);
        for(File file:logFiles){
            Log.i("CleanLog", "path:" + file.getPath());
            FileDeleter.deleteAll(file);
        }
        Log.i("CleanLog", "Total Size:" + cleanedLogSize);
        return cleanedLogSize;
    }

}
