package com.ntjxj.myvideo;

import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.ntjxj.data.AudioData;
import com.ntjxj.data.AudioDataUtil;
import com.ntjxj.data.MessageQueue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by lixiaodong on 2018/2/23.
 */

public class DecodeAudio extends JobHandler {

    private boolean isDecode;

    public boolean isDecode() {
        return isDecode;
    }

    public void setDecode(boolean decode) {
        isDecode = decode;
    }

    public DecodeAudio(Handler handler) {
        super(handler);
    }

    @Override
    public void run() {
        while (true) {
            AudioData data = MessageQueue.getInstance(MessageQueue.DECODER_DATA_QUEUE).take();
            byte[] spx = data.getEncodedData();
            short[] rawData = AudioDataUtil.spx2raw(spx);
            MessageQueue.getInstance(MessageQueue.TRACKER_DATA_QUEUE).put(new AudioData(rawData));
        }
//        try {
//            File outDir = Environment.getExternalStorageDirectory();
//            File soundFile = new File(outDir, "sound_test.raw");
//            BufferedReader orders = new BufferedReader(new FileReader(new File(outDir, "orders.txt")));
//            FileInputStream fis = new FileInputStream(soundFile);
//            Log.e("DecodeAudio", "size: " + fis.available());
//            String s = null;
//            int total = 0;
//            while ((s = orders.readLine()) != null) {
//                int len = Integer.valueOf(s);
//                byte[] data = new byte[len];
//                fis.read(data);
//                total += len;
//                short[] spx = AudioDataUtil.spx2raw(data);
//                Log.e("DecodeAudio", "size: " + spx.length);
//                AudioData ad = new AudioData();
//                ad.setRawData(spx);
//                MessageQueue.getInstance(MessageQueue.TRACKER_DATA_QUEUE).put(ad);
//            }
//            fis.close();
//            orders.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void free() {
        AudioDataUtil.free();
    }
}
