package com.example.projectskillswap;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    private final List<Chat> chatList;
    private OnItemLongClickListener longClickListener;

    public interface OnItemLongClickListener {
        void onItemLongClick(Chat chat, int position);
    }

    public ChatAdapter(List<Chat> chatList, OnItemLongClickListener longClickListener) {
        this.chatList = chatList;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Menggunakan item_swapper yang sudah bertema Liquid Glass
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_swapper, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Chat chat = chatList.get(position);

        if (chat != null) {
            holder.tvName.setText(chat.getName());
            // Menampilkan pesan terakhir di baris kedua (id: tv_swapper_skills)
            holder.tvMessage.setText(chat.getLastMessage());
            holder.ivProfile.setImageResource(chat.getImageResId());

            // Klik biasa: Buka ChatActivity
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), ChatActivity.class);
                intent.putExtra("EXTRA_PARTNER_NAME", chat.getName());
                intent.putExtra("EXTRA_PARTNER_IMAGE", chat.getImageResId());
                v.getContext().startActivity(intent);
            });

            // Klik lama: Hapus Chat
            holder.itemView.setOnLongClickListener(v -> {
                if (longClickListener != null) {
                    longClickListener.onItemLongClick(chat, position);
                }
                return true;
            });
        }
    }

    @Override
    public int getItemCount() {
        return chatList != null ? chatList.size() : 0;
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvMessage;
        ImageView ivProfile;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfile = itemView.findViewById(R.id.iv_swapper_profile);
            tvName = itemView.findViewById(R.id.tv_swapper_name);
            // Menggunakan tv_swapper_skills sebagai tempat menampilkan pesan terakhir
            tvMessage = itemView.findViewById(R.id.tv_swapper_skills);
        }
    }
}
