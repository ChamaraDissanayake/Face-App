package com.example.nativeloginpage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class FragmentRequests extends Fragment {

    View v;
    private ArrayList<String> mChatImage = new ArrayList<>();
    private ArrayList<String> mChatName = new ArrayList<>();
    private ArrayList<String> mChatScrap = new ArrayList<>();

    public FragmentRequests() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initImageBitmaps();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_requests, container, false);
        initImageBitmaps();
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void initImageBitmaps(){
        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.1.jpg");
        mChatName.add("Test 1");
        mChatScrap.add("Hi");

        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.2.jpg");
        mChatName.add("Test 2");
        mChatScrap.add("Where are you?");

        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.3.jpg");
        mChatName.add("Test 3");
        mChatScrap.add("Hi there");

        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.4.jpg");
        mChatName.add("Test 4");
        mChatScrap.add("Are you ok?");

        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.1.jpg");
        mChatName.add("Test 1");
        mChatScrap.add("Hi");

        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.2.jpg");
        mChatName.add("Test 2");
        mChatScrap.add("Where are you?");

        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.3.jpg");
        mChatName.add("Test 3");
        mChatScrap.add("Hi there");

        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.4.jpg");
        mChatName.add("Test 4");
        mChatScrap.add("Are you ok?");

        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.1.jpg");
        mChatName.add("Test 1");
        mChatScrap.add("Hi");

        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.2.jpg");
        mChatName.add("Test 2");
        mChatScrap.add("Where are you?");

        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.3.jpg");
        mChatName.add("Test 3");
        mChatScrap.add("Hi there");

        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.4.jpg");
        mChatName.add("Test 4");
        mChatScrap.add("Are you ok?");

        initRecycleView();
    }

    private void initRecycleView(){
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.rvChats);
        ChatAdapter adapter = new ChatAdapter(getContext(),mChatImage, mChatName, mChatScrap);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}