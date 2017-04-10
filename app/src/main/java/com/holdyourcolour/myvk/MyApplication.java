package com.holdyourcolour.myvk;

import android.app.Application;

import com.vk.sdk.VKSdk;

/**
 * Created by HoldYourColour on 26.02.2017.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(getApplicationContext());
    }
}
