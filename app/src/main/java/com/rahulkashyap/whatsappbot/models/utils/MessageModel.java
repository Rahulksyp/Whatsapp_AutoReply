package com.rahulkashyap.whatsappbot.models.utils;

import io.realm.RealmObject;

public class MessageModel extends RealmObject {
    String whatsMsg;
    String whatsToReply;
    int currentPostion;

    public MessageModel() {
    }

    public String getWhatsMsg() {
        return whatsMsg;
    }

    public int getCurrentPostion() {
        return currentPostion;
    }

    public void setCurrentPostion(int currentPostion) {
        this.currentPostion = currentPostion;
    }

    public void setWhatsMsg(String whatsMsg) {
        this.whatsMsg = whatsMsg;
    }

    public String getWhatsToReply() {
        return whatsToReply;
    }

    public void setWhatsToReply(String whatsToReply) {
        this.whatsToReply = whatsToReply;
    }
}


