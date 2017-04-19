package com.holdyourcolour.myvk.controller.adapters;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.holdyourcolour.myvk.R;
import com.holdyourcolour.myvk.data.model.messages.message.VKMessage;
import com.holdyourcolour.myvk.database.VKSession;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.Collections;
import java.util.List;

/**
 * Created by uadn_mei on 2/27/17.
 */

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = MessageAdapter.class.getSimpleName();
    private List<VKMessage> mMessages;
    private Fragment mFragment;
    private Bitmap mUserPhoto;
    private Bitmap mOwnerPhoto;
    private VKSession mVKSession;

    public MessageAdapter(Fragment fragment, List<VKMessage> messages, Bitmap userPhoto) {
        mVKSession = VKSession.getInstance(fragment.getContext());
        mFragment = fragment;
        // reverse list
        Collections.reverse(messages);
        mMessages = messages;
        mUserPhoto = userPhoto;
        Picasso.with(fragment.getContext()).load(mVKSession.getUserPhotoUrl()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                mOwnerPhoto = bitmap;
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }

    @Override
    public  RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            // from user
            case 0: return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_from, parent, false));
            // from me
            case 1: return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_to, parent, false));
            default: return new ViewHolder(new View(mFragment.getContext()));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(mMessages.get(position).getOut() == 1)
            return 1;
        return 0;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder)holder;
        VKMessage message = mMessages.get(position);
        viewHolder.mMessageTextView.setText(message.getBody());
        if(getItemViewType(position) == 0){
            Log.d(TAG, "user photo");
            viewHolder.mAvatarImageView.setImageBitmap(mUserPhoto);
        } else {
            Log.d(TAG, "user photo else ");
            viewHolder.mAvatarImageView.setImageBitmap(mOwnerPhoto);
        }
    }



    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mMessageTextView;
        ImageView mAvatarImageView;
        ViewHolder(View v) {
            super(v);
            mMessageTextView = (TextView) v.findViewById(R.id.message_body);
            mAvatarImageView = (ImageView) v.findViewById(R.id.avatar_message);
        }
    }
}

