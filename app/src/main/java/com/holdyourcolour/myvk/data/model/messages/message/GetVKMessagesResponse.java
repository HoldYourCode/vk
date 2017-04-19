package com.holdyourcolour.myvk.data.model.messages.message;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by HoldYourColour on 17.04.2017.
 */

public class GetVKMessagesResponse {
    @SerializedName("response")
    @Expose
    private VKMessagesResponse response;

    public VKMessagesResponse getResponse() {
        return response;
    }

    public void setResponse(VKMessagesResponse response) {
        this.response = response;
    }

}
