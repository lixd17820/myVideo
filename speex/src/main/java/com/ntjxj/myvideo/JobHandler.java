package com.ntjxj.myvideo;

import android.os.Handler;

/**
 * Created by lixiaodong on 2018/2/21.
 */

public abstract class JobHandler extends Thread {
    protected Handler handler;

    public JobHandler(Handler handler) {
        this.handler = handler;
    }

    public void free() {

    }
}
