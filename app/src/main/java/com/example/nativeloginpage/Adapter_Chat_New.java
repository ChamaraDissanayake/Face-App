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

public class Adapter_Chat_New extends RecyclerView.Adapter<Adapter_Chat_New.ViewHolder> {

    private ArrayList<String> mChatImage = new ArrayList<>();
    private ArrayList<String> mChatName = new ArrayList<>();
    private Context mContext;

    public Adapter_Chat_New(Context mContext, ArrayList<String> mChatImage, ArrayList<String> mChatName) {
        this.mChatImage = mChatImage;
        this.mChatName = mChatName;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public Adapter_Chat_New.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_list_view_new, parent, false);
        Adapter_Chat_New.ViewHolder holder = new Adapter_Chat_New.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Chat_New.ViewHolder holder, int position) {
        Glide.with(mContext)
                .asBitmap()
                .load(mChatImage.get(position))
                .into(holder.chatImage);
        holder.chatName.setText(mChatName.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),PrivateChat.class);
                intent.putExtra("ChatId", mChatName.get(position));
                intent.putExtra("ChatImage", mChatImage.get(position));
                v.getContext().startActivity(intent);
//                Toast.makeText(mContext, mChatName.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mChatName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView chatImage;
        TextView chatName;
        ConstraintLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            chatImage = (CircleImageView) itemView.findViewById(R.id.chat_image_new);
            chatName = (TextView) itemView.findViewById(R.id.chat_name_new);
            parentLayout = (ConstraintLayout) itemView.findViewById(R.id.parent_layout_new);
        }
    }
}
