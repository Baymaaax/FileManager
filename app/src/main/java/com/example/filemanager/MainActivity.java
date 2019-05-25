package com.example.filemanager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

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
    private long freeSpace;
    private long usedSpace;
    private long totalSpace;
    private long musicSize;
    private long videoSize;
    private long documentSize;
    private long imageSize;
    private String allFileSize = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestAllPower();
        spaceMessageInit();
        categoryListInit();
        suggestionInit();
        infoButtonInit();

    }

    private void suggestionInit() {
        suggestionButton = (ImageButton) findViewById(R.id.suggestion_button);
        suggestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Suggestion.class);
                intent.putExtra("totalSpace", totalSpace);
                intent.putExtra("usedSpace", usedSpace);
                intent.putExtra("freeSpace", freeSpace);
                intent.putExtra("largestType", getLargestType());
                intent.putExtra("largestSize", getLargestSize());
                startActivity(intent);
            }
        });

    }

    private long getLargestSize() {
        long size = 0;
        switch (getLargestType()) {
            case FileTools.MUSIC:
                size = musicSize;
                break;
            case FileTools.VIDEO:
                size = videoSize;
                break;
            case FileTools.IMAGE:
                size = imageSize;
                break;
            case FileTools.DOCUMENT:
                size = documentSize;
                break;
            default:
                size=-1;

        }
        return size;
    }

    private int getLargestType() {
        int largeOne = -1;
        if (videoSize >= musicSize && videoSize >= imageSize && videoSize >= documentSize) {
            largeOne = FileTools.VIDEO;
        } else if (musicSize >= videoSize && musicSize >= imageSize && musicSize >= documentSize) {
            largeOne = FileTools.MUSIC;
        } else if (imageSize >= videoSize && imageSize >= musicSize && imageSize >= documentSize) {
            largeOne = FileTools.IMAGE;
        } else if (documentSize >= videoSize && documentSize >= musicSize && documentSize >= imageSize) {
            largeOne = FileTools.DOCUMENT;
        }
        return largeOne;
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
        freeSpace = dir.getFreeSpace();
        float freeSpaceToGB = UnitConversion.getGB(freeSpace);
        totalSpace = dir.getTotalSpace();
        float totalSpaceToGB = UnitConversion.getGB(totalSpace);
        usedSpace = totalSpace - freeSpace;
        float usedSpaceToGB = UnitConversion.getGB(usedSpace);
        spaceMessage = (TextView) findViewById(R.id.space_message);
        spaceMessage.setText("总 共：" + totalSpaceToGB + "GB" + "\n" +
                "已 用：" + usedSpaceToGB + "GB" + "\n" +
                "剩 余：" + freeSpaceToGB + "GB");
    }


    //初始化目录列表，包含音乐、视频、文档、图片、全部文件类别。并添加点击事件跳转到各自Activity
    private void categoryListInit() {

        File rootDir = new File(Environment.getExternalStorageDirectory().toString());
        FileSearcher musicSearcher = new FileSearcher(rootDir, FileTools.MUSIC);
        FileSearcher videoSearcher = new FileSearcher(rootDir, FileTools.VIDEO);
        FileSearcher documentSearcher = new FileSearcher(rootDir, FileTools.DOCUMENT);
        FileSearcher imageSearcher = new FileSearcher(rootDir, FileTools.IMAGE);
        musicSize = FileTools.getTotalSize(musicSearcher.search());
        String musicSizeToMB = UnitConversion.getMB(musicSize) + "MB";
        videoSize = FileTools.getTotalSize(videoSearcher.search());
        String videoSizeToMB = UnitConversion.getMB(videoSize) + "MB";
        documentSize = FileTools.getTotalSize(documentSearcher.search());
        String documentSizeToMB = UnitConversion.getMB(documentSize) + "MB";
        imageSize = FileTools.getTotalSize(imageSearcher.search());
        String imageSizeToMB = UnitConversion.getMB(imageSize) + "MB";
        String[] size = {musicSizeToMB, videoSizeToMB, documentSizeToMB, imageSizeToMB, allFileSize};

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
