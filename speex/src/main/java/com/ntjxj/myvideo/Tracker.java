package com.ntjxj.myvideo;

import android.media.AudioTrack;
import android.os.Handler;
import android.util.Log;

import com.ntjxj.data.AudioData;
import com.ntjxj.data.MessageQueue;

/**
 * AudioTrack音频播放
 *
 * @author yanghao1
 */
public class Tracker extends JobHandler {

    private AudioTrack audioTrack;
    // 音频大小
    private int outAudioBufferSize;
    // 播放标志
    private boolean isPlaying = true;

    public Tracker(Handler handler) {
        super(handler);
        // 获取音频数据缓冲段大小
        outAudioBufferSize = AudioTrack.getMinBufferSize(
                Constants.sampleRateInHz, Constants.outputChannelConfig, Constants.audioFormat);
        // 初始化音频播放
        audioTrack = new AudioTrack(Constants.streamType,
                Constants.sampleRateInHz, Constants.outputChannelConfig, Constants.audioFormat,
                outAudioBufferSize, Constants.trackMode);
        Log.e("Tracker", "max " + AudioTrack.getMaxVolume());
        audioTrack.play();
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    @Override
    public void run() {
        while (true) {
            AudioData data  = MessageQueue.getInstance(MessageQueue.TRACKER_DATA_QUEUE).take();
            short[] spx = data.getRawData();
            if(spx == null)
                continue;
            Log.e("Tracker", "后：" + spx.length);
            audioTrack.write(spx, 0, spx.length);
        }
    }

    @Override
    public void free() {
        audioTrack.stop();
        audioTrack.release();
        audioTrack = null;
    }
}
