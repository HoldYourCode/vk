package com.holdyourcolour.myvk.data.remote;

import com.vk.sdk.VKScope;

/**
 * Created by HoldYourColour on 17.04.2017.
 */

public class ApiUtils {
    public static final String ENDPOINT = "https://api.vk.com/method/";
    public static final String API_VERSION = "5.63";
    public static final String[] SCOPE = {
            VKScope.FRIENDS,
            VKScope.MESSAGES,
            VKScope.AUDIO,
            VKScope.PHOTOS,
            VKScope.VIDEO,
            VKScope.NOTIFICATIONS,
            VKScope.WALL
    };

    public static VKApi getVKApiService(){
        return RetrofitClient.getClient(ENDPOINT).create(VKApi.class);
    }
}
