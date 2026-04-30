package com.example.projectskillswap;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TopSwappersAdapter extends RecyclerView.Adapter<TopSwappersAdapter.SwapperViewHolder> {

    private Context context;
    private List<Swapper> swapperList;

    public TopSwappersAdapter(Context context, List<Swapper> swapperList) {
        this.context = context;
        this.swapperList = swapperList;
    }

    @NonNull
    @Override
    public SwapperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_swapper, parent, false);
        return new SwapperViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SwapperViewHolder holder, int position) {
        if (swapperList == null || position >= swapperList.size()) return;
        
        Swapper swapper = swapperList.get(position);

        holder.ivSwapperProfile.setImageResource(swapper.getProfileImage());
        holder.tvSwapperName.setText(swapper.getName());
        holder.tvSwapperSkills.setText(swapper.getSkills());
        holder.tvSwapperDesc.setText(swapper.getDescription());

        holder.itemView.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(context, DetailSwapperActivity.class);
                intent.putExtra("EXTRA_NAME", swapper.getName());
                intent.putExtra("EXTRA_SKILLS", swapper.getSkills());
                intent.putExtra("EXTRA_DESC", swapper.getDescription());
                intent.putExtra("EXTRA_IMAGE", swapper.getProfileImage());
                context.startActivity(intent);
            } catch (Exception e) {
                Log.e("SKILLSWAP_CLICK", "Gagal buka detail: " + e.getMessage());
            }
        });
    }

    @Override
    public int getItemCount() {
        return swapperList != null ? swapperList.size() : 0;
    }

    public static class SwapperViewHolder extends RecyclerView.ViewHolder {
        ImageView ivSwapperProfile;
        TextView tvSwapperName, tvSwapperSkills, tvSwapperDesc;

        public SwapperViewHolder(@NonNull View itemView) {
            super(itemView);
            ivSwapperProfile = itemView.findViewById(R.id.iv_swapper_profile);
            tvSwapperName = itemView.findViewById(R.id.tv_swapper_name);
            tvSwapperSkills = itemView.findViewById(R.id.tv_swapper_skills);
            tvSwapperDesc = itemView.findViewById(R.id.tv_swapper_desc);
        }
    }
}
