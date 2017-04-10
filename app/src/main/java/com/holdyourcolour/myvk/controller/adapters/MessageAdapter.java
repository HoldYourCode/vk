package com.holdyourcolour.myvk.controller.adapters;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.holdyourcolour.myvk.R;
import com.holdyourcolour.myvk.api.VKApiPhotosExtension;
import com.holdyourcolour.myvk.model.Message;
import com.squareup.picasso.Picasso;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.methods.VKApiUsers;
import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKList;
import com.vk.sdk.api.model.VKPhotoArray;

import java.util.List;

/**
 * Created by uadn_mei on 2/27/17.
 */

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = MessageAdapter.class.getSimpleName();
    private List<Message> mMessages;
    private FragmentActivity mContext;

    // Provide a suitable constructor (depends on the kind of dataset)
    public MessageAdapter(FragmentActivity context, List<Message> messages) {
        mContext = context;
        mMessages = messages;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public  RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0: return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_from, parent, false));
            case 1: return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_to, parent, false));
            default: return new ViewHolder(new View(mContext));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(mMessages.get(position).getOut())
            return 1;
        return 0;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder)holder;
        Message message = mMessages.get(position);
        int userId = mMessages.get(position).getUserId();
        PhotoListener listener = new PhotoListener(viewHolder);
        getAvatar(userId, listener);
        //Picasso.with(mContext).load(getAvatar(userId)).into(viewHolder.mAvatarImageView);
        viewHolder.mMessageTextView.setText(message.getBody());
    }

    class PhotoListener {
        ViewHolder holder;
        PhotoListener(ViewHolder holder){
            this.holder = holder;
        }
        public String onResponse(String photoUrl){
            Picasso.with(mContext).load(photoUrl).into(holder.mAvatarImageView);
            return photoUrl;
        }
    }

    private void getAvatar(final int userId, final PhotoListener listener) {
        VKRequest requestPhoto = new VKApiPhotosExtension().getPhotos(VKParameters.from(VKApiConst.OWNER_ID,
                userId, VKApiConst.ALBUM_ID, "profile"));
        requestPhoto.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                Log.d(TAG, "getAvatar() onComplete() response = " + response);
                VKPhotoArray avataraArray = (VKPhotoArray) response.parsedModel;
                Log.d("PHOTO", avataraArray.get(avataraArray.size()-1).photo_75);
                listener.onResponse(avataraArray.get(avataraArray.size()-1).photo_75);
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
                Log.d(TAG, "getAvatar() onError() error = " + error);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView mMessageTextView;
        ImageView mAvatarImageView;
        ViewHolder(View v) {
            super(v);
            mMessageTextView = (TextView) v.findViewById(R.id.message_body);
            mAvatarImageView = (ImageView) v.findViewById(R.id.avatar_message);
        }
    }
}

