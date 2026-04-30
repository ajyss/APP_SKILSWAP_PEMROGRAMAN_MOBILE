package com.example.projectskillswap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText etUser, etPass;
    private FirebaseAuth mAuth;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        etUser = findViewById(R.id.et_login_user);
        etPass = findViewById(R.id.et_login_pass);

        loading = new ProgressDialog(this);
        loading.setMessage("Tunggu sebentar ya mas...");
        loading.setCancelable(false);

        findViewById(R.id.btn_masuk_submit).setOnClickListener(v -> handleLogin());
        
        if (findViewById(R.id.tv_btn_ke_daftar) != null) {
            findViewById(R.id.tv_btn_ke_daftar).setOnClickListener(v -> {
                startActivity(new Intent(this, RegisterActivity.class));
            });
        }
    }

    private void handleLogin() {
        String email = etUser.getText().toString().trim();
        String password = etPass.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Email & Password wajib diisi mas!", Toast.LENGTH_SHORT).show();
            return;
        }

        loading.show();

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, task -> {
                loading.dismiss();
                if (task.isSuccessful()) {
                    Log.d("LOGIN_SUCCESS", "Berhasil masuk, pindah ke MainActivity...");
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    String error = task.getException() != null ? task.getException().getMessage() : "Gagal masuk";
                    Toast.makeText(LoginActivity.this, "Gagal: " + error, Toast.LENGTH_LONG).show();
                }
            });
    }
}
