package com.example.projectskillswap;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView rvChat;
    private MessageAdapter messageAdapter;
    private List<ChatMessage> messageList;
    private EditText etMessage;
    private FirebaseHelper firebaseHelper;
    private String currentUserName;
    private String partnerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // 1. Ambil Nama Pengguna (Kita) & Nama Partner
        SharedPreferences sharedPref = getSharedPreferences("SkillSwapPref", MODE_PRIVATE);
        currentUserName = sharedPref.getString("USER_NAME", "User");
        partnerName = getIntent().getStringExtra("EXTRA_PARTNER_NAME");

        // 2. Setup Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_chat);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(partnerName);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // 3. Inisialisasi UI & Firebase
        rvChat = findViewById(R.id.rv_chat);
        etMessage = findViewById(R.id.et_chat_message);
        ImageButton btnSend = findViewById(R.id.btn_send_chat);
        firebaseHelper = new FirebaseHelper();

        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList, currentUserName);
        rvChat.setLayoutManager(new LinearLayoutManager(this));
        rvChat.setAdapter(messageAdapter);

        // 4. Listen Pesan Masuk (Real-time dari Firebase)
        firebaseHelper.listenForMessages(currentUserName, partnerName, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    ChatMessage chat = data.getValue(ChatMessage.class);
                    if (chat != null) {
                        messageList.add(chat);
                    }
                }
                messageAdapter.notifyDataSetChanged();
                rvChat.scrollToPosition(messageList.size() - 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error jika koneksi gagal
            }
        });

        // 5. Tombol Kirim Pesan ke Firebase
        btnSend.setOnClickListener(v -> {
            String text = etMessage.getText().toString().trim();
            if (!text.isEmpty()) {
                firebaseHelper.sendMessage(currentUserName, partnerName, text);
                etMessage.setText("");
            }
        });
    }
}
