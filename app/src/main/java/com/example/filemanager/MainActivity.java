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
import com.example.filemanager.tools.FileTools;
import com.example.filemanager.tools.UnitConversion;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private final static int[] categoryPicture = {R.drawable.music_folder, R.drawable.video_folder,
            R.drawable.document_folder, R.drawable.image_folder, R.drawable.all_files_folder};
    private final static String[] categoryName = {"音乐", "视频", "文档", "图片", "全部文件"};
    private TextView spaceMessage;
    private ImageButton cleanerButton;
    private ListView categoryList;
    private ImageButton infoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestAllPower();
        categoryListInit();
        cacheCleanerInit();
        spaceMessageInit();
        infoButtonInit();

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
        int freeSpace = UnitConversion.getGB(dir.getFreeSpace());
        int totalSpace = UnitConversion.getGB(dir.getTotalSpace());
        int usedSpace = totalSpace - freeSpace;
        spaceMessage = (TextView) findViewById(R.id.space_message);
        spaceMessage.setText("总 共：" + totalSpace + "GB" + "\n" +
                "已 用：" + usedSpace + "GB" + "\n" +
                "剩 余：" + freeSpace + "GB");
    }

    //缓存清理按键初始化，添加点击事件，弹出dialog询问是否清除缓存。
    private void cacheCleanerInit() {
        cleanerButton = (ImageButton) findViewById(R.id.cleaner_button);
        cleanerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("一键清理");
                dialog.setMessage("是否要删除所有缓存文件");
                dialog.setCancelable(false);
                dialog.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //new cleaner对象，并运行clean方法。
                        CacheCleaner cleaner = new CacheCleaner();
                        cleaner.clean();
                        //获取已清除缓存大小，换算成MB
                        int cachesize = UnitConversion.getMB(cleaner.getCleanedCacheSize());
                        Toast.makeText(MainActivity.this,
                                "已清除" + cachesize + "MB", Toast.LENGTH_SHORT).show();
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

    //初始化目录列表，包含音乐、视频、文档、图片、全部文件类别。并添加点击事件跳转到各自Activity
    private void categoryListInit() {
        categoryList = (ListView) findViewById(R.id.category_list);
        final CategoryListAdapter categoryListAdapter = new CategoryListAdapter(MainActivity.this
                , categoryPicture, categoryName);
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
