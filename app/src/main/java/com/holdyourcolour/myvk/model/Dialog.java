package com.holdyourcolour.myvk.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by uadn_mei on 2/27/17.
 */

public class Dialog {
    public static int sCountUnreadDialogs;
    private Message mMessage;
    private String mPhotoUrl;

    private Dialog(){}

    public Message getMessage(){
        return mMessage;
    }

    public void setMessage(Message message){
        mMessage = message;
    }

    public int getCountUnreadMessages(){
        return sCountUnreadDialogs;
    }

    public void setCountUnreadMessages(int countUnreadMessages){
        sCountUnreadDialogs = countUnreadMessages;
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        mPhotoUrl = photoUrl;
    }

    @Override
    public String toString() {
        return "Dialog{" +
                "mMessage=" + mMessage +
                '}';
    }

    public static Dialog parseDialog(JSONObject messageJSON) throws JSONException {
        Dialog dialog = new Dialog();
        dialog.setMessage(Message.parseMessage(messageJSON));
        return dialog;
    }
}
