package com.example.filemanager;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.filemanager.tools.CacheCleaner;
import com.example.filemanager.tools.Cleaner;
import com.example.filemanager.tools.FileSearcher;
import com.example.filemanager.tools.FileTools;
import com.example.filemanager.tools.UnitConversion;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private final static int[] categoryPicture = {R.drawable.music_folder, R.drawable.video_folder,
            R.drawable.document_folder, R.drawable.image_folder, R.drawable.all_files_folder};
    private final static String[] categoryName = {"音乐", "视频", "文档", "图片", "全部文件"};
    private TextView spaceMessage;
    private ImageButton suggestionButton;
    private ListView categoryList;
    private ImageButton infoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestAllPower();
        categoryListInit();
        suggestionInit();
        spaceMessageInit();
        infoButtonInit();

    }

    private void suggestionInit() {
        suggestionButton=(ImageButton) findViewById(R.id.suggestion_button);
        suggestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,Suggestion.class);
                startActivity(intent);
            }
        });

    }

    //详细信息按钮初始化，并添加点击事件，跳转到infoActivity
    private void infoButtonInit() {
        infoButton = (ImageButton) findViewById(R.id.info_button);
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(intent);
            }
        });
    }

    //判断是否有读写外部存储的权限，无权限时弹窗提示授权
    public void requestAllPower() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }

    }

    //存储空间信息初始化
    private void spaceMessageInit() {
        File dir = new File(Environment.getExternalStorageDirectory().toString());
        float freeSpace = UnitConversion.getGB(dir.getFreeSpace());
        float totalSpace = UnitConversion.getGB(dir.getTotalSpace());
        float usedSpace = (float) (Math.round((totalSpace - freeSpace) * 10)) / 10;
        spaceMessage = (TextView) findViewById(R.id.space_message);
        spaceMessage.setText("总 共：" + totalSpace + "GB" + "\n" +
                "已 用：" + usedSpace + "GB" + "\n" +
                "剩 余：" + freeSpace + "GB");
    }



    //初始化目录列表，包含音乐、视频、文档、图片、全部文件类别。并添加点击事件跳转到各自Activity
    private void categoryListInit() {
        String musicSize;
        String videoSize;
        String documentSize;
        String imageSize;
        String allFileSize = "";
        File rootDir = new File(Environment.getExternalStorageDirectory().toString());
        FileSearcher musicSearcher = new FileSearcher(rootDir, FileTools.MUSIC);
        FileSearcher videoSearcher = new FileSearcher(rootDir, FileTools.VIDEO);
        FileSearcher documentSearcher = new FileSearcher(rootDir, FileTools.DOCUMENT);
        FileSearcher imageSearcher = new FileSearcher(rootDir, FileTools.IMAGE);
        musicSize = UnitConversion.getMB(FileTools.getTotalSize(musicSearcher.search())) + "MB";
        videoSize = UnitConversion.getMB(FileTools.getTotalSize(videoSearcher.search())) + "MB";
        documentSize = UnitConversion.getMB(FileTools.getTotalSize(documentSearcher.search())) + "MB";
        imageSize = UnitConversion.getMB(FileTools.getTotalSize(imageSearcher.search())) + "MB";
        String[] size = {musicSize, videoSize, documentSize, imageSize, allFileSize};

        categoryList = (ListView) findViewById(R.id.category_list);
        final CategoryListAdapter categoryListAdapter = new CategoryListAdapter(MainActivity.this
                , categoryPicture, categoryName, size);
        categoryList.setAdapter(categoryListAdapter);
        categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case FileTools.MUSIC: {
                        Intent intent = new Intent(MainActivity.this, MusicActivity.class);
                        startActivity(intent);
                    }
                    break;
                    case FileTools.VIDEO: {
                        Intent intent = new Intent(MainActivity.this, VideoActivity.class);
                        startActivity(intent);
                    }
                    break;

                    case FileTools.DOCUMENT: {
                        Intent intent = new Intent(MainActivity.this, DocumentActivity.class);
                        startActivity(intent);
                    }
                    break;

                    case FileTools.IMAGE: {
                        Intent intent = new Intent(MainActivity.this, ImageActivity.class);
                        startActivity(intent);
                    }
                    break;

                    case FileTools.ALL_FILES: {
                        Intent intent = new Intent(MainActivity.this, AllFilesActivity.class);
                        startActivity(intent);
                    }
                    break;

                    default:
                        break;
                }

            }
        });
    }


}
