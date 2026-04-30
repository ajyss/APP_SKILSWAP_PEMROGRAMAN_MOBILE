package com.example.projectskillswap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private final List<ChatMessage> messageList;
    private final String currentUserName;

    private static final int TYPE_ME = 1;
    private static final int TYPE_OTHER = 2;

    public MessageAdapter(List<ChatMessage> messageList, String currentUserName) {
        this.messageList = messageList;
        this.currentUserName = currentUserName;
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage message = messageList.get(position);
        if (message.getSender() != null && message.getSender().equals(currentUserName)) {
            return TYPE_ME;
        } else {
            return TYPE_OTHER;
        }
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_ME) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_me, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_other, parent, false);
        }
        return new MessageViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        ChatMessage message = messageList.get(position);
        holder.tvMessage.setText(message.getMessage());
    }

    @Override
    public int getItemCount() {
        return messageList != null ? messageList.size() : 0;
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView tvMessage;

        public MessageViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            if (viewType == TYPE_ME) {
                tvMessage = itemView.findViewById(R.id.tv_message_me);
            } else {
                tvMessage = itemView.findViewById(R.id.tv_message_other);
            }
        }
    }
}
