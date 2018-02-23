package com.ntjxj.data;

import android.util.Log;

import com.jd.wly.intercom.audio.Speex;

/**
 * Created by lixiaodong on 2018/2/23.
 */

public class AudioDataUtil {

    /*The frame size in hardcoded for this sample code but it doesn't have to be*/
    private static int encFrameSize = 160;
    private static int decFrameSize = 160;
    private static int encodedFrameSize = 28;

    /**
     * 将raw原始音频文件编码为Speex格式
     *
     * @param audioData 原始音频数据
     * @return 编码后的数据
     */
    public static byte[] raw2spx(short[] audioData) {

        int len = ((audioData.length - 1) / encFrameSize + 1) * encodedFrameSize;
        int len2 = audioData.length / encFrameSize * encodedFrameSize;

        byte[] encodedData = new byte[audioData.length * 4];
        int re = Speex.getInstance().encode(audioData, encodedData, audioData.length);
        Log.e("AudioUtil", audioData.length + "/" + len + "/" + len2 + "/" + re);
        byte[] dest = new byte[re];
        System.arraycopy(encodedData, 0, dest, 0, re);
        return dest;


    }

    /**
     * 将Speex编码音频文件解码为raw音频格式
     *
     * @param encodedData 编码音频数据
     * @return 原始音频数据
     */
    public static short[] spx2raw(byte[] encodedData) {
        short[] shortRawData = new short[encodedData.length * decFrameSize / encodedFrameSize];
        //short[] shortRawData = new short[10000000];
        int re = Speex.getInstance().decode(encodedData, shortRawData, encodedFrameSize);
        short[] dest = new short[re];
        System.arraycopy(shortRawData, 0, dest, 0, re);
        return dest;
    }

    /**
     * 释放音频编解码资源
     */
    public static void free() {
        Speex.getInstance().close();
    }
}
