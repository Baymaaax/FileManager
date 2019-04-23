package com.example.filemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
            itemImage.setImageResource(R.drawable.file);
        }
        itemName.setText(files[position].getName());

        return convertView;
    }
}

