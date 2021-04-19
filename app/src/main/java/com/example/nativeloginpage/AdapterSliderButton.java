package com.example.nativeloginpage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterSliderButton extends RecyclerView.Adapter<AdapterSliderButton.ViewHolder> {

    private ArrayList<String> mSliderButton = new ArrayList<>();
    int photoIndex;

    public AdapterSliderButton(ArrayList<String> mSliderButton, int photoIndex) {
        this.mSliderButton = mSliderButton;
        this.photoIndex = photoIndex;
    }

    @NonNull
    @Override
    public AdapterSliderButton.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_button_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSliderButton.ViewHolder holder, int position) {
        int screenWidth = FragmentHome.getScreenWidth()-30;
        int numberOfImages = mSliderButton.size();
        int displayWidth = screenWidth/numberOfImages;
        int getColorWhite =  holder.btnSlider.getContext().getResources().getColor(R.color.white);
        int getColorTransparent = holder.btnSlider.getContext().getResources().getColor(R.color.half_transparent);
        holder.btnSlider.setBackgroundColor(getColorTransparent);
        holder.btnSlider.setWidth(displayWidth);

        if(position==photoIndex){
            holder.btnSlider.setBackgroundColor(getColorWhite);
        }
//
//        holder.btnSlider.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.btnSlider.setBackgroundColor(getColorWhite);
//                Log.i("TEST2", "onBindViewHolderOnClick ");
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return mSliderButton.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        Button btnSlider;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnSlider = (Button) itemView.findViewById(R.id.btnSlider);
        }
    }
}
