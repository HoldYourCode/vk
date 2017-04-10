package com.holdyourcolour.myvk.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by uadn_mei on 3/1/17.
 */

public class Message {
    private int mId;
    private long mDate;
    private boolean mOut; // 0 - received, 1 - sent
    private int mUserId;
    private boolean mReadState; // 0 - not read, 1 -read
    private String mTitle;
    private String mBody;

    private Message(){}

    public Message(int id, long date, boolean readState, String title, String body, boolean out, int userId) {
        mId = id;
        mDate = date;
        mReadState = readState;
        mTitle = title;
        mBody = body;
        mOut = out;
        mUserId = userId;
    }

    public int getId(){
        return mId;
    }

    public void setId(int id){
        mId = id;
    }

    public long getDate(){
        return mDate;
    }

    public void setDate(long date){
        mDate = date;
    }

    public boolean getReadState(){
        return mReadState;
    }

    public void setReadState(boolean readState){
        mReadState = readState;
    }

    public String getTitle(){
        return mTitle;
    }

    public void setTitle(String title){
        mTitle = title;
    }

    public String getBody() {
        return mBody;
    }

    public void setBody(String body) {
        mBody = body;
    }

    public boolean getOut() {
        return mOut;
    }

    public void setOut(boolean out) {
        mOut = out;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }

    public static Message parseMessage(JSONObject message) throws JSONException {
        Message msg = new Message();
        msg.setId(message.getInt("mid"));
        msg.setDate(message.getLong("date"));
        boolean out = message.getInt("out") != 0;
        msg.setOut(out);
        msg.setUserId(message.getInt("uid"));
        boolean readState = message.getInt("read_state") != 0;
        msg.setReadState(readState);
        msg.setTitle(message.getString("title"));
        msg.setBody(message.getString("body"));
        return msg;
    }

    @Override
    public String toString() {
        return "Message{" +
                "mId=" + mId +
                ", mDate=" + mDate +
                ", mOut=" + mOut +
                ", mUserId=" + mUserId +
                ", mReadState=" + mReadState +
                ", mTitle='" + mTitle + '\'' +
                ", mBody='" + mBody + '\'' +
                '}';
    }
}
