package com.example.filemanager;

import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.filemanager.tools.FileDeleter;
import com.example.filemanager.tools.FileSearcher;
import com.example.filemanager.tools.FileTools;

import java.io.File;

public class LargeFiles extends AppCompatActivity {
    private ImageButton goBackButton;
    private ListView largeFilesList;
    private Button deleteAllLargeFiles;
    private File[] largeFiles;
    private FileSearcher searcher;
    private LargeFileListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_large_files);
        goBackButtonInit();
        fileListInit();
        deleteAllButtonInit();
    }

    private void deleteAllButtonInit() {
        deleteAllLargeFiles = findViewById(R.id.delete_all_largefiles);
        deleteAllLargeFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(LargeFiles.this);
                dialog.setTitle("删除");
                dialog.setMessage("是否要删除所有文件");
                dialog.setCancelable(false);
                dialog.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (FileDeleter.deleteAll(largeFiles)) {
                            largeFiles = searcher.search();
                            adapter.changeFiles(largeFiles);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(LargeFiles.this, "已全部删除", Toast.LENGTH_SHORT).show();
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
            }
        });
    }

    private void fileListInit() {
        File root = new File(Environment.getExternalStorageDirectory().toString());
        largeFilesList = (ListView) findViewById(R.id.large_files_list);
        searcher = new FileSearcher(root, FileTools.LARGE_FILES);
        largeFiles = searcher.search();
        adapter = new LargeFileListAdapter(LargeFiles.this, largeFiles);
        largeFilesList.setAdapter(adapter);
        largeFilesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FileTools.openFile(largeFiles[position], LargeFiles.this);
            }
        });
        largeFilesList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(LargeFiles.this);
                dialog.setTitle("删除");
                dialog.setMessage("是否要删除此文件");
                dialog.setCancelable(false);
                dialog.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (FileDeleter.deleteAll(largeFiles[position])) {
                            largeFiles = searcher.search();
                            adapter.changeFiles(largeFiles);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(LargeFiles.this, "已删除", Toast.LENGTH_SHORT).show();
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

    private void goBackButtonInit() {
        goBackButton = (ImageButton) findViewById(R.id.go_back_button_largefiles);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LargeFiles.this.finish();
            }
        });
    }
}
