package com.example.projectskillswap;

public class Chat {    private String name;
    private String lastMessage;
    private int imageResId;

    public Chat(String name, String lastMessage, int imageResId) {
        this.name = name;
        this.lastMessage = lastMessage;
        this.imageResId = imageResId;
    }

    public String getName() { return name; }
    public String getLastMessage() { return lastMessage; }
    public int getImageResId() { return imageResId; }
}
