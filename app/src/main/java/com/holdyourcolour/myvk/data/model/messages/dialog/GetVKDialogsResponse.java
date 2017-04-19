package com.holdyourcolour.myvk.data.model.messages.dialog;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by HoldYourColour on 17.04.2017.
 */

public class GetVKDialogsResponse {
    @SerializedName("response")
    @Expose
    private VKDialogsResponse response;

    public VKDialogsResponse getResponse() {
        return response;
    }

    public void setResponse(VKDialogsResponse response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "GetDialogsResponse{" +
                "response=" + response +
                '}';
    }
}
