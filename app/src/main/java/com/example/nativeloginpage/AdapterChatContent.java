package com.example.nativeloginpage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterChatContent extends RecyclerView.Adapter<AdapterChatContent.ViewHolder> {

    private ArrayList<String> mChatId = new ArrayList<>();
    private ArrayList<String> mChatContent = new ArrayList<>();

    public AdapterChatContent(ArrayList<String> mChatId, ArrayList<String> mChatContent) {
        this.mChatId = mChatId;
        this.mChatContent = mChatContent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_content_view, parent, false);
        AdapterChatContent.ViewHolder holder = new AdapterChatContent.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterChatContent.ViewHolder holder, int position) {
        holder.chatId = mChatId.get(position);
        if(holder.chatId.equals("1")){
            holder.sentMessages.setText(mChatContent.get(position));
            holder.receivedMessages.setVisibility(View.GONE);
        } else {
            holder.receivedMessages.setText(mChatContent.get(position));
            holder.sentMessages.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mChatContent.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView receivedMessages, sentMessages;
        String chatId;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            receivedMessages = (TextView) itemView.findViewById(R.id.received_message);
            sentMessages = (TextView) itemView.findViewById(R.id.sent_message);
        }
    }
}
