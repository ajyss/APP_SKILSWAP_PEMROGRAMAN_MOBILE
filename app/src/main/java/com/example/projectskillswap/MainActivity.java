package com.example.projectskillswap;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main);

            BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_view);
            
            if (bottomNav != null) {
                bottomNav.setOnItemSelectedListener(item -> {
                    Fragment selectedFragment = null;
                    int id = item.getItemId();

                    if (id == R.id.nav_home) {
                        selectedFragment = new HomeFragment();
                    } else if (id == R.id.nav_analysis) {
                        selectedFragment = new AnalysisFragment();
                    } else if (id == R.id.nav_transactions) {
                        selectedFragment = new SwapFragment();
                    } else if (id == R.id.nav_category) {
                        selectedFragment = new CategoryFragment();
                    } else if (id == R.id.nav_profile) {
                        selectedFragment = new ProfileFragment();
                    }

                    if (selectedFragment != null) {
                        loadFragment(selectedFragment);
                        return true;
                    }
                    return false;
                });
            }

            // Muat HomeFragment sebagai default secara aman
            if (savedInstanceState == null) {
                loadFragment(new HomeFragment());
            }

        } catch (Exception e) {
            Log.e("SKILLSWAP_CRASH", "Gagal buka MainActivity: " + e.getMessage());
        }
    }

    private void loadFragment(Fragment fragment) {
        if (findViewById(R.id.fragment_container) != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commitAllowingStateLoss(); // Lebih aman untuk transisi cepat
        }
    }
}
