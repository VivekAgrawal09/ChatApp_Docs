package com.example.vivek.myapplication_chat;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vivek on 24/9/17.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    List<ChatMessage> messages = new ArrayList<>();

    public ChatAdapter(List<ChatMessage> messages) {
        this.messages =  messages;
    }

    public void setList(ArrayList<ChatMessage> messages) {
        this.messages = messages;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView message;
        LinearLayout messageContainer;
        LinearLayout chatLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            message = (TextView) itemView.findViewById(R.id.message);
            messageContainer = (LinearLayout) itemView.findViewById(R.id.messageContainer);
            chatLayout = (LinearLayout) itemView.findViewById(R.id.chatlayout);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_message, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.message.setText(messages.get(position).getMessage());
        if(!messages.get(position).isLeft()) {
            holder.messageContainer.setBackgroundResource(R.drawable.bubble_b);
            holder.chatLayout.setGravity(Gravity.RIGHT);
        }
        else {
            holder.messageContainer.setBackgroundResource(R.drawable.bubble_a);
            holder.chatLayout.setGravity(Gravity.LEFT);
        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
