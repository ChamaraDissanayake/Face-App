package com.example.nativeloginpage;

import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;

import org.json.JSONException;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

@Layout(R.layout.tinder_card_view)
public class TinderCard{

    @View(R.id.profileImageView)
    private ImageView profileImageView;

    @View(R.id.nameAgeTxt)
    private TextView nameAgeTxt;

    @View(R.id.locationNameTxt)
    private TextView locationNameTxt;

    @View(R.id.btnNext)
    private Button next;

    @View(R.id.btnBack)
    private Button back;

    @View(R.id.imageFooter)
    private LinearLayout imageFooter;

    @View(R.id.loutProfileDetails)
    private LinearLayout loutProfileDetails;

    @View(R.id.txtNameAgeProfile)
    private TextView txtNameAgeProfile;

    @View(R.id.txtUniversity)
    private TextView txtUniversity;

    @View(R.id.btnMinimize)
    private CircleImageView btnMinimize;

    @View(R.id.sex)
    private TextView sex;

    @View(R.id.txtDescription)
    private TextView txtDescription;

    @View(R.id.txtShareProfile)
    private TextView txtShareProfile;

    @View(R.id.btnReport)
    private Button btnReport;

    private Profile mProfile;
    private Context mContext;
    private SwipePlaceHolderView mSwipeView;
    private ArrayList<String> al;
    private int imageIndex;

    public TinderCard(Context context, Profile profile, SwipePlaceHolderView swipeView) {
        mContext = context;
        mProfile = profile;
        mSwipeView = swipeView;
        imageIndex = 0;

        al = new ArrayList<String>();
        al.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.1.jpg");
        al.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.2.jpg");
        al.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.3.jpg");
        al.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.4.jpg");
    }


    @Resolve
    private void onResolved(){
        Log.d("EVENT","onResolved");
        int numberOfPhotos = al.size();
        Glide.with(mContext).load(mProfile.getImageUrl()).into(profileImageView);
//        Glide.with(mContext).load(mProfile.getPhotos()).into(profileImageView);
        nameAgeTxt.setText(mProfile.getName() + ", " + mProfile.getAge());
        locationNameTxt.setText(mProfile.getLocation());
        txtDescription.setText(mProfile.getDescription());
        txtShareProfile.setText("SHARE " + mProfile.getName().toUpperCase() + "'S PROFILE");
        btnReport.setText("REPORT " + mProfile.getName().toUpperCase());
//        Log.i("TEST2", "sex"+mProfile.isFemale());
        if(mProfile.isFemale()){
            sex.setText("Women");
        } else {
            sex.setText("Man");
        }
//        txtNo.setText(mProfile.getIdentity());
//        for (int i=0; i<al.size(); i++){
//            Log.i("TEST2", al.get(i));
//            Glide.with(mContext).load(al.get(i)).into(profileImageView);
//        }
//        Glide.with(mContext).load(al.get(imageIndex)).into(profileImageView);
        txtNameAgeProfile.setText(mProfile.getName() + ", " + mProfile.getAge());
        txtUniversity.setText(mProfile.getUniversity());
        next.setOnClickListener(v -> {
//            getImageIndex();
            if(imageIndex<numberOfPhotos-1){
                setImageIndex(imageIndex+1);
                Glide.with(mContext).load(al.get(imageIndex)).into(profileImageView);
            }
        });

        back.setOnClickListener(v -> {
//            getImageIndex();
            if(imageIndex>0){
                setImageIndex(imageIndex-1);
                Glide.with(mContext).load(al.get(imageIndex)).into(profileImageView);
            }
        });

        imageFooter.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                imageFooter.setVisibility(android.view.View.GONE);
//                tabs.setVisibility(android.view.View.GONE);
                loutProfileDetails.setVisibility(android.view.View.VISIBLE);
                mSwipeView.lockViews();
//                final TabLayout tabs = (TabLayout)getActivity().findViewById(R.id.tabs);
//                tabs.setVisibility(android.view.View.GONE);
//                Button btnTest = getActivity().findViewById(R.id.btnRewind);
                
            }
        });

        btnMinimize.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                loutProfileDetails.setVisibility(android.view.View.GONE);
                imageFooter.setVisibility(android.view.View.VISIBLE);
                mSwipeView.unlockViews();
            }
        });
    }

    @SwipeOut
    private void onSwipedOut(){
        Log.d("EVENT", "onSwipedOut");
    }

    @SwipeCancelState
    private void onSwipeCancelState(){
        Log.d("EVENT", "onSwipeCancelState");
    }

    @SwipeIn
    private void onSwipeIn() throws JSONException { Log.d("EVENT", "onSwipedIn"); }

    @SwipeInState
    private void onSwipeInState(){
        Log.d("EVENT", "onSwipeInState");
    }

    @SwipeOutState
    private void onSwipeOutState(){
        Log.d("EVENT", "onSwipeOutState");
    }

    public int getImageIndex() {
        return imageIndex;
    }

    public void setImageIndex(int imageIndex) {
        this.imageIndex = imageIndex;
    }
}