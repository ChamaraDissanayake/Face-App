package com.example.nativeloginpage;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class FragmentRequests extends Fragment {

    View v;
    Context mContext;
//    private ArrayList<String> mChatImage = new ArrayList<>();
//    private ArrayList<String> mChatName = new ArrayList<>();
    private ArrayList<String> al;

    ViewPager viewPager;
    LinearLayout sliderDotsPanel;
    private int dotsCount;
    private ImageView[] dots;
    Button btnNext, btnBack;

    public FragmentRequests() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        v = inflater.inflate(R.layout.fragment_requests, container, false);
//        initImageBitmapsNew();
//        initPhotoSet();
//        return v;
        return inflater.inflate(R.layout.fragment_requests, container, false);
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        v=getView();
//        ImageSlider imageSlider = v.findViewById(R.id.mySlider);
//
//        List<SlideModel> slideModels = new ArrayList<>();
//        slideModels.add(new SlideModel("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.1.jpg", ScaleTypes.CENTER_CROP));
//        slideModels.add(new SlideModel("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.2.jpg", ScaleTypes.CENTER_CROP));
//        slideModels.add(new SlideModel("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.3.jpg", ScaleTypes.CENTER_CROP));
//        slideModels.add(new SlideModel("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.4.jpg", ScaleTypes.CENTER_CROP));
//        imageSlider.setImageList(slideModels);
//
//        imageSlider.setItemClickListener(new ItemClickListener() {
//            @Override
//            public void onItemSelected(int i) {
//                Log.i("TEST2", "clicked "+ i);
//            }
//        });
//    }

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

//    private void initPhotoSet(){
//        al.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.1.jpg");
//        al.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.2.jpg");
//        al.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.3.jpg");
//        al.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.4.jpg");
//
//        initRecycleView();
//    }
//
//    private void initRecycleView(){
//        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.rvChats);
//        AdapterSliderButton adapter = new AdapterSliderButton(al);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
//    }
//        ViewPager viewPager;
//        LinearLayout sliderDotspanel;
//        private int dotscount;
//        private ImageView[] dots;
//        Button button;
//    }
//    @Override
    public void onStart() {
        super.onStart();

        al = new ArrayList<String>();
        initPhotoSet();

        btnNext = (Button) getView().findViewById(R.id.btnNext);

        btnBack = (Button) getView().findViewById(R.id.btnBack);

        viewPager = (ViewPager) getView().findViewById(R.id.viewPager);

        sliderDotsPanel = (LinearLayout) getView().findViewById(R.id.SliderDots);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getContext(),al);

        viewPager.setAdapter(viewPagerAdapter);

        dotsCount = viewPagerAdapter.getCount();

        dots = new ImageView[dotsCount];

        for(int i = 0; i < dotsCount; i++){

            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotsPanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotsCount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.nonactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewPager.getCurrentItem() == 0){
                    viewPager.setCurrentItem(1);
                } else if(viewPager.getCurrentItem() == 1){
                    viewPager.setCurrentItem(2);
                } else {
                    viewPager.setCurrentItem(0);
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewPager.getCurrentItem() == 0){
                    viewPager.setCurrentItem(2);
                } else if(viewPager.getCurrentItem() == 1){
                    viewPager.setCurrentItem(0);
                } else {
                    viewPager.setCurrentItem(1);
                }
            }
        });
    }

    private void initPhotoSet(){
        al.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.1.jpg");
        al.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.2.jpg");
        al.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.3.jpg");
        al.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.4.jpg");


    }
}