package com.holdyourcolour.myvk.view.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.holdyourcolour.myvk.R;
import com.holdyourcolour.myvk.controller.adapters.MessageAdapter;
import com.holdyourcolour.myvk.data.model.messages.message.GetVKMessagesResponse;
import com.holdyourcolour.myvk.data.model.messages.message.VKMessagesResponse;
import com.holdyourcolour.myvk.data.remote.ApiUtils;
import com.holdyourcolour.myvk.data.remote.VKApi;
import com.holdyourcolour.myvk.database.VKSession;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by uadn_mei on 3/1/17.
 */
public class MessagesListFragment  extends Fragment{

    private static final String TAG = MessagesListFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private MessageAdapter mMessagesAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String mUserId;
    private Bitmap mPhoto;
    private VKApi mVKApiService;
    private VKSession mVKSession;


    public static MessagesListFragment newInstance(String userId, byte[] photo) {
        MessagesListFragment f = new MessagesListFragment();
        Bundle args = new Bundle();
        args.putString("user_id", userId);
        args.putByteArray("photo", photo);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserId = getArguments().getString("user_id");
            byte[] photoArray = getArguments().getByteArray("photo");
            mPhoto = BitmapFactory.decodeByteArray(photoArray, 0, photoArray.length);
        }
        Log.d(TAG, "onCreate() userId=" + mUserId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");
        View view = inflater.inflate(R.layout.fragment_messages_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        ((LinearLayoutManager)mLayoutManager).setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mVKApiService = ApiUtils.getVKApiService();
        mVKSession = VKSession.getInstance(getContext());
        getMessageByUserID();
        final EditText sendMessageEditText = (EditText) view.findViewById(R.id.sendMessageEditText);
        Button sendMessageButton = (Button) view.findViewById(R.id.sendMessageButton);
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VKRequest sendMessageRequest = new VKRequest("messages.send", VKParameters.from(VKApiConst.USER_ID, mUserId,
                        VKApiConst.MESSAGE, sendMessageEditText.getText().toString()));
                sendMessageRequest.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);
                        sendMessageEditText.getText().clear();
                        getMessageByUserID();
                        Log.d(TAG, "sendMessageRequest() onComplete() " + response);
                    }

                    @Override
                    public void onError(VKError error) {
                        super.onError(error);
                        Log.d(TAG, "sendMessageRequest() onError() " + error);
                    }
                });
            }
        });
        return view;
    }


    private void getMessageByUserID() {
        Log.d(TAG, "getMessageByUserID() " + mUserId);
        mVKApiService.getMessages("25", mUserId, mVKSession.getAccessToken(), ApiUtils.API_VERSION).enqueue(new Callback<GetVKMessagesResponse>() {
            @Override
            public void onResponse(Call<GetVKMessagesResponse> call, Response<GetVKMessagesResponse> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "getMessages RESPONSE: " + response.toString());
                    VKMessagesResponse messagesResponse = response.body().getResponse();
                    Log.d(TAG, "messagesResponse = " + messagesResponse);
                    mMessagesAdapter = new MessageAdapter(MessagesListFragment.this, messagesResponse.getItems(), mPhoto);
                    mRecyclerView.setAdapter(mMessagesAdapter);
                } else {
                    int statusCode = response.code();
                    Log.d(TAG, "getDialogs RESPONSE: status = " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<GetVKMessagesResponse> call, Throwable t) {
                Log.e(TAG, "onFailure:" + call.toString(), t);
            }
        });

    }

}
