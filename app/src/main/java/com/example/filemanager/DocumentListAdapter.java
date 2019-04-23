package com.example.filemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.filemanager.R;

import java.io.File;

public class DocumentListAdapter extends BaseAdapter {
    private File[] files;
    private Context mContext;
    DocumentListAdapter(Context mContext,File[] files){
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
        convertView=LayoutInflater.from(mContext).inflate(R.layout.document_list,null);
        ImageView documentImage=(ImageView)convertView.findViewById(R.id.document_image);
        TextView documentName=(TextView)convertView.findViewById(R.id.document_name);
        TextView documentPath=(TextView)convertView.findViewById(R.id.document_path);
        documentImage.setImageResource(R.drawable.document);
        documentName.setText(files[position].getName());
        documentPath.setText(files[position].getParent());
        return convertView;
    }
}
