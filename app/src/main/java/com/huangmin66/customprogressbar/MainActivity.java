package com.huangmin66.customprogressbar;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {

    private HorizontalProgressBar mProgress;
    private CustomProgressBar customProgressBar;

    private static final int MSG_UPDATE = 0X110;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int progress = mProgress.getProgress();
            mProgress.setProgress(++progress);
            customProgressBar.setProgress(++progress);
            if (progress >= 100){
                mHandler.removeMessages(MSG_UPDATE);
            }
            mHandler.sendEmptyMessageDelayed(MSG_UPDATE, 100);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgress = (HorizontalProgressBar) findViewById(R.id.id_progress01);
        customProgressBar = (CustomProgressBar) findViewById(R.id.id_progress02);

        mHandler.sendEmptyMessage(MSG_UPDATE);
    }
}
