package com.holdyourcolour.myvk.controller.adapters;

import android.graphics.Bitmap;
import android.graphics.Color;
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
import com.holdyourcolour.myvk.data.model.messages.dialog.VKDialog;
import com.holdyourcolour.myvk.data.model.users.GetUserInfoResponse;
import com.holdyourcolour.myvk.data.model.users.UserInfo;
import com.holdyourcolour.myvk.data.remote.ApiUtils;
import com.holdyourcolour.myvk.data.remote.VKApi;
import com.holdyourcolour.myvk.data.remote.VKApiConst;
import com.holdyourcolour.myvk.database.VKSession;
import com.holdyourcolour.myvk.view.fragments.MessagesListFragment;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by uadn_mei on 2/27/17.
 */

public class DialogAdapter extends RecyclerView.Adapter<DialogAdapter.ViewHolder> {
    private static final String TAG = DialogAdapter.class.getSimpleName();
    private List<VKDialog> mDialogs;
    private Fragment mFragment;
    private VKApi mVKApiService;
    private VKSession mVKSession;

    public DialogAdapter(Fragment fragment, List<VKDialog> dialogs) {
        mFragment = fragment;
        mDialogs = dialogs;
        mVKApiService = ApiUtils.getVKApiService();
        mVKSession = VKSession.getInstance(fragment.getContext());
    }

    public void updateDialogs(List<VKDialog> dialogs){
        mDialogs = dialogs;
        notifyDataSetChanged();
    }

    @Override
    public DialogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialog, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final VKDialog dialog = mDialogs.get(position);
        holder.mDialogTitleTextView.setText(dialog.getMessage().getTitle());
        holder.mDialogMessageTextView.setText(dialog.getMessage().getBody());
        // reset image because of mistake display
        holder.mDialogAvatarImageView.setImageDrawable(null);
        if (dialog.getUnread() == 0){
            holder.mCountUnreadMessagesTextView.setVisibility(View.GONE);
        } else {
            holder.mCountUnreadMessagesTextView.setVisibility(View.VISIBLE);
            String countUnreadMessages = "" + dialog.getUnread();
            holder.mCountUnreadMessagesTextView.setText(countUnreadMessages);
        }
        if (dialog.getMessage().getReadState() == 0){ // 0 - not read message, 1 - read
            holder.mDialogMessageTextView.setBackgroundColor(Color.parseColor("#80222222"));
        } else {
            holder.mDialogMessageTextView.setBackgroundColor(mFragment.getResources().getColor(android.R.color.transparent));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DialogAdapter", "on view click position = " + holder.getAdapterPosition());
                goToMessages(holder.getAdapterPosition());
            }
        });
        Log.d("DialogAdapter", "onBindViewHolder adapterPosition = " + holder.getAdapterPosition() + "/ position = " + position);
        if (dialog.getPhotoURL() == null) {
            requestUserPhoto(position);
        } else {
            Picasso.with(mFragment.getContext())
                    .load(dialog.getPhotoURL())
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded (final Bitmap bitmap, Picasso.LoadedFrom from){
                        /* Save the bitmap or do something with it here */

                            //Set it in the ImageView
                            holder.mDialogAvatarImageView.setImageBitmap(bitmap);
                            dialog.setPhoto(bitmap);
                        }
                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }
                    });
        }
    }

    private void requestUserPhoto(final int itemPosition) {
        String userId = mDialogs.get(itemPosition).getMessage().getUserId();
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
                    mDialogs.get(itemPosition).setPhotoURL(user.getPhoto100());
                    notifyItemChanged(itemPosition);
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
    public int getItemCount() {
        if (mDialogs == null)
            return 0;
        return mDialogs.size();
    }

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
        String userId = mDialogs.get(position).getMessage().getUserId();
        Log.d("DialogAdapter", "goToMessages userId = " + userId);
        Bitmap userPhoto = mDialogs.get(position).getPhoto();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        userPhoto.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        mFragment.getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, MessagesListFragment.newInstance(userId, byteArray)).addToBackStack(null).commit();
    }
}
