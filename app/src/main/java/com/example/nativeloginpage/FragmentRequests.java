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

    private ArrayList<String> mChatImage = new ArrayList<>();
    private ArrayList<String> mChatName = new ArrayList<>();

    public FragmentRequests() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initImageBitmaps();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_requests, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
//        initImageBitmaps();
    }

    private void initImageBitmaps(){
        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.1.jpg");
        mChatName.add("Test1");

        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.2.jpg");
        mChatName.add("Test2");

        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.3.jpg");
        mChatName.add("Test3");

        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.4.jpg");
        mChatName.add("Test4");

//        initRecycleView();
    }

    private void initRecycleView(){
        RecyclerView recyclerView = getView().findViewById(R.id.rvChats);
        ChatAdapter adapter = new ChatAdapter(getContext(), mChatName, mChatImage);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}