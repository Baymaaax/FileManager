package com.example.filemanager.tools;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;

/*
FileSearcher类封装了文件搜索相关的数据和方法
 */
public class FileSearcher {
    //文件类型过滤器，搜索时进行类型判断
    private FilterByType filter;
    //根目录，搜索的入口目录
    private File rootDir;

    //public类型的构造函数，可在FileTools包外调用
    public FileSearcher(File dir, int fileType) {
        this.rootDir = dir;
        filter = new FilterByType(fileType);
    }

    //搜索方法，将DFS方法中操作的array存放到数组中
    public File[] search() {
        ArrayList<File> fileArrayList = new ArrayList<>();
        deepFirstSearch(rootDir, fileArrayList);
        File[] files = new File[fileArrayList.size()];
        for (int i = 0; i < fileArrayList.size(); i++) {
            files[i] = fileArrayList.get(i);
            Log.i("search", files[i].getName());
        }
        return files;
    }


    /*
    深度优先搜索，通过filter筛选出目标文件，将目标文件存入array
    递归调用本方法，搜索所有目录
     */
    private void deepFirstSearch(File dir, ArrayList array) {
        File[] allFiles = dir.listFiles();
        File[] targeFiles = dir.listFiles(filter);
        if (!(targeFiles == null))
            for (File f : targeFiles) {
                array.add(f);
            }
        for (File file : allFiles) {
            if (file.isDirectory()) {
                deepFirstSearch(file, array);
            }
        }
    }
}
