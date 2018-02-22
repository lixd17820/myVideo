package com.ntjxj.myvideo;

import android.media.AudioRecord;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jd.wly.intercom.audio.Speex;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";
    Button btnStart, btnStop, btnPlay;
    TextView tv, tvTimes;
    Recorder recorder;
    Tracker tracker;
    TimeThread timeThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File f = Environment.getExternalStorageDirectory();
        Log.e(TAG, f.getAbsolutePath());
        btnStart = findViewById(R.id.btn_start);
        btnStop = findViewById(R.id.btn_stop);
        btnPlay = findViewById(R.id.btn_play);
        tv = findViewById(R.id.tv1);
        tvTimes = findViewById(R.id.tv_times);
        tvTimes.setText("time");
        recorder = new Recorder(myHandler);
        tracker = new Tracker(myHandler);
        timeThread = new TimeThread(timeHander);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recorder.isRecording()) {
                    return;
                }
                recorder.setRecording(true);
                recorder.start();
                tv.setText("正在录音");
                timeThread.setTime(true);
                timeThread.start();
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                stopRecord();
            }
        });
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recorder.isRecording()) {
                    Toast.makeText(MainActivity.this, "正在录音", Toast.LENGTH_SHORT).show();
                    return;
                }
                tracker.start();
            }
        });
    }

    private void stopRecord(){
        if (recorder.isRecording()) {
            recorder.setRecording(false);
            tv.setText("不在录音");
        }
        timeThread.setTime(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recorder.free();
        tracker.free();
    }

    private Handler timeHander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;

            if (what == 1) {
                tvTimes.setText(String.valueOf(msg.arg1));
                if (msg.arg1 >= 20) {
                    stopRecord();
                }
            } else {
            }

        }
    };

    private Handler myHandler = new Handler() {

    };
}
