package com.holdyourcolour.myvk.database;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by HoldYourColour on 09.04.2017.
 */

public class VKSession {
    private static VKSession sInstance;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private static final String SHARED = "VK_Preferences";
    private static final String OWNER_ID = "username";
    private static final String ACCESS_TOKEN = "access_token";

    public static VKSession getInstance(Context context){
        if (sInstance == null){
            sInstance = new VKSession(context);
        }
        return sInstance;
    }

    private VKSession(Context context){
        mSharedPreferences = context.getSharedPreferences(SHARED, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public void storeAccessToken(String accessToken, String ownerId){
        mEditor.putString(ACCESS_TOKEN, accessToken);
        mEditor.putString(OWNER_ID, ownerId);
        mEditor.commit();
    }

    /**
     * Get username
     * @return User name
     */
    public String getOwnerId(){
        return mSharedPreferences.getString(OWNER_ID, null);
    }

    /**
     * Get access_token
     * @return access token
     */
    public String getAccessToken(){
        return mSharedPreferences.getString(ACCESS_TOKEN, null);
    }

    /**
     * Reset access_token
     */
    public void resetAccessToken(){
        mEditor.putString(ACCESS_TOKEN, null);
        mEditor.putString(OWNER_ID, null);
        mEditor.commit();
    }

}
