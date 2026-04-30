package com.example.projectskillswap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class OnboardingAdapter extends PagerAdapter {

    private final Context context;
    private final int[] images = {R.drawable.image_onboarding1, R.drawable.image_onboarding2};
    private final OnNextClickListener nextClickListener;

    public interface OnNextClickListener {
        void onNextClick(int position);
    }

    public OnboardingAdapter(Context context, OnNextClickListener nextClickListener) {
        this.context = context;
        this.nextClickListener = nextClickListener;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        // Sekarang hanya memuat Card Putih
        View view = inflater.inflate(R.layout.onboarding_slide, container, false);

        ImageView imageView = view.findViewById(R.id.iv_onboarding);
        TextView tvBtnNext = view.findViewById(R.id.tv_btn_next);

        imageView.setImageResource(images[position]);
        
        if (position == images.length - 1) {
            tvBtnNext.setText("Mulai");
        } else {
            tvBtnNext.setText("Selanjutnya");
        }

        tvBtnNext.setOnClickListener(v -> {
            if (nextClickListener != null) {
                nextClickListener.onNextClick(position);
            }
        });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
