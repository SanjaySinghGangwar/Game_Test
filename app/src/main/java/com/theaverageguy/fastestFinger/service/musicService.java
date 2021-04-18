package com.theaverageguy.fastestFinger.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.theaverageguy.fastestFinger.R;

public class musicService extends Service {

    MediaPlayer mMediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.music);
        mMediaPlayer.setLooping(true);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        if (!mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
        }
    }

    @Override
    public void onDestroy() {
        mMediaPlayer.stop();
    }
}
