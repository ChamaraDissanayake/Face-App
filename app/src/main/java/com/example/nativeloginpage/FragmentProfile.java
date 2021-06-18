package com.example.nativeloginpage;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentProfile extends Fragment {
    Context mContext;
    TextView txtShowUniversity, txtShowName ;
    CircleImageView profile_image;

    public FragmentProfile() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        mContext = getContext();
        requireView().findViewById(R.id.btnEditProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), EditProfile.class));
            }
        });

        requireView().findViewById(R.id.loutEditProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), EditProfile.class));
            }
        });

        requireView().findViewById(R.id.loutAddMedia).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), EditProfile.class));
            }
        });


        requireView().findViewById(R.id.btnSettings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),Settings.class));
            }
        });
        profile_image = requireView().findViewById(R.id.profile_image);
        txtShowUniversity = requireView().findViewById(R.id.txtShowUniversity);
        txtShowName = requireView().findViewById(R.id.txtShowName);

//        for(MyProfile myProfile : Objects.requireNonNull(loadMyProfile(mContext))){
////            mSwipeView.addView(new ProfilesCard(mContext, profile, mSwipeView));
//            EditProfile tinderMyProfile = new EditProfile(mContext, myProfile);
//        }
//        loadMyProfile(mContext);
        Glide.with(mContext).load(Tabs.getProfileImage()).into(profile_image);
        txtShowName.setText(Tabs.getProfileName() + ", " + Tabs.getProfileAge());
        txtShowUniversity.setText(Tabs.getProfileUniversity());
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),MyProfileView.class));
            }
        });
    }

    public static @Nullable List<MyProfile> loadMyProfile(Context context){
        try{
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
//            JSONObject jsonObject = new JSONObject(loadJSONFromAsset(context,"myProfile.json"));
            JSONArray array = new JSONArray(loadJSONFromAsset(context, "myProfile.json"));
//            String test = jsonObject.toString();
//            JSONObject ob = new JSONObject(test);
//            JSONArray arr = ob.getJSONArray("myPassions");
//
//            for(int i=0; i<arr.length(); i++){
//                JSONObject o = arr.getJSONObject(i);
//                Log.i("TEST2","test "+ o.getString("id"));
//            }

            List<MyProfile> myProfileArray = new ArrayList<>();
            MyProfile myProfile1 = gson.fromJson(array.getString(0), MyProfile.class);
//            Profile profile = gson.fromJson(array.getString(i), Profile.class);
            myProfileArray.add(myProfile1);
//            MyProfile myProfile1 = gson.fromJson(jsonObject.getString("myJob"), MyProfile.class);
//            myProfile.add(myProfile1);
//            Log.i("TEST2", (myProfile1).toString());
            return myProfileArray;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private static @Nullable String loadJSONFromAsset(@NotNull Context context, String jsonFileName) {
        String json = null;
        InputStream is=null;
        try {
            AssetManager manager = context.getAssets();
            is = manager.open(jsonFileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}