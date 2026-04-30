package com.example.projectskillswap;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private RecyclerView rvTopSwappers;
    private TopSwappersAdapter adapter;
    private TextView tvGreeting, tvTier, tvCredits, tvEarnings, tvInfoCount;
    private ImageView ivNotification, ivChat;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            View view = inflater.inflate(R.layout.fragment_home, container, false);
            
            mAuth = FirebaseAuth.getInstance();
            mDatabase = FirebaseDatabase.getInstance().getReference();
            
            // Analisis: Null check pada setiap inisialisasi View untuk mencegah NPE saat transisi Login
            tvGreeting = view.findViewById(R.id.tv_greeting_home);
            tvTier = view.findViewById(R.id.tv_home_tier);
            tvCredits = view.findViewById(R.id.tv_home_credits);
            tvEarnings = view.findViewById(R.id.tv_home_earnings);
            tvInfoCount = view.findViewById(R.id.tv_home_info_count);
            ivNotification = view.findViewById(R.id.iv_home_notification);
            ivChat = view.findViewById(R.id.iv_home_chat);
            rvTopSwappers = view.findViewById(R.id.rv_top_swappers);
            ExtendedFloatingActionButton fabAi = view.findViewById(R.id.fab_ai_mentor);

            // Analisis alur Chat: Memastikan intent terdaftar di Manifest
            if (ivChat != null) {
                ivChat.setOnClickListener(v -> {
                    startActivity(new Intent(getActivity(), ChatActivity.class));
                });
            }

            // Analisis alur Notifikasi: Memastikan intent terdaftar di Manifest
            if (ivNotification != null) {
                ivNotification.setOnClickListener(v -> {
                    startActivity(new Intent(getActivity(), NotificationActivity.class));
                });
            }

            if (fabAi != null) {
                fabAi.setOnClickListener(v -> startActivity(new Intent(getActivity(), AiMentorActivity.class)));
            }

            // Delay pemuatan data asinkron agar UI Fragment siap sepenuhnya
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                if (isAdded()) {
                    loadStats();
                    loadSwappers();
                }
            }, 300);

            return view;
        } catch (Exception e) {
            Log.e("SKILLSWAP_ERROR", "Error Dashboard: " + e.getMessage());
            return new View(getContext());
        }
    }

    private void loadStats() {
        mDatabase.child("swap_requests").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!isAdded() || getView() == null) return;
                int count = 0;
                for (DataSnapshot d : snapshot.getChildren()) {
                    if ("accepted".equals(d.child("status").getValue(String.class))) count++;
                }
                if (tvTier != null) tvTier.setText("Gold");
                if (tvCredits != null) tvCredits.setText("C " + (count * 10));
                if (tvEarnings != null) {
                    NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
                    tvEarnings.setText(nf.format(count * 50000L));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void loadSwappers() {
        if (rvTopSwappers == null || !isAdded()) return;
        List<Swapper> list = new ArrayList<>();
        list.add(new Swapper("Ramanda", "UI/UX Design", "Ahli Figma.", "1.2 km", R.drawable.fotopria1));
        list.add(new Swapper("Siti Aminah", "Public Speaking", "Ahli Debat.", "3.5 km", R.drawable.fotoperempuan1));
        list.add(new Swapper("Budi Santoso", "Android Dev", "Ahli Kotlin.", "500 m", R.drawable.fotopria2));

        adapter = new TopSwappersAdapter(getActivity(), list);
        rvTopSwappers.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTopSwappers.setAdapter(adapter);
    }
}
