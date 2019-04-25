package com.example.filemanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.filemanager.tools.FileDeleter;
import com.example.filemanager.tools.FileSearcher;
import com.example.filemanager.tools.FileTpye;

import java.io.File;

public class ImageActivity extends AppCompatActivity {
    private ImageButton homeButton;
    private GridView imageGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        homeButton=(ImageButton) findViewById(R.id.home_button_apk);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent =new Intent(ImageActivity.this, MainActivity.class);
//                startActivity(intent);
                ImageActivity.this.finish();

            }
        });
        imageGrid=(GridView)findViewById(R.id.image_grid);
        File dir=new File(Environment.getExternalStorageDirectory().toString());
        FileSearcher fileSearcher=new FileSearcher(dir, FileTpye.IMAGE);
        final File[] files=fileSearcher.search();
        final ImageGridAdapter imageGridAdapter=new ImageGridAdapter(ImageActivity.this,files);
        imageGrid.setAdapter(imageGridAdapter);
        imageGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(files[position]),"image/*");
                startActivity(intent);
            }
        });
        imageGrid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder dialog=new AlertDialog.Builder(ImageActivity.this);
                dialog.setTitle("删除");
                dialog.setMessage("是否要删除此图片");
                dialog.setCancelable(false);
                dialog.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("delete", files[position].getPath());

                        if(FileDeleter.deleteAll(files[position])){

                            imageGridAdapter.notifyDataSetChanged();
                            Toast.makeText(ImageActivity.this, "已删除", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                return true;
            }
        });
    }

}
