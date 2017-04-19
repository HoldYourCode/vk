package com.holdyourcolour.myvk.data.model.messages.message;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by HoldYourColour on 17.04.2017.
 */

public class VKMessage {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("date")
    @Expose
    private Integer date;
    @SerializedName("out")
    @Expose
    private Integer out;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("read_state")
    @Expose
    private Integer readState;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("random_id")
    @Expose
    private Integer randomId;
    @SerializedName("emoji")
    @Expose
    private Integer emoji;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Integer getOut() {
        return out;
    }

    public void setOut(Integer out) {
        this.out = out;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getReadState() {
        return readState;
    }

    public void setReadState(Integer readState) {
        this.readState = readState;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getRandomId() {
        return randomId;
    }

    public void setRandomId(Integer randomId) {
        this.randomId = randomId;
    }

    public Integer getEmoji() {
        return emoji;
    }

    public void setEmoji(Integer emoji) {
        this.emoji = emoji;
    }

}