package com.example.filemanager;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableRow;
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
        brand=(TextView)findViewById(R.id.brand);
        model=(TextView)findViewById(R.id.model);
        systemVersion=(TextView)findViewById(R.id.system_version);
        firmwareVersion=(TextView)findViewById(R.id.firmware_version);
        brand.setText(Build.BRAND);
        model.setText(Build.MODEL);
        systemVersion.setText(Build.VERSION.RELEASE);
        firmwareVersion.setText( Build.DISPLAY);
        homeButton=(ImageButton)findViewById(R.id.home_button_info);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(InfoActivity.this,MainActivity.class);
//                startActivity(intent);
                InfoActivity.this.finish();
            }
        });


    }
}
