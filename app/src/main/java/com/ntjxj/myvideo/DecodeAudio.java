package com.ntjxj.myvideo;

import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.ntjxj.data.AudioDataUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by lixiaodong on 2018/2/23.
 */

public class DecodeAudio extends JobHandler {
    public DecodeAudio(Handler handler) {
        super(handler);
    }

    @Override
    public void run() {
        try {
            File soundFile = new File(Environment.getExternalStorageDirectory(), "sound_test.raw");
            FileInputStream fis = new FileInputStream(soundFile);
            int len = 0;
            byte[] rawData = new byte[(int) soundFile.length()];
            fis.read(rawData);
            if (fis != null) {
                fis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void free() {
        AudioDataUtil.free();
    }
}
