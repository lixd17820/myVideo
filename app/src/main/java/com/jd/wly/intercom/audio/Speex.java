package com.jd.wly.intercom.audio;

import android.util.Log;

/**
 * Created by yanghao1 on 2017/4/18.
 */

public class Speex {

    private static final int DEFAULT_COMPRESSION = 5;



    private static Speex speex = null;

    private Speex() {
        open(DEFAULT_COMPRESSION);
    }

    public static Speex getInstance() {
        if (speex == null) {
            synchronized (Speex.class) {
                if (speex == null) {
                    speex = new Speex();
                }
            }
        }
        return speex;
    }

    public native int open(int compression);

    public native int getFrameSize();

    public native int decode(byte encoded[], short lin[], int size);

    public native int encode(short lin[], byte encoded[], int size);

    public native void close();
}
