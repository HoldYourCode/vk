package com.holdyourcolour.myvk.api;

import android.content.Context;
import android.util.Log;

import com.holdyourcolour.myvk.database.VKSession;
import com.vk.sdk.VKScope;
import com.vk.sdk.api.VKResponse;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by HoldYourColour on 09.04.2017.
 */

public class VKApi {
    private static final String TAG = VKApi.class.getSimpleName();
    private static VKApi sInstance;
    private OkHttpClient mClient;
    private VKSession mVKSession;
    public static final String[] SCOPE = {
            VKScope.FRIENDS,
            VKScope.MESSAGES,
            VKScope.AUDIO,
            VKScope.PHOTOS,
            VKScope.VIDEO,
            VKScope.NOTIFICATIONS,
            VKScope.WALL
    };
    private static final String API_URL = "https://api.vk.com/method/";

    public static VKApi getInstance(Context context){
        if (sInstance == null){
            sInstance = new VKApi(context);
        }
        return sInstance;
    }

    private VKApi(Context context){
        mClient = new OkHttpClient();
        mVKSession = VKSession.getInstance(context);
    }

    public String getDialogs(String count) throws IOException {
        Log.d(TAG,"getDialogs REQUEST :\n" + API_URL + "messages.getDialogs?" +
                "count=" + count+ "&access_token=" + mVKSession.getAccessToken());
        Request request = new Request.Builder().url(API_URL + "messages.getDialogs?" +
                "count=" + count + "&access_token=" + mVKSession.getAccessToken()).build();
        Response response = mClient.newCall(request).execute();
        String result = response.body().string();
        Log.d(TAG, "getDialogs RESPONSE :\n" + result);
        return result;
    }

    public String getDialogs() throws IOException {
        return getDialogs("25");
    }

    public String getProfilePhoto(String userId, String photoSize) throws IOException {
        Log.d(TAG,"getProfilePhoto REQUEST :\n" + API_URL + "users.get?" + "user_ids=" + userId +
                "fields=" + photoSize + "&access_token=" + mVKSession.getAccessToken());
        Request request = new Request.Builder().url(API_URL + "users.get?" + "user_ids=" + userId +
                "fields=" + photoSize + "&access_token=" + mVKSession.getAccessToken()).build();
        Response response = mClient.newCall(request).execute();
        String result = response.body().string();
        Log.d(TAG, "getProfilePhoto RESPONSE :\n" + result);
        return result;
    }
}
