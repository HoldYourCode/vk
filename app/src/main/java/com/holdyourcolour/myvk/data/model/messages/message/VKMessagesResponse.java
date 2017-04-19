package com.holdyourcolour.myvk.data.model.messages.message;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

/**
 * Created by HoldYourColour on 17.04.2017.
 */

public class VKMessagesResponse {
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("items")
    @Expose
    private List<VKMessage> items = null;
    @SerializedName("in_read")
    @Expose
    private Integer inRead;
    @SerializedName("out_read")
    @Expose
    private Integer outRead;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<VKMessage> getItems() {
        return items;
    }

    public void setItems(List<VKMessage> items) {
        this.items = items;
    }

    public Integer getInRead() {
        return inRead;
    }

    public void setInRead(Integer inRead) {
        this.inRead = inRead;
    }

    public Integer getOutRead() {
        return outRead;
    }

    public void setOutRead(Integer outRead) {
        this.outRead = outRead;
    }

}
