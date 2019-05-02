package com.example.filemanager;


import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.filemanager.tools.FileDeleter;
import com.example.filemanager.tools.FileSearcher;
import com.example.filemanager.tools.FileTools;

import java.io.File;

public class VideoActivity extends AppCompatActivity {
    private ImageButton homeButton;
    private GridView videoGrid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        homeButtonInit();
        videoGridInit();
    }

    //初始化视频显示网格
    private void videoGridInit() {
        videoGrid = (GridView) findViewById(R.id.video_grid);
        File dir = new File(Environment.getExternalStorageDirectory().toString());
        final FileSearcher fileSearcher = new FileSearcher(dir, FileTools.VIDEO);
        final File[] files = fileSearcher.search();
        final VideoGridAdapter videoGridAdapter = new VideoGridAdapter(VideoActivity.this, files);
        videoGrid.setAdapter(videoGridAdapter);
        videoGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FileTools.openVideoFile(files[position], VideoActivity.this);
            }
        });
        videoGrid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(VideoActivity.this);
                dialog.setTitle("删除");
                dialog.setMessage("是否要删除此视频文件");
                dialog.setCancelable(false);
                dialog.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (FileDeleter.deleteAll(files[position])) {
                            File[] files = fileSearcher.search();
                            VideoGridAdapter newAdapter=new VideoGridAdapter(VideoActivity.this,files);
                            videoGrid.setAdapter(newAdapter);
                            Toast.makeText(VideoActivity.this, "已删除", Toast.LENGTH_SHORT).show();
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

    //初始化home键
    private void homeButtonInit() {
        homeButton = (ImageButton) findViewById(R.id.home_button_video);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoActivity.this.finish();
            }
        });
    }
}
