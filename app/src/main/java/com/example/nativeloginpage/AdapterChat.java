package com.example.nativeloginpage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterChat extends RecyclerView.Adapter<AdapterChat.ViewHolder>{

    private final ArrayList<String> mChatImage;
    private final ArrayList<String> mChatName;
    private final ArrayList<String> mChatScrap;
    private final ArrayList<String> mChatOpponentId;
    private final Context mContext;


    public AdapterChat(Context mContext, ArrayList<String> mChatImage, ArrayList<String> mChatName, ArrayList<String> mChatScrap, ArrayList<String> mChatOpponentId) {
        this.mChatImage = mChatImage;
        this.mChatName = mChatName;
        this.mChatScrap = mChatScrap;
        this.mChatOpponentId = mChatOpponentId;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_list_view, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(mContext)
                .asBitmap()
                .load(mChatImage.get(position))
                .into(holder.chatImage);
        holder.chatName.setText(mChatName.get(position));
        holder.chatScrap.setText(mChatScrap.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),PrivateChat.class);
                intent.putExtra("ChatName", mChatName.get(position));
                intent.putExtra("ChatImage", mChatImage.get(position));
                intent.putExtra("ChatId", mChatOpponentId.get(position));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mChatName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView chatImage;
        TextView chatName, chatScrap;
        ConstraintLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            chatImage = (CircleImageView) itemView.findViewById(R.id.chat_image);
            chatName = (TextView) itemView.findViewById(R.id.chat_name);
            chatScrap = (TextView) itemView.findViewById(R.id.chat_scrap);
            parentLayout = (ConstraintLayout) itemView.findViewById(R.id.parent_layout);
        }
    }
}
