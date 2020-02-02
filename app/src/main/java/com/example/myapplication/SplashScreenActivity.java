package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import com.ldoublem.loadingviewlib.view.LVCircularZoom;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private Handler mHandler;

    private Runnable myRunnable;

    LVCircularZoom mLVCircularZoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mLVCircularZoom = (LVCircularZoom) findViewById(R.id.lv_circularZoom);
     //   mLVCircularZoom.setViewColor(Color.rgb(255, 0, 122));
        mLVCircularZoom.setViewColor(R.color.white);

        mLVCircularZoom.startAnim();

        mHandler = new Handler();
        myRunnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };


    }

    @Override
    public void onBackPressed() {
// Remove callback on back press
        if (mHandler != null && myRunnable != null) {
            mHandler.removeCallbacks(myRunnable);
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
// Remove callback on pause
        if (mHandler != null && myRunnable != null) {
            mHandler.removeCallbacks(myRunnable);
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
// Attach and start callback with delay on resume
        if (mHandler != null && myRunnable != null) {
            mHandler.postDelayed(myRunnable, 1000);
        }
        super.onResume();
    }
}

