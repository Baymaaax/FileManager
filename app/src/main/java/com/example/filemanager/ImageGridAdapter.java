package com.example.filemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import com.bumptech.glide.Glide;

import java.io.File;

public class ImageGridAdapter extends BaseAdapter {
    private Context mContext;
    private File[] files;

    ImageGridAdapter(Context mContext, File[] files) {
        this.mContext = mContext;
        this.files = files;
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
        convertView = LayoutInflater.from(mContext).inflate(R.layout.image_grid, null);
        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        if (files[position] != null) {
            //使用Glide框架加载图片
            Glide.with(mContext)
                    .load(files[position].getAbsolutePath())
                    .override(330, 330)
                    .crossFade()
                    .fitCenter()
                    .into(image);
        }
        return convertView;
    }

    public void changeFiles(File[] newFiles) {
        this.files = newFiles;
    }
}
