package com.example.projectskillswap;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText etName, etEmail, etPhone, etDob, etPass, etConfirmPass;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // SINKRONISASI ID DENGAN LAYOUT PREMIUM
        etName = findViewById(R.id.et_reg_name);
        etEmail = findViewById(R.id.et_reg_email);
        etPhone = findViewById(R.id.et_reg_phone);
        etDob = findViewById(R.id.et_reg_dob);
        etPass = findViewById(R.id.et_reg_password);
        etConfirmPass = findViewById(R.id.et_reg_confirm_password);

        findViewById(R.id.btn_register_submit).setOnClickListener(v -> handleRegister());
        
        findViewById(R.id.tv_btn_ke_login).setOnClickListener(v -> {
            finish(); // Kembali ke halaman Login
        });
    }

    private void handleRegister() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String dob = etDob.getText().toString().trim();
        String password = etPass.getText().toString().trim();
        String confirmPass = etConfirmPass.getText().toString().trim();

        // 1. VALIDASI INPUT SUPER KETAT
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Nama, Email, dan Password wajib diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            etPass.setError("Password minimal 6 karakter");
            return;
        }

        if (!password.equals(confirmPass)) {
            etConfirmPass.setError("Konfirmasi password tidak cocok");
            return;
        }

        // 2. PROSES DAFTAR KE FIREBASE
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    String userId = mAuth.getCurrentUser().getUid();
                    
                    // Simpan Data Lengkap ke Realtime Database
                    Map<String, Object> userMap = new HashMap<>();
                    userMap.put("fullName", name);
                    userMap.put("email", email);
                    userMap.put("phone", phone);
                    userMap.put("dob", dob);
                    userMap.put("credits", 100); // Bonus awal pengguna baru

                    mDatabase.child("users").child(userId).setValue(userMap)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(RegisterActivity.this, "Akun Berhasil Dibuat!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            finish();
                        });
                } else {
                    Toast.makeText(RegisterActivity.this, "Gagal Daftar: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }
}
