package com.example.projectskillswap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SkillMatchAdapter extends RecyclerView.Adapter<SkillMatchAdapter.MatchViewHolder> {

    private List<Swapper> matchList;

    public SkillMatchAdapter(List<Swapper> matchList) {
        this.matchList = matchList;
    }

    @NonNull
    @Override
    public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_skill_match, parent, false);
        return new MatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchViewHolder holder, int position) {
        Swapper swapper = matchList.get(position);
        holder.tvName.setText(swapper.getName());
        holder.tvBio.setText(swapper.getDescription());
        holder.ivProfile.setImageResource(swapper.getProfileImage());
    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }

    public static class MatchViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvBio;
        ImageView ivProfile;

        public MatchViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_match_name);
            tvBio = itemView.findViewById(R.id.tv_match_bio);
            ivProfile = itemView.findViewById(R.id.iv_match_profile);
        }
    }
}
