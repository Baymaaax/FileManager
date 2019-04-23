package com.example.filemanager.tools;

import java.io.File;

public class FileDeleter {
    public static boolean deleteAll(File file) {
        boolean isDeleted = false;

        if (file.isFile()) {
            file.delete();
            isDeleted = true;
        } else {
            File[] files = file.listFiles();
            if (files == null) {
                file.delete();
                isDeleted = true;
            } else {
                for (int i = 0; i < files.length; i++) {
                    deleteAll(files[i]);
                }
                file.delete();
                isDeleted = true;
            }
        }
        return isDeleted;
    }

    public static boolean deleteInner(File dir) {
        boolean isDeleted=false;
        File[] files = dir.listFiles();
        if (files == null) {
            isDeleted= true;
        } else {
            for (File file : files) {
                deleteAll(file);
            }
            isDeleted= true;
        }
        return isDeleted;
    }


}
