package com.example.nativeloginpage;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterAddImages extends RecyclerView.Adapter<AdapterAddImages.ViewHolder>{
    private static int RESULT_LOAD_IMAGE = 1;
    private ArrayList<String> imgSet;
    private Context mContext;

    public AdapterAddImages(Context mContext, ArrayList<String> imgSet) {
        this.imgSet = imgSet;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public AdapterAddImages.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_set_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAddImages.ViewHolder holder, int position) {

        Glide.with(mContext)
                .asBitmap()
                .load(imgSet.get(position))
                .into(holder.imgAdd);
        holder.btnRemoveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDeleteImage(v.getContext(), position, holder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 9;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView btnRemoveImage;
        ImageView imgAdd;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnRemoveImage = itemView.findViewById(R.id.btnRemoveImage);
            imgAdd = itemView.findViewById(R.id.imgAdd);
        }
    }

//        @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
//            Uri selectedImage = data.getData();
//            ImageView imgAdd = findViewById(R.id.imgAdd);
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
//                imgAdd.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 240, 300, false));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public void alertDeleteImage(Context context, int position, AdapterAddImages.ViewHolder holder){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Are you sure, you want to delete this photo?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        imgSet.remove(position);
                        imgSet.add("");
                        Glide.with(mContext)
                                .asBitmap()
                                .load(imgSet.get(8))
                                .into(holder.imgAdd);
                        setImageArray(imgSet);
                        Toast.makeText(mContext,"Successfully deleted",Toast.LENGTH_LONG).show();
                    }
                });
        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(mContext,"Not deleted",Toast.LENGTH_LONG).show();
                    }
                });
        alertDialogBuilder.show();
    }

    private void setImageArray(ArrayList<String> imageSet){
        EditProfile editProfile = new EditProfile();
        editProfile.setImgSet(imageSet);
    }
}
