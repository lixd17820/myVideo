package com.ntjxj.data;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by lixiaodong on 2018/2/23.
 */

public class MessageQueue {

    private static MessageQueue[] messageQueue = new MessageQueue[4];

    private BlockingQueue<AudioData> audioDataQueue = null;

    private MessageQueue() {
        audioDataQueue = new LinkedBlockingQueue<>();
    }

    @Retention(SOURCE)
    @IntDef({ENCODER_DATA_QUEUE, SENDER_DATA_QUEUE, DECODER_DATA_QUEUE, TRACKER_DATA_QUEUE})
    public @interface DataQueueType {
    }

    public static final int ENCODER_DATA_QUEUE = 0;
    public static final int SENDER_DATA_QUEUE = 1;
    public static final int DECODER_DATA_QUEUE = 2;
    public static final int TRACKER_DATA_QUEUE = 3;

    public static MessageQueue getInstance(@DataQueueType int type) {
        if(messageQueue[type] == null){
            messageQueue[type] = new MessageQueue();
        }
        return messageQueue[type];
    }

    public void put(AudioData audioData) {
        try {
            audioDataQueue.put(audioData);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public AudioData take() {
        try {
            return audioDataQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getSize() {
        return audioDataQueue.size();
    }

    public synchronized void clear() {
        audioDataQueue.clear();
    }
}
