package com.example.filemanager;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.filemanager.tools.FileTools;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LargeFileListAdapter extends BaseAdapter {
    private File[] files;
    private Context mContext;

    LargeFileListAdapter(Context mContext, File[] files) {
        super();
        this.files = files;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return files.length;
    }

    @Override
    public Object getItem(int position) {
        return files[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.large_files_list, null);
        ImageView fileImage = (ImageView) convertView.findViewById(R.id.large_files_image);
        TextView fileName = (TextView) convertView.findViewById(R.id.large_files_name);
        TextView filePath=(TextView)convertView.findViewById(R.id.large_files_path);
        TextView fileDate=(TextView)convertView.findViewById(R.id.large_files_date);
        int fileType = FileTools.getFileType(files[position]);
        switch (fileType) {
            case FileTools.MUSIC:
                fileImage.setImageResource(R.drawable.music_image);
                break;
            case FileTools.VIDEO:
                fileImage.setImageResource(R.drawable.video);
                break;
            case FileTools.DOCUMENT:
                fileImage.setImageResource(R.drawable.document);
                break;
            case FileTools.IMAGE:
                fileImage.setImageResource(R.drawable.list_image);
                break;
            case FileTools.APK:
                fileImage.setImageResource(R.drawable.apk);
                break;
            default:
                fileImage.setImageResource(R.drawable.list_unknown_file);
                break;
        }
        fileName.setText(files[position].getName());
        filePath.setText(files[position].getPath());
        fileDate.setText("最后修改时间："+getData(files[position]));

        return convertView;
    }
    public void changeFiles(File[] newFiles) {
        this.files = newFiles;
    }

    private String getData(File file) {
        SimpleDateFormat formater=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar date=Calendar.getInstance();
        date.setTimeInMillis(file.lastModified());
        return formater.format(date.getTime());
    }
}
