package com.holdyourcolour.myvk.data.model.messages.dialog;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by HoldYourColour on 17.04.2017.
 */

public class VKDialogsResponse {
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("unread_dialogs")
    @Expose
    private Integer unreadDialogs;
    @SerializedName("items")
    @Expose
    private List<VKDialog> dialogs = null;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getUnreadDialogs() {
        return unreadDialogs;
    }

    public void setUnreadDialogs(Integer unreadDialogs) {
        this.unreadDialogs = unreadDialogs;
    }

    public List<VKDialog> getDialogs() {
        return dialogs;
    }

    public void setDialogs(List<VKDialog> items) {
        this.dialogs = items;
    }

    @Override
    public String toString() {
        return "DialogsResponse{" +
                "count=" + count +
                ", unreadDialogs=" + unreadDialogs +
                ", dialogs=" + dialogs +
                '}';
    }
}
