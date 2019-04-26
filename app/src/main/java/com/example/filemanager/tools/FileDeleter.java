package com.example.filemanager.tools;

import java.io.File;

//FileDeleter类封装了与删除有关的静态方法，可通过类名直接调用
public class FileDeleter {
    /*
    全部删除方法
    如果file是文件，直接删除该文件
    如果file是目录，判断目录是否为空
    空目录直接删除，非空目录递归调用本方法
     */
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

    /*
    清除内部文件方法
    dir是目录时，判断dir是否为空
    dir内部为空时不用删除
    dir内部非空时对内部每一个file调用deleteAll()方法
     */
    public static boolean deleteInner(File dir) {
        boolean isDeleted = false;
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files == null) {
                isDeleted = true;
            } else {
                for (File file : files) {
                    deleteAll(file);
                }
                isDeleted = true;
            }
        }
        return isDeleted;
    }


}
