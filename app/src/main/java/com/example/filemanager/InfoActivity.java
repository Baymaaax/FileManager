package com.example.filemanager;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {
    private TextView brand;
    private TextView model;
    private TextView systemVersion;
    private TextView firmwareVersion;
    private ImageButton homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        infoInit();
        homeButtonInit();
    }

    //初始化home键，添加点击事件返回MainActivity
    private void homeButtonInit() {
        homeButton = (ImageButton) findViewById(R.id.home_button_info);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoActivity.this.finish();
            }
        });
    }

    //初始化TextView 并添加要显示的相关信息。
    private void infoInit() {
        brand = (TextView) findViewById(R.id.brand);
        model = (TextView) findViewById(R.id.model);
        systemVersion = (TextView) findViewById(R.id.system_version);
        firmwareVersion = (TextView) findViewById(R.id.firmware_version);
        brand.setText(Build.BRAND);
        model.setText(Build.MODEL);
        systemVersion.setText(Build.VERSION.RELEASE);
        firmwareVersion.setText(Build.DISPLAY);
    }
}
