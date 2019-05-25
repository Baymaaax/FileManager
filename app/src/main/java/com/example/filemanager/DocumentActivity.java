package com.example.filemanager;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.filemanager.tools.FileSearcher;
import com.example.filemanager.tools.FileTools;

import java.io.File;

public class DocumentActivity extends AppCompatActivity {
    private ImageButton homeButton;
    private ListView documentList;
    private FileSearcher fileSearcher;
    private File[] allDocumentfiles;
    private DocumentListAdapter documentListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);
        homeButtonInit();
        documentListInit();
    }

    //初始化文档显示列表
    private void documentListInit() {
        documentList = (ListView) findViewById(R.id.document_list);
        File dir = new File(Environment.getExternalStorageDirectory().toString());
        fileSearcher = new FileSearcher(dir, FileTools.DOCUMENT);
        allDocumentfiles = fileSearcher.search();
        documentListAdapter = new DocumentListAdapter(DocumentActivity.this, allDocumentfiles);
        documentList.setAdapter(documentListAdapter);
        documentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //打开文档类型的文件
                FileTools.openFile(allDocumentfiles[position], DocumentActivity.this);
            }
        });
        //长按点击事件，删除文件
        documentList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(DocumentActivity.this);
                dialog.setTitle("删除");
                dialog.setMessage("是否要删除此文本文件");
                dialog.setCancelable(false);
                dialog.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (FileDeleter.deleteAll(allDocumentfiles[position])) {
                            allDocumentfiles = fileSearcher.search();
                            documentListAdapter.changeFiles(allDocumentfiles);
                            documentListAdapter.notifyDataSetChanged();
                            Toast.makeText(DocumentActivity.this, "已删除", Toast.LENGTH_SHORT).show();
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
        homeButton = (ImageButton) findViewById(R.id.home_button_document);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DocumentActivity.this, MainActivity.class);
                startActivity(intent);
                DocumentActivity.this.finish();
            }
        });
    }
}
