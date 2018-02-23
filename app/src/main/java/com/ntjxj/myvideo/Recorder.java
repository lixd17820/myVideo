package com.ntjxj.myvideo;

import android.media.AudioRecord;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.ntjxj.data.AudioData;
import com.ntjxj.data.MessageQueue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by lixiaodong on 2018/2/21.
 */

public class Recorder extends JobHandler {

    private AudioRecord audioRecord;
    // 音频大小
    private int inAudioBufferSize;

    // 录音标志
    private boolean isRecording = false;

    public Recorder(Handler handler) {
        super(handler);
        inAudioBufferSize = AudioRecord.getMinBufferSize(
                Constants.sampleRateInHz, Constants.inputChannelConfig, Constants.audioFormat);
        // 初始化音频录制
        audioRecord = new AudioRecord(Constants.audioSource,
                Constants.sampleRateInHz, Constants.inputChannelConfig, Constants.audioFormat, inAudioBufferSize);
    }

    public boolean isRecording() {
        return isRecording;
    }

    public void setRecording(boolean recording) {
        isRecording = recording;
    }


    @Override
    public void run() {
        //try {
        //File soundFile = new File( Environment.getExternalStorageDirectory(),"sound_test.raw");
        //Log.e("Recorder",soundFile.getAbsolutePath()+isRecording);
        //FileOutputStream fos = new FileOutputStream(soundFile);
        while (isRecording) {
            if (audioRecord.getRecordingState() == AudioRecord.RECORDSTATE_STOPPED) {
                audioRecord.startRecording();
            }
            // 实例化音频数据缓冲
            //byte[] rawData = new byte[inAudioBufferSize];
            //int len = audioRecord.read(rawData, 0, inAudioBufferSize);
            //fos.write(rawData, 0, len);
            short[] rawData = new short[inAudioBufferSize];
            int len = audioRecord.read(rawData, 0, inAudioBufferSize);
            AudioData data = new AudioData(Arrays.copyOf(rawData,len));
            MessageQueue.getInstance(MessageQueue.ENCODER_DATA_QUEUE).put(data);
        }
        AudioData data = new AudioData();
        data.setStop(true);
        MessageQueue.getInstance(MessageQueue.ENCODER_DATA_QUEUE).put(data);
        //if (fos != null) {
        //    fos.close();
        //}
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
    }

    @Override
    public void free() {
        // 释放音频录制资源
        audioRecord.stop();
        audioRecord.release();
        audioRecord = null;
    }
}
