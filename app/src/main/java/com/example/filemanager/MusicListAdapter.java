package com.example.filemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class MusicListAdapter extends BaseAdapter {
    private File[] files;
    private Context mContext;

    MusicListAdapter(Context mContext, File[] files) {
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
        return files[position].getName();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.music_list, null);
        ImageView musicImage = (ImageView) convertView.findViewById(R.id.music_image);
        TextView musicName = (TextView) convertView.findViewById(R.id.music_name);
        TextView musicPath = (TextView) convertView.findViewById(R.id.music_path);
        if (files[position] != null) {
            musicImage.setImageResource(R.drawable.music_image);
            musicName.setText(files[position].getName());
            musicPath.setText(files[position].getParent());
        }

        return convertView;
    }

    public void changeFiles(File[] newFiles) {
        this.files = newFiles;
    }
}
