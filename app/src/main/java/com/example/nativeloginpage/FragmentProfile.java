package com.example.nativeloginpage;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

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
    Context myContext;
    public FragmentProfile() { }

    CircleImageView imgEditInfo;

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
        myContext = getContext();
        getView().findViewById(R.id.imgEditInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),TinderMyProfile.class));
            }
        });
        getView().findViewById(R.id.imgSettings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),Settings.class));
            }
        });

        for(MyProfile myProfile : loadMyProfile(myContext)){
//            mSwipeView.addView(new TinderCard(mContext, profile, mSwipeView));
            TinderMyProfile tinderMyProfile = new TinderMyProfile(myContext, myProfile);
        }
//        loadMyProfile(myContext);
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