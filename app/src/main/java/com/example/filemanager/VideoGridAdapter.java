package com.example.filemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.filemanager.R;

import java.io.File;

public class VideoGridAdapter extends BaseAdapter {
    private Context mContext;
    private File[] files;
    VideoGridAdapter(Context mContext, File[] files){
        this.mContext=mContext;
        this.files=files;
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
        convertView= LayoutInflater.from(mContext).inflate(R.layout.video_list,null);
        ImageView videoImage=(ImageView)convertView.findViewById(R.id.video_image);
        TextView videoName=(TextView)convertView.findViewById(R.id.video_name);
        videoImage.setImageResource(R.drawable.video);
        videoName.setText(files[position].getName());
        return convertView;
    }
}
