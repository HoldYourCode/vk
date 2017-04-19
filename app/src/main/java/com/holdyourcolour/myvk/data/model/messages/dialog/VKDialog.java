package com.holdyourcolour.myvk.data.model.messages.dialog;

import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.holdyourcolour.myvk.data.model.messages.message.VKMessage;

/**
 * Created by HoldYourColour on 17.04.2017.
 */

public class VKDialog {
    @SerializedName("unread")
    @Expose
    private int unread; // optional field
    @SerializedName("message")
    @Expose
    private VKMessage message;
    @SerializedName("in_read")
    @Expose
    private Integer inRead;

    @SerializedName("out_read")
    @Expose
    private Integer outRead;

    private String photoURL;
    private Bitmap photo;

    public Integer getUnread() {
        return unread;
    }

    public void setUnread(Integer unread) {
        this.unread = unread;
    }

    public VKMessage getMessage() {
        return message;
    }

    public void setMessage(VKMessage message) {
        this.message = message;
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

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "VKDialog{" +
                "unread=" + unread +
                ", message=" + message +
                ", inRead=" + inRead +
                ", outRead=" + outRead +
                '}';
    }

}
