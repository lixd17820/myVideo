package com.ntjxj.myvideo;

import android.os.Handler;
import android.util.Log;

import com.ntjxj.data.AudioData;
import com.ntjxj.data.MessageQueue;
import com.ntjxj.web.WebClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by lixiaodong on 2018/2/25.
 */

public class Reciver extends JobHandler {
    public Reciver(Handler handler) {
        super(handler);
    }

    private boolean isDown;

    public boolean isDown() {
        return isDown;
    }

    public void setDown(boolean down) {
        isDown = down;
    }

    @Override
    public void run() {
        while (isDown) {
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            int i = WebClient.testDownload(bao, "061247", "061247");
            if (i > 0) {
                MessageQueue.getInstance(MessageQueue.DECODER_DATA_QUEUE).put(new AudioData(bao.toByteArray()));
            } else {
                sleep(1000);
            }
            try {
                bao.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            sleep(50);
        }
        Log.e("Reciver", "Reciver is gone!");
    }

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void free() {
        super.free();
        isDown = false;
    }
}
