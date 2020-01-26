package com.goldenbirds.rex.chatapp;

public class Message {
    String msg;
    String id;
    String  timestamp;

    public Message(){
        //default empty constructor
    }
    public Message(String msg, String id, String timestamp) {
        this.msg = msg;
        this.id = id;
        this.timestamp = timestamp;
    }

    public String getMsg() {
        return msg;
    }

    public String getId() {
        return id;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
