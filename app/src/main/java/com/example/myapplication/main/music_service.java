package com.example.myapplication.main;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.myapplication.R;


// 서비스
// onStartCommand를 구현해서 음악을 실행할 수 있도록 한다.
public class music_service extends Service {

    private MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        android.util.Log.i("뮤직서비스 테스트", "onCreate()");
        mediaPlayer = MediaPlayer.create(this, R.raw.sunset);
        mediaPlayer.setLooping(true);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        android.util.Log.i("뮤직서비스 테스트", "onDestroy()");
        // 음악중지
        super.onDestroy();
        mediaPlayer.stop();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        android.util.Log.i("뮤직서비스 테스트", "onStartCommand()");
        // 음악시작 및 반복

        mediaPlayer.start();
        mediaPlayer.setLooping(true);
        // 서비스가 종료됬을때도 다시 자동으로 실행해주기
        if (intent == null) {
            return Service.START_STICKY;
        }

        return super.onStartCommand(intent, flags, startId);
    }

}

