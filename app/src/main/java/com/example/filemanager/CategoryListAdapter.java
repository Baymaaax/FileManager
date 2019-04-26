package com.example.filemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoryListAdapter extends BaseAdapter {
    private  int[] categoryPicture;
    private  String[] categoryName;
    private Context mContext;
    private String[] itemSize;

    CategoryListAdapter(Context mContext, int[] categoryPicture, String[] categoryName,String[] size) {
        super();
        this.mContext = mContext;
        this.categoryPicture = categoryPicture;
        this.categoryName = categoryName;
        this.itemSize=size;

    }

    @Override
    public int getCount() {
        return categoryName.length;
    }

    @Override
    public Object getItem(int position) {
        return categoryName[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.catagory_list, null);
        ImageView picture = (ImageView) convertView.findViewById(R.id.category_picture);
        TextView name = (TextView) convertView.findViewById(R.id.category_name);
        TextView size=(TextView)convertView.findViewById(R.id.item_total_size);
        picture.setImageResource(categoryPicture[position]);
        name.setText(categoryName[position]);
        size.setText(itemSize[position]);

        return convertView;
    }
}
