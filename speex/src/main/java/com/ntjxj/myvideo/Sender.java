package com.ntjxj.myvideo;

import android.os.Handler;

import com.ntjxj.data.AudioData;
import com.ntjxj.data.MessageQueue;
import com.ntjxj.web.WebClient;

/**
 * Created by lixiaodong on 2018/2/25.
 */

public class Sender extends JobHandler {
    public Sender(Handler handler) {
        super(handler);
    }

    @Override
    public void run() {
        while (true) {
            AudioData data = MessageQueue.getInstance(MessageQueue.SENDER_DATA_QUEUE).take();
            WebClient.uploadTest("061247", data.getEncodedData());
        }
    }
}
