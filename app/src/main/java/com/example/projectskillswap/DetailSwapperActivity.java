package com.example.projectskillswap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class DetailSwapperActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_swapper);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // 1. Bind UI Components
        ImageView ivProfile = findViewById(R.id.iv_detail_profile);
        TextView tvName = findViewById(R.id.tv_detail_name);
        TextView tvSkills = findViewById(R.id.tv_detail_skills);
        
        Button btnRequest = findViewById(R.id.btn_chat_request); // Tombol Request
        Button btnMessage = findViewById(R.id.btn_message_direct); // Tombol Message

        // Tombol Back
        findViewById(R.id.btn_back_detail).setOnClickListener(v -> finish());

        // 2. Ambil data dari Intent
        String name = getIntent().getStringExtra("EXTRA_NAME");
        String skills = getIntent().getStringExtra("EXTRA_SKILLS");
        int image = getIntent().getIntExtra("EXTRA_IMAGE", R.drawable.fotopria1);

        if (tvName != null) tvName.setText(name);
        if (tvSkills != null) tvSkills.setText(skills);
        if (ivProfile != null) ivProfile.setImageResource(image);

        // 3. FUNGSI TOMBOL REQUEST -> Langsung Match Success
        if (btnRequest != null) {
            btnRequest.setOnClickListener(v -> handleSwapProcess(name, skills, image));
        }

        // 4. FUNGSI TOMBOL MESSAGE -> Langsung ke Chat
        if (btnMessage != null) {
            btnMessage.setOnClickListener(v -> {
                startActivity(new Intent(this, ChatActivity.class));
            });
        }
    }

    private void handleSwapProcess(String targetName, String targetSkills, int targetImage) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            Toast.makeText(this, "Silakan login dulu mas!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Simpan data swap ke Firebase (Agar poin dashboard naik)
        String key = mDatabase.child("swap_requests").push().getKey();
        Map<String, Object> data = new HashMap<>();
        data.put("senderName", user.getDisplayName() != null ? user.getDisplayName() : "User");
        data.put("targetName", targetName);
        data.put("status", "accepted");
        data.put("timestamp", System.currentTimeMillis());

        if (key != null) {
            mDatabase.child("swap_requests").child(key).setValue(data).addOnSuccessListener(aVoid -> {
                // PINDAH KE HALAMAN MATCH (Sesuai Gambar Mas)
                Intent intent = new Intent(DetailSwapperActivity.this, SkillMatchResultActivity.class);
                intent.putExtra("EXTRA_IMAGE", targetImage);
                startActivity(intent);
                finish();
            });
        }
    }
}
