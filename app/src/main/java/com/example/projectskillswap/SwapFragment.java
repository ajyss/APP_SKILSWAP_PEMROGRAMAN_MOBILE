package com.example.projectskillswap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class SwapFragment extends Fragment {

    private RecyclerView rvSwappers;
    private TopSwappersAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_swap, container, false);

        // Menggunakan ID yang benar dari fragment_swap.xml mas
        rvSwappers = view.findViewById(R.id.rv_skill_matches);
        
        if (rvSwappers != null) {
            setupAllSwappers();
        }

        return view;
    }

    private void setupAllSwappers() {
        List<Swapper> swapperList = new ArrayList<>();
        // Menggunakan constructor baru Swapper (tanpa koordinat lat/long)
        swapperList.add(new Swapper("Ramanda", "UI/UX", "Ahli Figma.", "1.2 km", R.drawable.fotopria1));
        swapperList.add(new Swapper("Siti Aminah", "Public Speaking", "Ahli Debat.", "3.5 km", R.drawable.fotoperempuan1));
        swapperList.add(new Swapper("Budi Santoso", "Android Dev", "Ahli Kotlin.", "500 m", R.drawable.fotopria2));

        // Constructor adapter: Context dulu baru List
        adapter = new TopSwappersAdapter(getActivity(), swapperList);
        rvSwappers.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvSwappers.setAdapter(adapter);
    }
}
