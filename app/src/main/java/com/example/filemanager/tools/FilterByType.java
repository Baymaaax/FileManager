package com.example.filemanager.tools;

import android.util.Log;

import java.io.File;
import java.io.FilenameFilter;

public class FilterByType implements FilenameFilter {
    private String[] Suffix;

    //过滤器构造方法
    FilterByType(int type) {
        switch (type) {
            case FileTools.MUSIC:
                Suffix = FileTools.musicSuffix;
                break;
            case FileTools.VIDEO:
                Suffix = FileTools.videoSuffix;
                break;
            case FileTools.DOCUMENT:
                Suffix = FileTools.documentSuffix;
                break;
            case FileTools.IMAGE:
                Suffix = FileTools.imageSuffix;
                break;
            default:
                Log.e("fliter ", "未知的类型");
                break;
        }

    }

    @Override
    public boolean accept(File dir, String name) {
        for (String str : Suffix) {
            if (name.endsWith(str)) {
                return true;
            }
        }
        return false;
    }
}
