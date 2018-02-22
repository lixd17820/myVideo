package com.ntjxj.myvideo;

import android.media.AudioRecord;
import android.media.AudioTrack;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * AudioTrack音频播放
 *
 * @author yanghao1
 */
public class Tracker extends JobHandler {

    private AudioTrack audioTrack;
    // 音频大小
    private int outAudioBufferSize;
    // 播放标志
    private boolean isPlaying = true;

    public Tracker(Handler handler) {
        super(handler);
        // 获取音频数据缓冲段大小
        outAudioBufferSize = AudioTrack.getMinBufferSize(
                Constants.sampleRateInHz, Constants.outputChannelConfig, Constants.audioFormat);
        // 初始化音频播放
        audioTrack = new AudioTrack(Constants.streamType,
                Constants.sampleRateInHz, Constants.outputChannelConfig, Constants.audioFormat,
                outAudioBufferSize, Constants.trackMode);
        Log.e("Tracker","max " + AudioTrack.getMaxVolume());
        audioTrack.play();
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    @Override
    public void run() {
        try {
            File soundFile = new File(Environment.getExternalStorageDirectory(), "sound_test.raw");
            Log.e("Tracker", soundFile.getAbsolutePath() + isPlaying);
            FileInputStream fis = new FileInputStream(soundFile);
            int len = 0;
            byte[] rawData = new byte[outAudioBufferSize];
            while ((len = fis.read(rawData)) > 0) {
                audioTrack.write(rawData, 0, len);
            }
            if (fis != null) {
                fis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void free() {
        audioTrack.stop();
        audioTrack.release();
        audioTrack = null;
    }
}
