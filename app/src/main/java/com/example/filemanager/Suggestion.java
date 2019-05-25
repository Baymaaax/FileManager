package com.example.filemanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.filemanager.tools.Cleaner;
import com.example.filemanager.tools.FileDeleter;
import com.example.filemanager.tools.UnitConversion;

public class Suggestion extends AppCompatActivity {
    private ImageButton homeButton;
    private ImageButton cacheCleanButton;
    private ImageButton tempCleanButton;
    private ImageButton logCleanButton;
    private TextView cacheSize;
    private TextView tempSize;
    private TextView logSize;
    private ImageButton apksButton;
    private ImageButton largeFilesButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);
        homeButtonInit();
        cleanerInit();
        installedApkInit();
        largeFilesInit();

    }

    private void largeFilesInit() {
        largeFilesButton=(ImageButton) findViewById(R.id.large_files_button);
        largeFilesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Suggestion.this,LargeFiles.class);
                startActivity(intent);
            }
        });
    }

    private void installedApkInit() {
        apksButton=(ImageButton) findViewById(R.id.apks_button);
        apksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Suggestion.this,InstalledApks.class);
                startActivity(intent);
            }
        });


    }

    private void cleanerInit() {
        cacheCleanButton = (ImageButton) findViewById(R.id.cache_clean_button);
        tempCleanButton = (ImageButton) findViewById(R.id.temp_clean_button);
        logCleanButton = (ImageButton) findViewById(R.id.log_clean_button);
        cacheSize = (TextView) findViewById(R.id.cache_size);
        tempSize = (TextView) findViewById(R.id.temp_size);
        logSize = (TextView) findViewById(R.id.log_size);
        cacheSize.setText(UnitConversion.getMB(Cleaner.getCacheSize()) + "MB");
        cacheCleanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog=new AlertDialog.Builder(Suggestion.this);
                dialog.setTitle("清除缓存");
                dialog.setMessage("是否要清理缓存文件");
                dialog.setCancelable(false);
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.setPositiveButton("清除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Suggestion.this,
                                "已清除" + UnitConversion.getMB(Cleaner.cleanCache()) + "MB"
                                , Toast.LENGTH_SHORT).show();
                        cacheSize.setText(UnitConversion.getMB(Cleaner.getCacheSize()) + "MB");

                    }
                });
                dialog.show();

            }
        });
        tempSize.setText(UnitConversion.getKB(Cleaner.getTempSize()) + "KB");
        tempCleanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog=new AlertDialog.Builder(Suggestion.this);
                dialog.setTitle("清除临时文件");
                dialog.setMessage("是否要清理临时文件");
                dialog.setCancelable(false);
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.setPositiveButton("清除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Suggestion.this,
                                "已清除" + UnitConversion.getKB(Cleaner.cleanTemp()) + "KB"
                                , Toast.LENGTH_SHORT).show();
                        tempSize.setText(UnitConversion.getKB(Cleaner.getTempSize()) + "KB");

                    }
                });
                dialog.show();

            }
        });
        logSize.setText(UnitConversion.getKB(Cleaner.getLogSize()) + "KB");
        logCleanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog=new AlertDialog.Builder(Suggestion.this);
                dialog.setTitle("清除日志文件");
                dialog.setMessage("是否要清理日志文件");
                dialog.setCancelable(false);
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.setPositiveButton("清除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Suggestion.this,
                                "已清除" + UnitConversion.getKB(Cleaner.cleanLog()) + "KB"
                                , Toast.LENGTH_SHORT).show();
                        logSize.setText(UnitConversion.getKB(Cleaner.getLogSize()) + "KB");

                    }
                });
                dialog.show();

            }
        });

    }

    private void homeButtonInit() {
        homeButton = findViewById(R.id.home_button_suggestion);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Suggestion.this, MainActivity.class);
                startActivity(intent);
                Suggestion.this.finish();
            }
        });
    }
}
