package com.example.filemanager.tools;

import android.util.Log;

import java.io.File;
import java.io.FilenameFilter;

public class FilterByType implements FilenameFilter {
    private String[] Suffix;



//过滤器
    FilterByType(int type) {
        switch (type) {
            case FileTpye.MUSIC:
                Suffix = FileTpye.musicSuffix;
                break;
            case FileTpye.VIDEO:
                Suffix = FileTpye.videoSuffix;
                break;
            case FileTpye.DOCUMENT:
                Suffix = FileTpye.documentSuffix;
                break;
            case FileTpye.IMAGE:
                Suffix = FileTpye.imageSuffix;
                break;
            default:
                Log.e("fliter ","未知的类型");
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
