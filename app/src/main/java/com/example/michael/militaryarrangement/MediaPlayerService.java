package com.example.michael.militaryarrangement;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Class MediaPlayerService is the class that defines the MediaPlayerService in application///
 */

public class MediaPlayerService extends Service {
    private MediaPlayer mediaPlayer ;
    @Override
    public IBinder onBind (Intent intent)
    {
        return null ;
    }

    @Override
    public int onStartCommand (Intent intent, int flags, int startId) {
        mediaPlayer = MediaPlayer.create(this , R.raw.heroine_2017);
        mediaPlayer.setLooping(false);
        mediaPlayer.start();
        return START_STICKY ;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }

}
