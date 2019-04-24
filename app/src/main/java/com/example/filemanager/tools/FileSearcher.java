package com.example.filemanager.tools;

import java.io.File;
import java.util.ArrayList;

public class FileSearcher {
    private FilterByType filter;
    private File rootDir;

    public FileSearcher(File dir, int fileType) {
        this.rootDir = dir;
        filter = new FilterByType(fileType);
    }


    public File[] search() {
        ArrayList<File> fileArrayList = new ArrayList<File>();
        deepFirstSearch(rootDir, fileArrayList);
        File[] files=new File[fileArrayList.size()];
        for(int i=0;i<fileArrayList.size();i++){
            files[i]=fileArrayList.get(i);
        }
        return files;
    }

    private void deepFirstSearch(File dir, ArrayList array) {
        File[] allFiles = dir.listFiles();
        File[] targeFiles = dir.listFiles(filter);
        if (!(targeFiles==null))
            for (File f : targeFiles){
                array.add(f);
            }
        for(File file:allFiles){
            if(file.isDirectory()){
                deepFirstSearch(file,array);
            }
        }

    }


}
