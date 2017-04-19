package com.holdyourcolour.myvk.view.activities;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.holdyourcolour.myvk.R;
import com.holdyourcolour.myvk.data.model.users.GetUserInfoResponse;
import com.holdyourcolour.myvk.data.model.users.UserInfo;
import com.holdyourcolour.myvk.data.remote.ApiUtils;
import com.holdyourcolour.myvk.data.remote.VKApi;
import com.holdyourcolour.myvk.data.remote.VKApiConst;
import com.holdyourcolour.myvk.database.VKSession;
import com.holdyourcolour.myvk.view.fragments.DialogsListFragment;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private VKSession mVKSession;
    private VKApi mVKApiService;

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
            requestOwnerPhoto();
        }

        @Override
        public void onError(VKError error) {
            Log.d(TAG, "Login onError() error = " + error);
        }
    };

    private void requestOwnerPhoto() {
        String userId = mVKSession.getOwnerId();
        mVKApiService.getUserInfo(userId, VKApiConst.PHOTO_100, mVKSession.getAccessToken(), ApiUtils.API_VERSION).enqueue(new Callback<GetUserInfoResponse>() {
            @Override
            public void onResponse(Call<GetUserInfoResponse> call, Response<GetUserInfoResponse> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "getUserPhoto RESPONSE: " + response.toString());
                    List<UserInfo> userInfoList = response.body().getResponse();
                    if (userInfoList == null || userInfoList.size() == 0){
                        return;
                    }
                    UserInfo user = userInfoList.get(0);
                    Log.d(TAG, " " + user);
                    mVKSession.setOwnerPhotoURL(user.getPhoto100());
                } else {
                    int status = response.code();
                    Log.d(TAG, "getUserPhoto status = " + status);
                }
            }

            @Override
            public void onFailure(Call<GetUserInfoResponse> call, Throwable t) {
                Log.e(TAG, "getUserPhoto onFailure", t);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*String[] fingerprints = VKUtil.getCertificateFingerprint(this, this.getPackageName());
        Log.d(TAG, "fingerPrints = " + fingerprints[0]);
        Log.d(TAG, "VKSdk.getApiVersion = " + VKSdk.getApiVersion());*/
        mVKSession = VKSession.getInstance(this);
        mVKApiService = ApiUtils.getVKApiService();
        VKSdk.login(this, ApiUtils.SCOPE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, mAccessTokenCallBack))
            super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0)
            super.onBackPressed();
        else
            getSupportFragmentManager().popBackStack();

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
