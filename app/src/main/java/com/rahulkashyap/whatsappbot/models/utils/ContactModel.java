package com.rahulkashyap.whatsappbot.models.utils;

import io.realm.RealmObject;

public class ContactModel extends RealmObject {
    public static final String SELECTEDPOSTION = "selectedPosition";
    private String name;
    private String phone;
    public int selectedPosition;
    private boolean isChecked = false;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }


    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public ContactModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
