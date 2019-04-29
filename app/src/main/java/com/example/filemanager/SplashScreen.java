package com.example.filemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        start();
    }

    //在欢迎页等待2.5秒后跳转登陆页面
    private void start() {
        TimerTask delayTask = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                SplashScreen.this.finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(delayTask, 2000);
    }

}
