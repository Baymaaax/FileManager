package com.example.filemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.filemanager.tools.FileTools;

import java.io.File;

public class AllFilesListAdapter extends BaseAdapter {
    private File[] files;
    private Context mContext;

    AllFilesListAdapter(Context mContext, File[] files) {
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
        convertView = LayoutInflater.from(mContext).inflate(R.layout.all_files_list, null);
        ImageView itemImage = (ImageView) convertView.findViewById(R.id.all_files_list_image);
        TextView itemName = (TextView) convertView.findViewById(R.id.all_files_list_name);
        if (files[position].isDirectory()) {
            itemImage.setImageResource(R.drawable.folder);
        } else if (files[position].isFile()) {
            //判断文件类型,根据不同的类型设置不同的图片
            int fileType = FileTools.getFileType(files[position]);
            switch (fileType) {
                case FileTools.MUSIC:
                    itemImage.setImageResource(R.drawable.list_music);
                    break;
                case FileTools.VIDEO:
                    itemImage.setImageResource(R.drawable.list_video);
                    break;
                case FileTools.DOCUMENT:
                    itemImage.setImageResource(R.drawable.list_document);
                    break;
                case FileTools.IMAGE:
                    itemImage.setImageResource(R.drawable.list_image);
                    break;
                default:
                    itemImage.setImageResource(R.drawable.list_unknown_file);
                    break;
            }
        }
        itemName.setText(files[position].getName());

        return convertView;
    }

    public void changeFiles(File[] newFiles) {
        this.files = newFiles;
    }
}

