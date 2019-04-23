package com.example.filemanager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.filemanager.tools.FileDeleter;
import com.example.filemanager.tools.FileSearcher;
import com.example.filemanager.tools.FileTpye;
import com.example.filemanager.tools.FilterByType;
import com.example.filemanager.tools.Stack;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final static int[] categoryPicture = {R.drawable.music_folder, R.drawable.video_folder,
            R.drawable.document_folder, R.drawable.image_folder, R.drawable.all_files_folder};
    private final static String[] categoryName = {"音乐", "视频", "文档", "图片", "全部文件"};
    private TextView spaceMessage;
    private ImageButton cleanerButton;
    private ListView categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestAllPower();
        categoryListInit();
        cacheCleanerInit();
        spaceMessageInit();

    }
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
    private void spaceMessageInit() {
        File dir = new File(Environment.getExternalStorageDirectory().toString());
        int freeSpace = (int) (dir.getFreeSpace() / (1024 * 1024));
        int totalSpace = (int) (dir.getTotalSpace() / (1024 * 1024));
        int usedSpace = totalSpace - freeSpace;
        spaceMessage = (TextView) findViewById(R.id.space_message);
        spaceMessage.setText("已使用：" + usedSpace + "MB" + "\n" +
                "总共：" + totalSpace + "MB" + "\n" +
                "剩余：" + freeSpace + "MB");
    }

    private void cacheCleanerInit() {
        cleanerButton = (ImageButton) findViewById(R.id.cleaner_button);
        cleanerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = Environment.getExternalStorageDirectory().toString()+ "/Android/data";
                File dataFile = new File(path);
                File[] packages=dataFile.listFiles();
                Stack cacheStack=new Stack();
//
//
                for(File file:packages){
                    File[] packageInnerDir=file.listFiles();
                    if(!(packageInnerDir==null)){
                        for(File f:packageInnerDir){
                            if(f.getName().equals("cache")){
                                cacheStack.push(f);
                            }
                        }
                    }
                }

                while (!cacheStack.isEmpty()){
                    File cacheDir=(File) cacheStack.pop();
                    Log.i("/cleaner",cacheDir.getAbsolutePath());
                    FileDeleter.deleteInner(cacheDir);
                }

            }

        });
    }


    private void categoryListInit() {
        categoryList = (ListView) findViewById(R.id.category_list);
        final CategoryListAdapter categoryListAdapter = new CategoryListAdapter(MainActivity.this
                , categoryPicture, categoryName);
        categoryList.setAdapter(categoryListAdapter);
        categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case FileTpye.MUSIC: {
                        Intent intent = new Intent(MainActivity.this, MusicActivity.class);
                        startActivity(intent);

                    }
                    break;
                    case FileTpye.VIDEO: {
                        Intent intent = new Intent(MainActivity.this, VideoActivity.class);
                        startActivity(intent);
                    }
                    break;

                    case FileTpye.DOCUMENT: {
                        Intent intent = new Intent(MainActivity.this, DocumentActivity.class);
                        startActivity(intent);
                    }
                    break;

                    case FileTpye.IMAGE: {
                        Intent intent = new Intent(MainActivity.this, ImageActivity.class);
                        startActivity(intent);
                    }
                    break;

                    case FileTpye.ALL_FILES: {
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
