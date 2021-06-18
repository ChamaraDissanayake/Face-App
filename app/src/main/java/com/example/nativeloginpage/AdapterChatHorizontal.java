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

public class AdapterChatHorizontal extends RecyclerView.Adapter<AdapterChatHorizontal.ViewHolder> {

    private ArrayList<String> mChatImage = new ArrayList<>();
    private ArrayList<String> mChatName = new ArrayList<>();
    private ArrayList<String> mChatOpponentId = new ArrayList<>();
    private final Context mContext;

    public AdapterChatHorizontal(Context mContext, ArrayList<String> mChatImage, ArrayList<String> mChatName, ArrayList<String> mChatOpponentId) {
        this.mChatImage = mChatImage;
        this.mChatName = mChatName;
        this.mContext = mContext;
        this.mChatOpponentId = mChatOpponentId;
    }

    @NonNull
    @Override
    public AdapterChatHorizontal.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_list_view_new, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterChatHorizontal.ViewHolder holder, int position) {
        Glide.with(mContext)
                .asBitmap()
                .load(mChatImage.get(position))
                .into(holder.chatImage);
        holder.chatName.setText(mChatName.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),PrivateChat.class);
                if(!mChatOpponentId.get(position).equals("0")){
                    intent.putExtra("ChatId", mChatOpponentId.get(position));
                    intent.putExtra("ChatImage", mChatImage.get(position));
                    intent.putExtra("ChatName", mChatName.get(position));
                    v.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mChatName.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
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
