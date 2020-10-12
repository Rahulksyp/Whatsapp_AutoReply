package com.rahulkashyap.whatsappbot.models.utils;

import io.realm.RealmObject;

public class CustomMessageModel extends RealmObject {

    private String incomingMessage;
    private String replyMessage;
    private String radioButtonText;
    private String specificConName;
    private String specificConNum;


    public CustomMessageModel() {
    }

    public String getSpecificConNum() {
        return specificConNum;
    }

    public void setSpecificConNum(String specificConNum) {
        this.specificConNum = specificConNum;
    }

    public String getSpecificConName() {
        return specificConName;
    }

    public void setSpecificConName(String specificConName) {
        this.specificConName = specificConName;
    }

    public String getIncomingMessage() {
        return incomingMessage;
    }

    public void setIncomingMessage(String incomingMessage) {
        this.incomingMessage = incomingMessage;
    }

    public String getReplyMessage() {
        return replyMessage;
    }

    public void setReplyMessage(String replyMessage) {
        this.replyMessage = replyMessage;
    }

    public String getRadioButtonText() {
        return radioButtonText;
    }

    public void setRadioButtonText(String radioButtonText) {
        this.radioButtonText = radioButtonText;
    }
}
