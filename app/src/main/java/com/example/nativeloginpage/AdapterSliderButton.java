package com.example.nativeloginpage;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterSliderButton extends RecyclerView.Adapter<AdapterSliderButton.ViewHolder> {

    private ArrayList<String> mSliderButton = new ArrayList<>();
    Button test;

    public AdapterSliderButton(ArrayList<String> mSliderButton) {
        this.mSliderButton = mSliderButton;
    }

    @NonNull
    @Override
    public AdapterSliderButton.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_button_view, parent, false);
        AdapterSliderButton.ViewHolder holder = new AdapterSliderButton.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSliderButton.ViewHolder holder, int position) {
        int getColorWhite =  holder.btnSlider.getContext().getResources().getColor(R.color.white);
        int getColorTransparent = holder.btnSlider.getContext().getResources().getColor(R.color.half_transparent);
        holder.btnSlider.setBackgroundColor(getColorTransparent);

        if(position==0){
            holder.btnSlider.setBackgroundColor(getColorWhite);
        }

        holder.btnSlider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.btnSlider.setBackgroundColor(getColorWhite);
                Log.i("TEST2", "onBindViewHolderOnClick ");

            }
        });

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
