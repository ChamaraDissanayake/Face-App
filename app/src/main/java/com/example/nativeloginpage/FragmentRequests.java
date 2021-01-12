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
    private ArrayList<String> al;

    public FragmentRequests() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_requests, container, false);
//        initImageBitmapsNew();
        al = new ArrayList<String>();
        initPhotoSet();
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

//    private void initImageBitmapsNew(){
//        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.1.jpg");
//        mChatName.add("Test 1");
//
//        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.2.jpg");
//        mChatName.add("Test 2");
//
//        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.3.jpg");
//        mChatName.add("Test 3");
//
//        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.4.jpg");
//        mChatName.add("Test 4");
//
//        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.1.jpg");
//        mChatName.add("Test 1");
//
//        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.2.jpg");
//        mChatName.add("Test 2");
//
//        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.3.jpg");
//        mChatName.add("Test 3");
//
//        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.4.jpg");
//        mChatName.add("Test 4");
//
//        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.1.jpg");
//        mChatName.add("Test 1");
//
//        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.2.jpg");
//        mChatName.add("Test 2");
//
//        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.3.jpg");
//        mChatName.add("Test 3");
//
//        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.4.jpg");
//        mChatName.add("Test 4");
//
//        initRecycleViewNew();
//    }
//
//    private void initRecycleViewNew(){
//        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.rvChats);
//        AdapterChatHorizontal adapter = new AdapterChatHorizontal(getContext(),mChatImage, mChatName);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
//    }

    private void initPhotoSet(){
        al.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.1.jpg");
        al.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.2.jpg");
        al.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.3.jpg");
        al.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.4.jpg");

        initRecycleView();
    }

    private void initRecycleView(){
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.rvChats);
        AdapterSliderButton adapter = new AdapterSliderButton(al);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
    }
}