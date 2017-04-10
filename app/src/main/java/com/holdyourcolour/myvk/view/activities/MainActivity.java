package com.holdyourcolour.myvk.view.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.holdyourcolour.myvk.R;
import com.holdyourcolour.myvk.api.VKApi;
import com.holdyourcolour.myvk.database.VKSession;
import com.holdyourcolour.myvk.view.fragments.DialogsListFragment;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;
import com.vk.sdk.util.VKUtil;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private VKSession mVKSession;

    private VKCallback<VKAccessToken> mAccessTokenCallBack = new VKCallback<VKAccessToken>() {

        @Override
        public void onResult(VKAccessToken res) {
            mVKSession.storeAccessToken(res.accessToken, res.userId);
            Log.d(TAG, "Login onResult() token = " + res.accessToken);
            // start dialog fragment
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DialogsListFragment())
                    .addToBackStack(null)
                    .commit();
        }

        @Override
        public void onError(VKError error) {
            Log.d(TAG, "Login onError() error = " + error);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*String[] fingerprints = VKUtil.getCertificateFingerprint(this, this.getPackageName());
        Log.d(TAG, "fingerPrints = " + fingerprints[0]);
        Log.d(TAG, "VKSdk.getApiVersion = " + VKSdk.getApiVersion());*/
        mVKSession = VKSession.getInstance(this);
        VKSdk.login(this, VKApi.SCOPE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, mAccessTokenCallBack))
            super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0)
            super.onBackPressed();
        else
            getSupportFragmentManager().popBackStack();

    }
}
