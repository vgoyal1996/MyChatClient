package com.example.vipul.mychatclient;


public class Chat {
    private String name;
    private String text;

    public Chat(String name,String text) {
        this.name = name;
        this.text = text;
    }

    public Chat(){

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String user) {
        this.name = user;
    }
}
