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
import android.widget.Button;
import android.widget.EditText;

import com.holdyourcolour.myvk.R;
import com.holdyourcolour.myvk.api.VKApiMessagesExtension;
import com.holdyourcolour.myvk.controller.adapters.MessageAdapter;
import com.holdyourcolour.myvk.model.Message;
import com.holdyourcolour.myvk.view.activities.MainActivity;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiGetMessagesResponse;
import com.vk.sdk.api.model.VKApiMessage;
import com.vk.sdk.api.model.VKList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uadn_mei on 3/1/17.
 */
public class MessagesListFragment  extends Fragment{

    private static final String TAG = MessagesListFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private MessageAdapter mMessagesAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int mUserId;


    public static MessagesListFragment newInstance(int userId) {
        MessagesListFragment f = new MessagesListFragment();
        Bundle args = new Bundle();
        args.putInt("user_id", userId);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserId = getArguments().getInt("user_id", 0);
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
        mRecyclerView.setLayoutManager(mLayoutManager);
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
        VKRequest messagesRequest = new VKApiMessagesExtension().getHistory(VKParameters.from(VKApiConst.USER_ID, String.valueOf(mUserId)));
        messagesRequest.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                Log.d(TAG, "getMessageByUserID() onComplete() response = " + response);
                VKApiGetMessagesResponse getMessagesResponse = (VKApiGetMessagesResponse) response.parsedModel;
                Log.d(TAG, "Messages with : " + mUserId + " getMessagesResponse count = " + getMessagesResponse.count);
                Log.d(TAG, "Messages with : " + mUserId + " getMessagesResponse items = " + getMessagesResponse.items.size());
                VKList<VKApiMessage> messagesList = getMessagesResponse.items;
                List<Message> messages = new ArrayList<>();
                for (VKApiMessage message : messagesList){
                    Log.d(TAG, "user:" + mUserId + " message: " + message.body);
                    //Message msg = Message.parseMessage(message);
                    //messages.add(msg);
                }
                mMessagesAdapter = new MessageAdapter(getActivity(), messages);
                mRecyclerView.setAdapter(mMessagesAdapter);
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
                Log.d(TAG, "getMessageByUserID() onError() error = " + error);
            }
        });
    }

}
