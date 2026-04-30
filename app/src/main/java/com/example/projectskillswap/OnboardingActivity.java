package com.example.projectskillswap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import me.relex.circleindicator.CircleIndicator;

public class OnboardingActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private CircleIndicator indicator;
    private OnboardingAdapter adapter;
    private TextView tvFixedTitle;

    private final String[] titles = {
        "Selamat Datang Di\nSkillSwap", 
        "Tukarkan Keahlian\nAnda Dan Dapatkan\nKeuntungan"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        viewPager = findViewById(R.id.viewPagerOnboarding);
        indicator = findViewById(R.id.indicator);
        tvFixedTitle = findViewById(R.id.tv_fixed_title);

        adapter = new OnboardingAdapter(this, position -> {
            if (position < adapter.getCount() - 1) {
                viewPager.setCurrentItem(position + 1);
            } else {
                startActivity(new Intent(OnboardingActivity.this, LoginActivity.class));
                finish();
            }
        });

        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);

        // Update Judul Statis saat Panel di slide
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                tvFixedTitle.setText(titles[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }
}
