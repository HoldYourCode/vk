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
import android.widget.ImageView;

import com.holdyourcolour.myvk.R;
import com.holdyourcolour.myvk.api.RequestCompleted;
import com.holdyourcolour.myvk.api.VKApiPhotosExtension;
import com.holdyourcolour.myvk.controller.adapters.DialogAdapter;
import com.holdyourcolour.myvk.controller.adapters.MessageAdapter;
import com.holdyourcolour.myvk.model.Dialog;
import com.holdyourcolour.myvk.model.Message;
import com.holdyourcolour.myvk.threads.FetchDialogs;
import com.holdyourcolour.myvk.view.activities.MainActivity;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiDialog;
import com.vk.sdk.api.model.VKApiGetDialogResponse;
import com.vk.sdk.api.model.VKList;
import com.vk.sdk.api.model.VKPhotoArray;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by uadn_mei on 2/27/17.
 */

public class DialogsListFragment extends Fragment implements RequestCompleted{

    private static final String TAG = DialogsListFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private DialogAdapter mDialogAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialogs_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        new FetchDialogs(this).execute();
        return view;
    }



    @Override
    public void onFinish(Object response) {
        Log.d(TAG, "onFinish");
        List<Dialog> dialogList = (List<Dialog>) response;
        mDialogAdapter = new DialogAdapter(this, dialogList);
        mRecyclerView.setAdapter(mDialogAdapter);
    }
}
