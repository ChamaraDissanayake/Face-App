package com.example.nativeloginpage;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class FragmentChat extends Fragment implements SearchView.OnQueryTextListener {

    public FragmentChat() { }

    SearchView searchFriend;
    View v;
    private final ArrayList<String> mChatImage = new ArrayList<>();
    private final ArrayList<String> mChatImageNew = new ArrayList<>();
    private final ArrayList<String> mChatName = new ArrayList<>();
    private final ArrayList<String> mChatNameNew = new ArrayList<>();
    private final ArrayList<String> mChatOpponentId = new ArrayList<>();
    private final ArrayList<String> mChatOpponentIdNew = new ArrayList<>();
    private final ArrayList<String> mChatScrap = new ArrayList<>();
    private final ArrayList<String> testArray = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void clearData() {
        mChatImage.clear();
        mChatName.clear();
        mChatImageNew.clear();
        mChatNameNew.clear();
        mChatScrap.clear();
        mChatOpponentId.clear();
        mChatOpponentIdNew.clear();
        testArray.clear();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_chat, container, false);
//        initImageBitmapsNew();
        clearData();
//        initImageBitmaps();
        getMatches();
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        searchFriend = requireView().findViewById(R.id.searchFriend);
        searchFriend.setOnQueryTextListener(this);
    }

//    private void initImageBitmaps(){
//        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image10.jpg");
//        mChatName.add("Test 1");
//        mChatScrap.add("Hi");
//
//        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image11.jpg");
//        mChatName.add("Test 2");
//        mChatScrap.add("Where are you?");
//
//        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image12.jpg");
//        mChatName.add("Test 3");
//        mChatScrap.add("Hi there");
//
//        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image13.jpg");
//        mChatName.add("Test 4");
//        mChatScrap.add("Are you ok?");
//
//        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image2.jpg");
//        mChatName.add("Test 1");
//        mChatScrap.add("Hi");
//
//        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image3.jpg");
//        mChatName.add("Test 2");
//        mChatScrap.add("Where are you?");
//
//        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image4.jpg");
//        mChatName.add("Test 3");
//        mChatScrap.add("Hi there");
//
//        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image5.jpg");
//        mChatName.add("Test 4");
//        mChatScrap.add("Are you ok?");
//
//        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image6.jpg");
//        mChatName.add("Test 1");
//        mChatScrap.add("Hi");
//
//        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image7.jpg");
//        mChatName.add("Test 2");
//        mChatScrap.add("Where are you?");
//
//        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image8.jpg");
//        mChatName.add("Test 3");
//        mChatScrap.add("Hi there");
//
//        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image9.jpg");
//        mChatName.add("Test 4");
//        mChatScrap.add("Are you Ok?");
//
//        initRecycleView();
//    }

    private void initRecycleView(){
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.rvChatted);
        AdapterChat adapter = new AdapterChat(getContext(), mChatImage, mChatName, mChatScrap, mChatOpponentId);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

//    private void initImageBitmapsNew(){
//        mChatImageNew.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.1.jpg");
//        mChatNameNew.add("Test 1");
//
//        mChatImageNew.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.2.jpg");
//        mChatNameNew.add("Test 2");
//
//        mChatImageNew.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.3.jpg");
//        mChatNameNew.add("Test 3");
//
//        mChatImageNew.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.4.jpg");
//        mChatNameNew.add("Test 4");
//
//        mChatImageNew.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.1.jpg");
//        mChatNameNew.add("Test 1");
//
//        mChatImageNew.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.2.jpg");
//        mChatNameNew.add("Test 2");
//
//        mChatImageNew.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.3.jpg");
//        mChatNameNew.add("Test 3");
//
//        mChatImageNew.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.4.jpg");
//        mChatNameNew.add("Test 4");
//
//        mChatImageNew.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.1.jpg");
//        mChatNameNew.add("Test 1");
//
//        mChatImageNew.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.2.jpg");
//        mChatNameNew.add("Test 2");
//
//        mChatImageNew.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.3.jpg");
//        mChatNameNew.add("Test 3");
//
//        mChatImageNew.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.4.jpg");
//        mChatNameNew.add("Test 4");

//        initRecycleViewNew();
//    }

    private void initRecycleViewNew(){
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.rvChatNew);
        AdapterChatHorizontal adapter = new AdapterChatHorizontal(getContext(),mChatImageNew, mChatNameNew, mChatOpponentIdNew);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
    }


    private void getMatches(){
        String postUrl = "http://faceapp.vindana.com.au/api/v1/faceapp/getmatch";
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        JSONObject postData = new JSONObject();
        try {
            postData.put("fbId", Tabs.getProfileId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray pd = new JSONArray();
        pd.put(postData);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, postUrl, pd, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("TEST2", "response in getMatches " + response);
                try{
                    mChatImageNew.add("https://faceapp.s3.ap-south-1.amazonaws.com/upload/thumbnail/2021/06/8c196031e6_1623315846.jpg");
                    mChatNameNew.add(response.length() + " Liked");
                    mChatOpponentIdNew.add("0");

                    for(int i=0;i<response.length();i++){
                        JSONObject jsonObject = response.getJSONObject(i);
//                        int len = jsonObject.get("opntProfileImage").toString().length()-2;
//                        String opntProfileImage = jsonObject.get("opntProfileImage").toString().substring(2,len);
//                        String opntProfileImage1 = opntProfileImage.replace("\\","");
//                        Log.i("TEST2", "loop opntProfileImage: "+ opntProfileImage1);
                        if(jsonObject.getBoolean("conversationStatus")){
                            mChatImage.add(jsonObject.getString("opntProfileImage"));
                            mChatName.add(jsonObject.getString("opntName"));
                            mChatScrap.add("Hi " + jsonObject.getString("opntName"));
                            mChatOpponentId.add(jsonObject.getString("opntFbId"));
                        }else{
                            mChatImageNew.add(jsonObject.getString("opntProfileImage"));
                            mChatNameNew.add(jsonObject.getString("opntName"));
                            mChatOpponentIdNew.add(jsonObject.getString("opntFbId"));
                        }
                    }
                    initRecycleView();
                    initRecycleViewNew();
                }catch (Exception e){
                    Log.i("TEST2", "error in getMatches loop: " + e);
                }
            }
        }, error -> {
            error.printStackTrace();
            Log.i("TEST2", "error in getMatches: " + error);
            mChatImageNew.add("https://faceapp.s3.ap-south-1.amazonaws.com/upload/thumbnail/2021/06/8c196031e6_1623315846.jpg");
            mChatNameNew.add("0 Liked");
            mChatOpponentIdNew.add("0");
            initRecycleViewNew();
        });
        requestQueue.add(jsonArrayRequest);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.i("TEST2", "TEXT CHANGED FINISHED " + query);
        filter(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
//        Log.i("TEST2", "TEXT CHANGED " + newText);
        filter(newText);
        return false;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        testArray.clear();
        if (charText.length() == 0) {
            testArray.addAll(mChatName);
        } else {
            for(int i=0;i<mChatName.size();i++){
                if (mChatName.get(i).toLowerCase(Locale.getDefault()).contains(charText)) {
                    testArray.add(mChatName.get(i));
                }
            }
        }
//        notifyDataSetChanged();
        Log.i("TEST2", "test array: " +testArray.get(0));
    }
}