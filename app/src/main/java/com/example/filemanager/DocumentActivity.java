package com.example.filemanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import com.example.filemanager.tools.FileTpye;

import java.io.File;

public class DocumentActivity extends AppCompatActivity {
    private ImageButton homeButton;
    private ListView documentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);
        homeButton=(ImageButton) findViewById(R.id.home_button_document);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent =new Intent(DocumentActivity.this, MainActivity.class);
//                startActivity(intent);
                DocumentActivity.this.finish();
            }
        });
        documentList=(ListView) findViewById(R.id.document_list);
        File dir=new File(Environment.getExternalStorageDirectory().toString());
        FileSearcher fileSearcher=new FileSearcher(dir, FileTpye.DOCUMENT);
        final File[] files=fileSearcher.search();
        final DocumentListAdapter documentListAdapter=new DocumentListAdapter(DocumentActivity.this,files);
        documentList.setAdapter(documentListAdapter);
        documentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(files[position]),"text/plain");
                startActivity(intent);
            }
        });
        documentList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder dialog=new AlertDialog.Builder(DocumentActivity.this);
                dialog.setTitle("删除");
                dialog.setMessage("是否要删除此文本文件");
                dialog.setCancelable(false);
                dialog.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(FileDeleter.deleteAll(files[position])){

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
}
