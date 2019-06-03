package com.example.filemanager.tools;

import android.util.Log;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Calendar;

public class FilterByType implements FilenameFilter {
    private String[] suffix;
    private int type;
    private Calendar limitDate;

    //过滤器构造方法
    FilterByType(int type) {
        this.type = type;
        limitDate = Calendar.getInstance();
        limitDate.add(Calendar.HOUR, -3);
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
            case FileTools.LARGE_FILES:
                suffix = FileTools.largeFilesSuffix;
                break;
            case FileTools.APK:
                suffix = FileTools.apkSuffix;
                break;
            default:
                Log.e("fliter ", "未知的类型");
                break;
        }

    }

    @Override
    public boolean accept(File dir, String name) {
        boolean isAccepted = false;
        File file = new File(dir.getPath() + File.separator + name);
        Log.i("dsp", file.getPath());
        switch (type) {
            //缓存文件和临时文件具有相同的判断方法
            case FileTools.CACHE_DIR:
            case FileTools.TEMP_DIR: {
                //判断前提是目录类型
                if (file.isDirectory()) {
                    for (String str : suffix) {
                        if (name.endsWith(str)) {
                            isAccepted = true;
                        }
                    }
                }
            }
            break;
            //长时间未使用的大文件判断依据是，大于30MB，3小时未修改（为了便于测试）
            case FileTools.LARGE_FILES: {
                if (file.isFile() && file.length() > (30 * 1024 * 1024)) {
                    Calendar lastModifideDate = Calendar.getInstance();
                    lastModifideDate.setTimeInMillis(file.lastModified());
                    if (lastModifideDate.before(limitDate))
                        isAccepted = true;
                }

            }
            break;
            default: {
                if (file.isFile()) {
                    for (String str : suffix) {
                        if (name.endsWith(str)) {
                            isAccepted = true;
                        }
                    }
                }
            }
            break;
        }

        return isAccepted;
    }
}
