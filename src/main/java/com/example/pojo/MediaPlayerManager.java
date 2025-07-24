package com.example.pojo;

import android.media.MediaPlayer;

public class MediaPlayerManager {
    private static MediaPlayer mediaPlayer;
    private static MediaPlayerManager instance;

    // 私有构造函数
    private MediaPlayerManager() {}

    // 获取单例实例
    public static MediaPlayerManager getInstance() {
        if (instance == null) {
            instance = new MediaPlayerManager();
        }
        return instance;
    }

    // 获取MediaPlayer实例
    public MediaPlayer getMediaPlayer() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        return mediaPlayer;
    }

    // 释放MediaPlayer资源
    public void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    // 重置MediaPlayer
    public void resetMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.reset();
        }
    }
}
