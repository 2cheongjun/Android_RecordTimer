package com.example.myapplication.intro;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class KakaoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        KakaoSdk.init(this,"cf847b6d619a3fe4180f322a4823139e");
    }
}
