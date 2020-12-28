package com.example.nativeloginpage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{
    private static final String TAG = "ChatAdapter";

    private ArrayList<String> mChatImage = new ArrayList<>();
    private ArrayList<String> mChatName = new ArrayList<>();
//    private ArrayList<String> mChatScrap = new ArrayList<>();
    private Context mContext;

    public ChatAdapter(Context mContext, ArrayList<String> mChatImage, ArrayList<String> mChatName) {
//public ChatAdapter(Context mContext, ArrayList<String> mChatImage, ArrayList<String> mChatName, ArrayList<String> mChatScrap) {
        this.mChatImage = mChatImage;
        this.mChatName = mChatName;
//        this.mChatScrap = mChatScrap;
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

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, mChatImage.get(position),Toast.LENGTH_LONG).show();
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
            chatImage = itemView.findViewById(R.id.chat_image);
            chatName = itemView.findViewById(R.id.chat_name);
            chatScrap = itemView.findViewById(R.id.parent_layout);
        }
    }
}
