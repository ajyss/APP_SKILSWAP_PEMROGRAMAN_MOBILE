package com.example.projectskillswap;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseHelper {
    private DatabaseReference mDatabase;

    public FirebaseHelper() {
        // Inisialisasi Database
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    // Fungsi Kirim Pesan Real-time
    public void sendMessage(String sender, String receiver, String message) {
        String chatId = getChatId(sender, receiver);
        String messageId = mDatabase.child("chats").child(chatId).push().getKey();

        ChatMessage chatMessage = new ChatMessage(sender, receiver, message, System.currentTimeMillis());

        if (messageId != null) {
            mDatabase.child("chats").child(chatId).child(messageId).setValue(chatMessage);
        }
    }

    // Fungsi untuk Listen Pesan (Real-time)
    public void listenForMessages(String sender, String receiver, ValueEventListener listener) {
        String chatId = getChatId(sender, receiver);
        mDatabase.child("chats").child(chatId).addValueEventListener(listener);
    }

    // Membuat ID Unik untuk Chat antar 2 orang agar selalu sama
    private String getChatId(String user1, String user2) {
        if (user1.compareTo(user2) < 0) {
            return user1 + "_" + user2;
        } else {
            return user2 + "_" + user1;
        }
    }
}
