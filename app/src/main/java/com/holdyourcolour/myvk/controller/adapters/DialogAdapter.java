package com.holdyourcolour.myvk.controller.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.holdyourcolour.myvk.R;
import com.holdyourcolour.myvk.api.RequestCompleted;
import com.holdyourcolour.myvk.api.VKApiPhotosExtension;
import com.holdyourcolour.myvk.model.Dialog;
import com.holdyourcolour.myvk.threads.FetchPhotoURL;
import com.holdyourcolour.myvk.view.fragments.MessagesListFragment;
import com.squareup.picasso.Picasso;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKPhotoArray;

import java.util.List;
import java.util.Map;

/**
 * Created by uadn_mei on 2/27/17.
 */

public class DialogAdapter extends RecyclerView.Adapter<DialogAdapter.ViewHolder>  implements RequestCompleted{
    private static final String TAG = DialogAdapter.class.getSimpleName();
    private List<Dialog> mDialogs;
    private Fragment mFragment;

    // Provide a suitable constructor (depends on the kind of dataset)
    public DialogAdapter(Fragment context, List<Dialog> dialogs) {
        mFragment = context;
        mDialogs = dialogs;
    }

    public Context getContext(){
        return mFragment.getContext();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DialogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialog, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Dialog dialog = mDialogs.get(position);
        holder.mDialogTitleTextView.setText(dialog.getMessage().getTitle());
        holder.mDialogMessageTextView.setText(dialog.getMessage().getBody());
        if (dialog.getCountUnreadMessages() == 0){
            holder.mCountUnreadMessagesTextView.setVisibility(View.GONE);
        } else {
            holder.mCountUnreadMessagesTextView.setText("" + dialog.getCountUnreadMessages());
        }
        if (!dialog.getMessage().getReadState()){
            holder.mDialogMessageTextView.setBackgroundColor(Color.parseColor("#80222222"));
        }
        String userId = String.valueOf(mDialogs.get(position).getMessage().getUserId());
        if (dialog.getPhotoUrl() != null){
            Picasso.with(mFragment.getContext()).load(dialog.getPhotoUrl()).into(holder.mDialogAvatarImageView);
        } else {
            new FetchPhotoURL(mFragment, position).execute(userId);
        }
        //PhotoListener listener = new DialogAdapter.PhotoListener(holder);

        //getAvatar(userId, listener);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DialogAdapter", "on view click position = " + position);
                goToMessages(position);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDialogs.size();
    }

    @Override
    public void onFinish(Object response) {
        Map<String, String> map = (Map<String, String>)response;
        int position = Integer.parseInt(map.get("position"));
        String photoUrl = map.get("photo_url");
        mDialogs.get(position).setPhotoUrl(photoUrl);
        notifyItemChanged(position);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView mDialogTitleTextView, mDialogMessageTextView, mCountUnreadMessagesTextView;
        ImageView mDialogAvatarImageView;
        ViewHolder(View v) {
            super(v);
            mDialogTitleTextView = (TextView) v.findViewById(R.id.dialog_title_textview);
            mDialogMessageTextView = (TextView) v.findViewById(R.id.dialog_message_textview);
            mDialogAvatarImageView = (ImageView) v.findViewById(R.id.avatar_dialog_imageview);
            mCountUnreadMessagesTextView = (TextView) v.findViewById(R.id.count_unread_messages_textview);
        }
    }



    private void goToMessages(int position) {
        Log.d("DialogAdapter", "goToMessages position = " + position);
        int userId = mDialogs.get(position).getMessage().getUserId();
        Log.d("DialogAdapter", "goToMessages userId = " + userId);

        mFragment.getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, MessagesListFragment.newInstance(userId)).addToBackStack(null).commit();
    }
}
