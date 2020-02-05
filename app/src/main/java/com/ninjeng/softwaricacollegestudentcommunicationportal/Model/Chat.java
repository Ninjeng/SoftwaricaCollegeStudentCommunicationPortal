package com.ninjeng.softwaricacollegestudentcommunicationportal.Model;

public class Chat {
    private String message;
    private String msgReciver;
    private String msgSender;
    private String type;

    public Chat(String message, String msgReciver, String msgSender,String type) {
        this.message = message;
        this.msgReciver = msgReciver;
        this.msgSender = msgSender;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Chat() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsgReciver() {
        return msgReciver;
    }

    public void setMsgReciver(String msgReciver) {
        this.msgReciver = msgReciver;
    }

    public String getMsgSender() {
        return msgSender;
    }

    public void setMsgSender(String msgSender) {
        this.msgSender = msgSender;
    }
}
