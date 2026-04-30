package com.example.projectskillswap;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Handle the splash screen transition.
        SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);

        // Setelah splash screen selesai, langsung pindah ke activity berikutnya.
        // Sistem akan menunggu proses penggambaran layout selesai sebelum transisi.
        // Anda tidak perlu Handler atau delay lagi.

        // Tentukan tujuan selanjutnya (misalnya Onboarding atau Main)
        Intent intent = new Intent(SplashActivity.this, OnboardingActivity.class); // atau MainActivity, LoginActivity, dll.
        startActivity(intent);
        finish(); // Pastikan activity ini ditutup agar tidak bisa kembali ke splash screen
    }
}
