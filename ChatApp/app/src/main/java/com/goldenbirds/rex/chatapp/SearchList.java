package com.goldenbirds.rex.chatapp;

public class SearchList {
    String deviceID;
    String gender;

    public SearchList() {
    }

    public String getDeviceID() {
        return deviceID;
    }

    public String getGender() {
        return gender;
    }

    public SearchList(String deviceID, String gender) {
        this.deviceID = deviceID;
        this.gender = gender;
    }
}
