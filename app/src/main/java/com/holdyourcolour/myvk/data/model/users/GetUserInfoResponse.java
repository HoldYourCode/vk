package com.holdyourcolour.myvk.data.model.users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by HoldYourColour on 17.04.2017.
 */

public class GetUserInfoResponse {
    @SerializedName("response")
    @Expose
    private List<UserInfo> response = null;

    public List<UserInfo> getResponse() {
        return response;
    }

    public void setResponse(List<UserInfo> response) {
        this.response = response;
    }

}
