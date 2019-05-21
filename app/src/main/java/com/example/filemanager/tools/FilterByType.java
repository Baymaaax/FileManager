package com.example.filemanager.tools;

import android.util.Log;

import java.io.File;
import java.io.FilenameFilter;

public class FilterByType implements FilenameFilter {
    private String[] suffix;
    private int type;

    //过滤器构造方法
    FilterByType(int type) {
        this.type = type;
        switch (type) {
            case FileTools.MUSIC:
                suffix = FileTools.musicSuffix;
                break;
            case FileTools.VIDEO:
                suffix = FileTools.videoSuffix;
                break;
            case FileTools.DOCUMENT:
                suffix = FileTools.documentSuffix;
                break;
            case FileTools.IMAGE:
                suffix = FileTools.imageSuffix;
                break;
            case FileTools.CACHE_DIR:
                suffix = FileTools.cacheDirName;
                break;
            case FileTools.TEMP_DIR:
                suffix = FileTools.tempDirName;
                break;
            case FileTools.LOG_FILES:
                suffix = FileTools.logSuffix;
                break;
            default:
                Log.e("fliter ", "未知的类型");
                break;
        }

    }

    @Override
    public boolean accept(File dir, String name) {
        boolean isAccepted = false;
        if (type == FileTools.CACHE_DIR || type == FileTools.TEMP_DIR) {
            File file = new File(dir.getPath() + File.separator + name);
            if (file.isDirectory()) {
                for (String str : suffix) {
                    if (name.endsWith(str)) {
                        isAccepted = true;
                    }
                }
            }
        } else {
            for (String str : suffix) {
                if (name.endsWith(str)) {
                    isAccepted = true;
                }
            }
        }
        return isAccepted;
    }
}
