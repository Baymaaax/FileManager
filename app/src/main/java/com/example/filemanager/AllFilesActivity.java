package com.example.filemanager;


import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.filemanager.tools.FileDeleter;
import com.example.filemanager.tools.FileTools;
import com.example.filemanager.tools.Stack;

import java.io.File;
import java.util.ArrayList;

public class AllFilesActivity extends AppCompatActivity {
    private ImageButton homeButton;
    private ImageButton goBackButton;
    private ListView allFilesList;
    private File dir;
    private Stack parentDir;
    private AllFilesListAdapter allFilesListAdapter;
    private File[] files;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_files);
        homeButtonInit();
        //dir初始化为外部存储根目录
        dir = new File(Environment.getExternalStorageDirectory().toString());

        allFilesList = (ListView) findViewById(R.id.all_files_list);
        //初始化栈存放父目录地址
        parentDir = new Stack();
        FilesListInit(dir);
        gobackButtonInit();


    }

    //初始化Home键，添加点击事件返回MainActivity
    private void homeButtonInit() {
        homeButton = (ImageButton) findViewById(R.id.home_button_all_files);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllFilesActivity.this.finish();
            }
        });

    }

    //初始化返回键，如果父目录栈非空，弹栈，实现返回上一级目录。
    private void gobackButtonInit() {
        goBackButton = (ImageButton) findViewById(R.id.go_back_button);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (parentDir.isEmpty()) {
                    Toast.makeText(AllFilesActivity.this, "已经没有上一级目录了", Toast.LENGTH_SHORT).show();
                } else {
                    FilesListInit((File) parentDir.pop());
                }
            }
        });
    }

    //初始化文件列表
    private void FilesListInit(final File dir) {

//        final File[] tempFiles = dir.listFiles();
        files = dirInOrder(dir.listFiles());
        allFilesListAdapter = new AllFilesListAdapter(AllFilesActivity.this, files);
        allFilesList.setAdapter(allFilesListAdapter);
        allFilesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //添加点击事件，如果是文件则弹出
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (files[position].isDirectory()) {
                    parentDir.push(dir);
                    FilesListInit(files[position]);
                } else {
                    FileTools.openFile(files[position], AllFilesActivity.this);
                }
            }
        });
        //添加长按点击事件实现删除功能，弹出dialog判断是否删除，
        allFilesList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(AllFilesActivity.this);
                dialog.setTitle("删除");
                if (files[position].isDirectory()) {
                    dialog.setMessage("是否要删除此文件夹");
                } else {
                    dialog.setMessage("是否要删除此文件");
                }
                dialog.setCancelable(false);
                dialog.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        File parentFile = files[position].getParentFile();
                        if (FileDeleter.deleteAll(files[position])) {
                            files = dirInOrder(parentFile.listFiles());
                            allFilesListAdapter.changeFiles(files);
                            allFilesListAdapter.notifyDataSetChanged();
                            Toast.makeText(AllFilesActivity.this, "已删除", Toast.LENGTH_SHORT).show();
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

    //实现文件列表分类，文件夹在数组前面，文件在后面
    private File[] dirInOrder(File[] tempFiles) {
        ArrayList<File> fileList = new ArrayList<>();
        ArrayList<File> dirList = new ArrayList<>();
        for (File f : tempFiles) {
            if (f.isDirectory()) {
                dirList.add(f);
            } else if (f.isFile()) {
                fileList.add(f);
            }
        }
        dirList.addAll(fileList);
        File[] files = new File[dirList.size()];
        for (int i = 0; i < dirList.size(); i++) {
            files[i] = dirList.get(i);
        }
        return files;
    }
}
