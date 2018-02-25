package com.ntjxj.myvideo;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ntjxj.data.AudioData;
import com.ntjxj.data.AudioDataUtil;
import com.ntjxj.data.MessageQueue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by lixiaodong on 2018/2/23.
 */

public class EncodeAudio extends JobHandler {
    public EncodeAudio(Handler handler) {
        super(handler);
    }

    @Override
    public void free() {
        AudioDataUtil.free();
    }

    @Override
    public void run() {
        //File outDir = Environment.getExternalStorageDirectory();
        //FileOutputStream fos = null;
        //BufferedWriter orders = null;
        //try {
        //    File soundFile = new File(outDir, "sound_test.raw");
        //    fos = new FileOutputStream(soundFile);
        //    orders = new BufferedWriter(new FileWriter(new File(outDir, "orders.txt")));
        // 在MessageQueue为空时，take方法阻塞
        AudioData data;
        while ((data = MessageQueue.getInstance(MessageQueue.ENCODER_DATA_QUEUE).take()) != null) {
            short[] rawData = data.getRawData();
            if (rawData != null) {
                byte[] b = AudioDataUtil.raw2spx(data.getRawData());
                MessageQueue.getInstance(MessageQueue.SENDER_DATA_QUEUE).put(new AudioData(b));
                //fos.write(b);
                //orders.write(b.length + "\n");
            }
            //Log.e("Recorder",soundFile.getAbsolutePath()+isRecording);
            //FileOutputStream fos = new FileOutputStream(soundFile);
            //data.setEncodedData(AudioDataUtil.raw2spx(data.getRawData()));
            //MessageQueue.getInstance(MessageQueue.SENDER_DATA_QUEUE).put(data);
        }
        //} catch (IOException e) {
        //Log.e("EncodeAudio", e.getMessage());
        //    e.printStackTrace();
        // } finally {
        //     try {
        //        if (fos != null) {
        //            fos.close();
        //        }
        //       if (orders != null) {
        //           orders.close();
        //       }
        //   } catch (IOException e) {
        //       e.printStackTrace();
        //   }
        //}
    }
}
