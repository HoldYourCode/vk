package com.holdyourcolour.myvk.data.remote;

import com.holdyourcolour.myvk.data.model.messages.dialog.GetVKDialogsResponse;
import com.holdyourcolour.myvk.data.model.messages.message.GetVKMessagesResponse;
import com.holdyourcolour.myvk.data.model.users.GetUserInfoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by HoldYourColour on 17.04.2017.
 */

public interface VKApi {
    String METHOD_GET_DIALOGS = "messages.getDialogs?";

    @GET("messages.getDialogs?")
    Call<GetVKDialogsResponse> getDialogs(@Query("count")String count,
                                          @Query("access_token")String accessToken,
                                          @Query("v") String version);
    @GET("users.get?")
    Call<GetUserInfoResponse> getUserInfo(@Query("user_ids") String userId,
                                          @Query("fields") String photoSize,
                                          @Query("access_token") String accessToken,
                                          @Query("v") String version);

    @GET("messages.getHistory?")
    Call<GetVKMessagesResponse> getMessages(@Query("count") String count,
                                            @Query("user_id") String userId,
                                            @Query("access_token")String accessToken,
                                            @Query("v")String version);
}
