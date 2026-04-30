package com.example.projectskillswap;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EditProfileActivity extends AppCompatActivity {

    private EditText etUsername, etEmail;
    private AppCompatButton btnUpdate;
    private ImageView btnBack;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        // Hubungkan ke ID yang ada di activity_edit_profile.xml
        etUsername = findViewById(R.id.et_edit_username);
        etEmail = findViewById(R.id.et_edit_email);
        btnUpdate = findViewById(R.id.btn_update_profile);
        btnBack = findViewById(R.id.btn_back_edit);

        // Isi data otomatis dari Firebase agar tidak "John Smith" lagi
        if (user != null) {
            if (etUsername != null) etUsername.setText(user.getDisplayName());
            if (etEmail != null) etEmail.setText(user.getEmail());
        }

        // Fungsi Kembali
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // Fungsi Update (Simulasi)
        if (btnUpdate != null) {
            btnUpdate.setOnClickListener(v -> {
                // Di sini mas bisa menambahkan logika Firebase untuk update profile jika perlu
                Toast.makeText(this, "Profil Berhasil Diperbarui!", Toast.LENGTH_SHORT).show();
                finish();
            });
        }
    }
}
