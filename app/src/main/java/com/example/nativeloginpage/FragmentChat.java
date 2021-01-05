package com.example.nativeloginpage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FragmentChat extends Fragment {

    public FragmentChat() { }

    View v;
    private ArrayList<String> mChatImage = new ArrayList<>();
    private ArrayList<String> mChatName = new ArrayList<>();
    private ArrayList<String> mChatImageNew = new ArrayList<>();
    private ArrayList<String> mChatNameNew = new ArrayList<>();
    private ArrayList<String> mChatScrap = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_chat, container, false);
        initImageBitmapsNew();
        initImageBitmaps();
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void initImageBitmaps(){
        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image10.jpg");
        mChatName.add("Test 1");
        mChatScrap.add("Hi");

        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image11.jpg");
        mChatName.add("Test 2");
        mChatScrap.add("Where are you?");

        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image12.jpg");
        mChatName.add("Test 3");
        mChatScrap.add("Hi there");

        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image13.jpg");
        mChatName.add("Test 4");
        mChatScrap.add("Are you ok?");

        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image2.jpg");
        mChatName.add("Test 1");
        mChatScrap.add("Hi");

        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image3.jpg");
        mChatName.add("Test 2");
        mChatScrap.add("Where are you?");

        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image4.jpg");
        mChatName.add("Test 3");
        mChatScrap.add("Hi there");

        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image5.jpg");
        mChatName.add("Test 4");
        mChatScrap.add("Are you ok?");

        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image6.jpg");
        mChatName.add("Test 1");
        mChatScrap.add("Hi");

        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image7.jpg");
        mChatName.add("Test 2");
        mChatScrap.add("Where are you?");

        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image8.jpg");
        mChatName.add("Test 3");
        mChatScrap.add("Hi there");

        mChatImage.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image9.jpg");
        mChatName.add("Test 4");
        mChatScrap.add("Are you ok?");

        initRecycleView();
    }

    private void initRecycleView(){
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.rvChatted);
        Adapter_Chat adapter = new Adapter_Chat(getContext(), mChatImage, mChatName, mChatScrap);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void initImageBitmapsNew(){
        mChatImageNew.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.1.jpg");
        mChatNameNew.add("Test 1");

        mChatImageNew.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.2.jpg");
        mChatNameNew.add("Test 2");

        mChatImageNew.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.3.jpg");
        mChatNameNew.add("Test 3");

        mChatImageNew.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.4.jpg");
        mChatNameNew.add("Test 4");

        mChatImageNew.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.1.jpg");
        mChatNameNew.add("Test 1");

        mChatImageNew.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.2.jpg");
        mChatNameNew.add("Test 2");

        mChatImageNew.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.3.jpg");
        mChatNameNew.add("Test 3");

        mChatImageNew.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.4.jpg");
        mChatNameNew.add("Test 4");

        mChatImageNew.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.1.jpg");
        mChatNameNew.add("Test 1");

        mChatImageNew.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.2.jpg");
        mChatNameNew.add("Test 2");

        mChatImageNew.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.3.jpg");
        mChatNameNew.add("Test 3");

        mChatImageNew.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.4.jpg");
        mChatNameNew.add("Test 4");

        initRecycleViewNew();
    }

    private void initRecycleViewNew(){
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.rvChatNew);
        Adapter_Chat_New adapter = new Adapter_Chat_New(getContext(),mChatImageNew, mChatNameNew);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
    }
}