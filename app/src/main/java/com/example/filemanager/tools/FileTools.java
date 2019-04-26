package com.example.filemanager.tools;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import java.io.File;

/*
FileTools类封装了文件相关操作以及文件类型和后缀名
 */
public class FileTools {
    //文件类型
    public final static int MUSIC = 0;//音乐
    public final static int VIDEO = 1;//视频
    public final static int DOCUMENT = 2;//文档
    public final static int IMAGE = 3;//图片
    public final static int ALL_FILES = 4;//所有文件
    //文件后缀名
    public final static String[] musicSuffix = {".mp3", ".wma", ".ogg"};//音频文件后缀名
    public final static String[] videoSuffix = {".mp4"};//视频文件后缀名
    public final static String[] documentSuffix = {".txt", ".doc"};//文本文件后缀名
    public final static String[] imageSuffix = {".jpg", ".png"};//图片文件后缀名

    //获取文件类型方法，通过文件名是否与后缀名字符串相同判断。
    public static int getFileType(File file) {
        for (String suffix : FileTools.musicSuffix) {
            if (file.getName().endsWith(suffix))
                return FileTools.MUSIC;
        }
        for (String suffix : FileTools.videoSuffix) {
            if (file.getName().endsWith(suffix))
                return FileTools.VIDEO;
        }
        for (String suffix : FileTools.documentSuffix) {
            if (file.getName().endsWith(suffix))
                return FileTools.DOCUMENT;
        }
        for (String suffix : FileTools.imageSuffix) {
            if (file.getName().endsWith(suffix))
                return FileTools.IMAGE;
        }
        //未知类型返回-1
        return -1;
    }

    //打开文件，判断文件类型，根据文件类型调用不同的打开方法
    public static void openFile(File file, Context mContext) {
        int fileType = getFileType(file);
        switch (fileType) {
            case MUSIC:
                openMusicFile(file, mContext);
                break;
            case VIDEO:
                openVideoFile(file, mContext);
                break;
            case DOCUMENT:
                openDocumentFile(file, mContext);
                break;
            case IMAGE:
                openImageFile(file, mContext);
                break;
            default:
                Toast.makeText(mContext, "未知的文件类型", Toast.LENGTH_SHORT);
                break;

        }

    }

    //打开图片文件
    public static void openImageFile(File file, Context mContext) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "image/*");
        mContext.startActivity(intent);
    }

    //打开文本文件
    public static void openDocumentFile(File file, Context mContext) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "text/plain");
        mContext.startActivity(intent);
    }

    //打开视频文件
    public static void openVideoFile(File file, Context mContext) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "video/*");
        mContext.startActivity(intent);
    }

    //打开音频文件
    public static void openMusicFile(File file, Context mContext) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "audio/*");
        mContext.startActivity(intent);
    }

}
