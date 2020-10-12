package com.rahulkashyap.whatsappbot.models.utils;

import io.realm.RealmObject;

public class MessageStore extends RealmObject {
    public static final String SELECTEDPOSTION = "selectedPosition";

    public String receive;
    public String sent;
    public int selectedPosition;

    public MessageStore() {
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public String getReceive() {
        return receive;
    }

    public void setReceive(String receive) {
        this.receive = receive;
    }

    public String getSent() {
        return sent;
    }

    public void setSent(String sent) {
        this.sent = sent;
    }
}
