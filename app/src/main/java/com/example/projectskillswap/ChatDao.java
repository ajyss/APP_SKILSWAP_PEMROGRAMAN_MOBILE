package com.example.projectskillswap;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ChatDao {
    @Insert
    void insert(ChatEntity chat);

    @Query("SELECT * FROM inbox_table ORDER BY id DESC")
    List<ChatEntity> getAllInbox();

    @Query("SELECT * FROM inbox_table WHERE name = :name LIMIT 1")
    ChatEntity getChatByName(String name);

    @Delete
    void delete(ChatEntity chat);
}
