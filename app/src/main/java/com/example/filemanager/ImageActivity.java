package com.example.filemanager;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.filemanager.tools.FileTools;

import java.io.File;

public class ImageActivity extends AppCompatActivity {
    private ImageButton homeButton;
    private GridView imageGrid;
    private FileSearcher fileSearcher;
    private File[] allImageFile;
    private ImageGridAdapter imageGridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        homeButtonInit();
        imageGridInit();

    }

    //图片显示网格初始化
    private void imageGridInit() {
        imageGrid = (GridView) findViewById(R.id.image_grid);
        File dir = new File(Environment.getExternalStorageDirectory().toString());
        fileSearcher = new FileSearcher(dir, FileTools.IMAGE);
        allImageFile = fileSearcher.search();
        imageGridAdapter = new ImageGridAdapter(ImageActivity.this, allImageFile);
        imageGrid.setAdapter(imageGridAdapter);
        imageGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FileTools.openFile(allImageFile[position], ImageActivity.this);
            }
        });
        imageGrid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(ImageActivity.this);
                dialog.setTitle("删除");
                dialog.setMessage("是否要删除此图片");
                dialog.setCancelable(false);
                dialog.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("delete", allImageFile[position].getPath());
                        if (FileDeleter.deleteAll(allImageFile[position])) {
                            allImageFile = fileSearcher.search();
                            imageGridAdapter.changeFiles(allImageFile);
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

    //home键初始化
    private void homeButtonInit() {
        homeButton = (ImageButton) findViewById(R.id.home_button_apk);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImageActivity.this, MainActivity.class);
                startActivity(intent);
                ImageActivity.this.finish();
            }
        });
    }

}
