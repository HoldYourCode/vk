package com.holdyourcolour.myvk.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.holdyourcolour.myvk.R;
import com.holdyourcolour.myvk.controller.adapters.DialogAdapter;
import com.holdyourcolour.myvk.data.model.messages.dialog.VKDialogsResponse;
import com.holdyourcolour.myvk.data.model.messages.dialog.GetVKDialogsResponse;
import com.holdyourcolour.myvk.data.remote.ApiUtils;
import com.holdyourcolour.myvk.data.remote.VKApi;
import com.holdyourcolour.myvk.database.VKSession;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by uadn_mei on 2/27/17.
 */

public class DialogsListFragment extends Fragment {

    private static final String TAG = DialogsListFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private DialogAdapter mDialogAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private VKApi mVKApiService;
    private VKSession mVKSession;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialogs_list, container, false);
        mVKApiService = ApiUtils.getVKApiService();
        mVKSession = VKSession.getInstance(getContext());
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        requestDialogs();
        //new FetchDialogs(this).execute();
        return view;
    }

    private void requestDialogs() {
        Log.d(TAG, "GET DIALOGS REQUEST :\n" + ApiUtils.ENDPOINT + VKApi.METHOD_GET_DIALOGS +
                "count=" + "2" + "&access_token=" + mVKSession.getAccessToken() + "&v=" + ApiUtils.API_VERSION);
        mVKApiService.getDialogs("25", mVKSession.getAccessToken(), ApiUtils.API_VERSION).enqueue(new Callback<GetVKDialogsResponse>() {
            @Override
            public void onResponse(Call<GetVKDialogsResponse> call, Response<GetVKDialogsResponse> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "getDialogs RESPONSE: " + response.toString());
                    VKDialogsResponse dialogsResponse = response.body().getResponse();
                    Log.d(TAG, "dialogResponse = " + dialogsResponse);
                    mDialogAdapter = new DialogAdapter(DialogsListFragment.this, dialogsResponse.getDialogs());
                    mRecyclerView.setAdapter(mDialogAdapter);
                } else {
                    int statusCode = response.code();
                    Log.d(TAG, "getDialogs RESPONSE: status = " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<GetVKDialogsResponse> call, Throwable t) {
                Log.e(TAG, "onFailure:" + call.toString(), t);
            }
        });
    }
}
