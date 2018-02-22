package com.ntjxj.myvideo;

import android.os.Handler;
import android.os.Message;

/**
 * Created by lixiaodong on 2018/2/22.
 */

public class TimeThread extends JobHandler {

    int times;
    boolean isTime = true;

    public TimeThread(Handler handler) {
        super(handler);
    }

    public boolean isTime() {
        return isTime;
    }

    public void setTime(boolean time) {
        isTime = time;
    }

    @Override
    public void run() {
        times = 0;

        while(isTime){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            times++;
            Message msg = new Message();
            msg.what = 1;
            msg.arg1 = times;
            handler.sendMessage(msg);
        }
    }
}
