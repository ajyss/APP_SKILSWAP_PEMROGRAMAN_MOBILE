package com.example.projectskillswap;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDao {
    @Insert
    void registerUser(UserEntity user);

    @Query("SELECT * FROM users_table WHERE email = :email AND password = :password LIMIT 1")
    UserEntity login(String email, String password);

    @Query("SELECT * FROM users_table WHERE email = :email LIMIT 1")
    UserEntity getUserByEmail(String email);

    @Query("SELECT * FROM users_table WHERE fullName = :name LIMIT 1")
    UserEntity getUserByName(String name);

    @Update
    void updateUser(UserEntity user);
}
