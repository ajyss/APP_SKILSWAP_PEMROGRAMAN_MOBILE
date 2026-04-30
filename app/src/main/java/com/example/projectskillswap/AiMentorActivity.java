package com.example.projectskillswap;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AiMentorActivity extends AppCompatActivity {

    private RecyclerView rvAiChat;
    private MessageAdapter aiAdapter;
    private List<ChatMessage> aiMessages;
    private EditText etInput;
    private ProgressBar pbLoading;
    private GeminiHelper geminiHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_mentor);

        // Inisialisasi UI
        rvAiChat = findViewById(R.id.rv_ai_chat);
        etInput = findViewById(R.id.et_ai_input);
        pbLoading = findViewById(R.id.pb_ai_loading);
        ImageButton btnSend = findViewById(R.id.btn_send_ai);

        geminiHelper = new GeminiHelper();
        aiMessages = new ArrayList<>();
        
        // Menggunakan MessageAdapter yang sama, tapi pengirimnya "AI Mentor"
        aiAdapter = new MessageAdapter(aiMessages, "Saya");
        rvAiChat.setLayoutManager(new LinearLayoutManager(this));
        rvAiChat.setAdapter(aiAdapter);

        // Pesan sambutan dari AI
        aiMessages.add(new ChatMessage("AI Mentor", "Saya", "Halo! Saya AI Mentor SkillSwap. Ada yang bisa saya bantu terkait belajar skill baru hari ini?", System.currentTimeMillis()));

        btnSend.setOnClickListener(v -> {
            String query = etInput.getText().toString().trim();
            if (!query.isEmpty()) {
                askAI(query);
            }
        });
    }

    private void askAI(String query) {
        // 1. Tampilkan pesan user ke list
        aiMessages.add(new ChatMessage("Saya", "AI Mentor", query, System.currentTimeMillis()));
        aiAdapter.notifyItemInserted(aiMessages.size() - 1);
        rvAiChat.scrollToPosition(aiMessages.size() - 1);
        etInput.setText("");

        // 2. Tampilkan loading & jalankan AI
        pbLoading.setVisibility(View.VISIBLE);
        geminiHelper.askGemini(query, new GeminiHelper.AIResponseListener() {
            @Override
            public void onResponse(String response) {
                runOnUiThread(() -> {
                    pbLoading.setVisibility(View.GONE);
                    aiMessages.add(new ChatMessage("AI Mentor", "Saya", response, System.currentTimeMillis()));
                    aiAdapter.notifyItemInserted(aiMessages.size() - 1);
                    rvAiChat.scrollToPosition(aiMessages.size() - 1);
                });
            }

            @Override
            public void onError(String errorMessage) {
                runOnUiThread(() -> {
                    pbLoading.setVisibility(View.GONE);
                    // Sekarang menampilkan pesan error yang lebih jelas dari GeminiHelper
                    Toast.makeText(AiMentorActivity.this, "AI Mentor: " + errorMessage, Toast.LENGTH_LONG).show();
                });
            }
        });
    }
}
