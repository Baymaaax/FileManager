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

public class MusicActivity extends AppCompatActivity {
    private ImageButton homeButton;
    private ListView musicList;
    private MusicListAdapter musicListAdapter;
    private File[] allMusicFiles;
    private FileSearcher fileSearcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        homeButtonInit();
        musicListInit();
    }

    //音乐显示列表初始化
    private void musicListInit() {
        musicList = (ListView) findViewById(R.id.music_list);
        File dir = new File(Environment.getExternalStorageDirectory().toString());
        fileSearcher = new FileSearcher(dir, FileTools.MUSIC);
        allMusicFiles = fileSearcher.search();
        musicListAdapter = new MusicListAdapter(MusicActivity.this, allMusicFiles);
        musicList.setAdapter(musicListAdapter);
        musicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FileTools.openMusicFile(allMusicFiles[position], MusicActivity.this);
            }

        });
        musicList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MusicActivity.this);
                dialog.setTitle("删除");
                dialog.setMessage("是否要删除此音频文件");
                dialog.setCancelable(false);
                dialog.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (FileDeleter.deleteAll(allMusicFiles[position])) {
                            allMusicFiles = fileSearcher.search();
                            musicListAdapter.changeFiles(allMusicFiles);
                            musicListAdapter.notifyDataSetChanged();
                            Toast.makeText(MusicActivity.this, "已删除", Toast.LENGTH_SHORT).show();
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
        homeButton = (ImageButton) findViewById(R.id.home_button_music);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MusicActivity.this, MainActivity.class);
                startActivity(intent);
                MusicActivity.this.finish();
            }
        });
    }
}
