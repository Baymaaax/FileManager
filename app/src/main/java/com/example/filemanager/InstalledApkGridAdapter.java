package com.example.filemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.File;

public class InstalledApkGridAdapter extends BaseAdapter {
    private File[] files;
    private Context mContext;

    InstalledApkGridAdapter(Context mContext, File[] files) {
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
        convertView = LayoutInflater.from(mContext).inflate(R.layout.installed_apk_grid, null);
        TextView apkName = (TextView) convertView.findViewById(R.id.apk_name);
        apkName.setText(files[position].getName());
        return convertView;
    }

    public void changeFiles(File[] newFiles) {
        this.files = newFiles;
    }
}
