package com.ninjeng.softwaricacollegestudentcommunicationportal.Model;

public class ChatList {
    String id ;
    String reciverid;


    public ChatList(String id, String reciverid) {
        this.id = id;
        this.reciverid = reciverid;
    }



    public String getReciverid() {
        return reciverid;
    }

    public void setReciverid(String reciverid) {
        this.reciverid = reciverid;
    }

    public ChatList() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
