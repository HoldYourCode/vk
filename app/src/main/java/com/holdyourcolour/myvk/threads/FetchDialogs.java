package com.holdyourcolour.myvk.threads;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.holdyourcolour.myvk.api.RequestCompleted;
import com.holdyourcolour.myvk.api.VKApi;
import com.holdyourcolour.myvk.model.Dialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HoldYourColour on 09.04.2017.
 */

public class FetchDialogs extends AsyncTask<Void, Void, List<Dialog>> {
    private static final String TAG = FetchDialogs.class.getSimpleName();
    private Fragment mFragment;

    public FetchDialogs(Fragment fragment){
        mFragment = fragment;
    }
    @Override
    protected List<Dialog> doInBackground(Void... params) {
        List<Dialog> dialogs = null;
        try {
            dialogs = new ArrayList<>();
            String result = VKApi.getInstance(mFragment.getContext()).getDialogs();
            JSONObject jsonObj = new JSONObject(result);
            JSONArray dialogsJSONArray = jsonObj.getJSONArray("response");
            for (int i = 1; i < dialogsJSONArray.length(); i++){
                JSONObject dialogJSON = dialogsJSONArray.getJSONObject(i);
                Dialog dialog = Dialog.parseDialog(dialogJSON);
                Log.d(TAG, "" + dialog);
                dialogs.add(dialog);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dialogs;
    }

    @Override
    protected void onPostExecute(List<Dialog> list) {
        super.onPostExecute(list);
        ((RequestCompleted)mFragment).onFinish(list);
    }
}
