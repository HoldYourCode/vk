package com.holdyourcolour.myvk.threads;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;

import com.holdyourcolour.myvk.api.RequestCompleted;
import com.holdyourcolour.myvk.api.VKApi;
import com.holdyourcolour.myvk.api.VKApiConst;
import com.holdyourcolour.myvk.model.Dialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HoldYourColour on 09.04.2017.
 */

public class FetchPhotoURL extends AsyncTask<String, Void, String> {
    private static final String TAG = FetchPhotoURL.class.getSimpleName();
    private Fragment mFragment;
    private int mPosition;

    public FetchPhotoURL(Fragment fragment, int position){
        mFragment = fragment;
        mPosition = position;
    }
    @Override
    protected String doInBackground(String... params) {
        String userId = params[0];
        String photoURL = null;
        try {
            String result = VKApi.getInstance(mFragment.getContext()).getProfilePhoto(userId, VKApiConst.PHOTO_50);
            JSONObject jsonObj = new JSONObject(result);
            JSONArray jsonResponseArray = jsonObj.getJSONArray("response");
            JSONObject userData = jsonResponseArray.getJSONObject(0);
            photoURL = userData.getString("photo_50");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return photoURL;
    }

    @Override
    protected void onPostExecute(String photoURL) {
        super.onPostExecute(photoURL);
        Map<String, String> map = new HashMap<>();
        map.put("position", String.valueOf(mPosition));
        map.put("photo_url", photoURL);
        ((RequestCompleted)mFragment).onFinish(map);
    }
}
