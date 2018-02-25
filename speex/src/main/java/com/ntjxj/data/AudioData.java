package com.ntjxj.data;

/**
 * Created by lixiaodong on 2018/2/23.
 */

public class AudioData {

    private boolean isStop;

    /**
     * 原始数据
     */
    private short[] rawData;

    /**
     * 加密数据
     */
    private byte[] encodedData;

    public AudioData() {
    }

    public AudioData(short[] rawData) {
        this.rawData = rawData;
    }

    public AudioData(byte[] encodedData) {
        this.encodedData = encodedData;
    }

    public boolean isStop() {
        return isStop;
    }

    public void setStop(boolean stop) {
        isStop = stop;
    }

    public short[] getRawData() {
        return rawData;
    }

    public void setRawData(short[] rawData) {
        this.rawData = rawData;
    }

    public byte[] getEncodedData() {
        return encodedData;
    }

    public void setEncodedData(byte[] encodedData) {
        this.encodedData = encodedData;
    }
}
