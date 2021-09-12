package com.example.face_scan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class Flash_screen extends AppCompatActivity {
    ProgressBar P;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_screen);
        P = findViewById(R.id.splash_load);
        P.setVisibility(View.VISIBLE);
        getSupportActionBar().hide();
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(Flash_screen.this, login.class));
                finish();
            }
        },3000);
    }
}
