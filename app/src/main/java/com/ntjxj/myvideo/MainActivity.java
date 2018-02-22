package com.ntjxj.myvideo;

import android.media.AudioRecord;
import android.os.Environment;
import android.os.Handler;
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
    TextView tv;
    Recorder recorder;
    Tracker tracker;

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
        recorder = new Recorder(myHandler);
        tracker = new Tracker(myHandler);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recorder.isRecording()) {
                    return;
                }
                recorder.setRecording(true);
                recorder.start();
                tv.setText("正在录音");
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (recorder.isRecording()) {
                    recorder.setRecording(false);
                    tv.setText("不在录音");
                }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recorder.free();
        tracker.free();
    }

    private Handler myHandler = new Handler() {

    };
}
