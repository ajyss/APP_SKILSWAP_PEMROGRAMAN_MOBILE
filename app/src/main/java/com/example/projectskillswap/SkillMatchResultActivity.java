package com.example.projectskillswap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class SkillMatchResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_match_result);

        CircleImageView ivOther = findViewById(R.id.iv_match_other);
        Button btnSendMessage = findViewById(R.id.btn_send_message_match);

        // Ambil gambar lawan swap dari intent
        int otherImage = getIntent().getIntExtra("EXTRA_IMAGE", R.drawable.fotopria1);
        if (ivOther != null) {
            ivOther.setImageResource(otherImage);
        }

        if (btnSendMessage != null) {
            btnSendMessage.setOnClickListener(v -> {
                startActivity(new Intent(this, ChatActivity.class));
                finish();
            });
        }
    }
}
