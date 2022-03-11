package com.meonjicompany.planning.kakao;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class KakaoAPIConnection extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        KakaoSdk.init(this,"3eabcf626d06674038a715ba330c681f");
    }
}