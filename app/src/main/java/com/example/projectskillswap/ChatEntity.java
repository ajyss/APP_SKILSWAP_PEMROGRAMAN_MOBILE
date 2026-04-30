package com.example.projectskillswap;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "inbox_table")
public class ChatEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public String lastMessage;
    public int imageResId;

    public ChatEntity(String name, String lastMessage, int imageResId) {
        this.name = name;
        this.lastMessage = lastMessage;
        this.imageResId = imageResId;
    }
}
