package com.example.nativeloginpage;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EditProfile extends AppCompatActivity {

    private static final String ROOT_URL = "http://faceapp.vindana.com.au/api/v1/faceapp/imageUpload";
    private static final int REQUEST_PERMISSIONS = 100;
    private static final int PICK_IMAGE_REQUEST =1 ;
//    private static int RESULT_LOAD_IMAGE = 1;
    private Context myContext;
//    private MyProfile myProfile;
//    FlowLayout loutSetImages;
    private final ArrayList<String> imgSet = new ArrayList<>();
    TextView txtMyDescription, txtMyPassions, txtMyJob, txtMyUniversity, txtMyCompany, txtMyCity;
    Button btnEditProfileBack;
    RadioButton btnEditWoman, btnEditMan;
    private boolean isFemale;
    String myPassions;
    ArrayList<String> selectedPassionsList = new ArrayList<String>();
    LinearLayout btnAddImage;
    private Bitmap bitmap;
    private String filePath;
//    SwitchCompat tglBtnAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Objects.requireNonNull(getSupportActionBar()).hide();


        myContext = getApplicationContext();

//        loutSetImages = findViewById(R.id.loutSetImages);
        btnAddImage = findViewById(R.id.btnAddImage);
        txtMyDescription = findViewById(R.id.txtMyDescription);
        txtMyPassions = findViewById(R.id.txtMyPassions);
        txtMyJob = findViewById(R.id.txtMyJob);
        btnEditProfileBack = findViewById(R.id.btnEditProfileBack);
        btnEditWoman = findViewById(R.id.btnEditWoman);
        btnEditMan = findViewById(R.id.btnEditMan);
        txtMyUniversity = findViewById(R.id.txtMyUniversity);
        txtMyCompany = findViewById(R.id.txtMyCompany);
        txtMyCity = findViewById(R.id.txtMyCity);
//        tglBtnAge = findViewById(R.id.tglBtnAge);

//        tglBtnAge.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(tglBtnAge.isChecked()){
//                    tglBtnAge.setThumbTintList(Color.);
//                }
//            }
//        });
//        isFemale = Tabs.getProfileIsFemale();
//        txtMyDescription.setText(Tabs.getProfileDescription());
//        txtMyUniversity.setText(Tabs.getProfileUniversity());
//        txtMyJob.setText(Tabs.getProfileJob());
//        txtMyCompany.setText(Tabs.getProfileCompany());
//        txtMyCity.setText(Tabs.getProfileCity());
//        ArrayList<String> arr = Tabs.getProfilePassions();
//
//        try{
//            myPassions = "";
//            for(int i = 0; i < arr.size()-1; i++){
//                myPassions = myPassions + arr.get(i) + ", ";
//            }
//            myPassions = myPassions + arr.get(arr.size()-1);
//            txtMyPassions.setText(myPassions);
//        } catch (Exception e){
//            Log.i("TEST2", e.toString());
//        }
//        new Handler().postDelayed(() -> getDataFromDB(Tabs.getProfileId()), 5000);
        getDataFromDB(Tabs.getProfileId());
        initImageSet();
//        try{
//            if(getIntent().hasExtra("myEditedPassions")) {
//                selectedPassionsList.clear();
//                selectedPassionsList = getIntent().getStringArrayListExtra("myEditedPassions");
//            }
//        } catch (Exception e){
//            Toast.makeText(EditProfile.this, "Direct"+ e,Toast.LENGTH_LONG).show();
//        }

        txtMyPassions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TEST2", "Clicked");
                startActivity(new Intent(EditProfile.this, GetPassionsEdit.class).putStringArrayListExtra("myPassionsList",selectedPassionsList));
            }
        });

//        txtMyJob.setText(Tabs.getProfileJob());, company, city
//        txtMyPassions.setText((CharSequence) Tabs.getProfilePassions());
        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isFull = true;
                for(int i =0; i<imgSet.size(); i++){
                    if(imgSet.get(i).equals("")){
                        isFull = false;
                        break;
                    }
                }
                if(!isFull){

                    if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                        if ((ActivityCompat.shouldShowRequestPermissionRationale(EditProfile.this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(EditProfile.this,
                                Manifest.permission.READ_EXTERNAL_STORAGE))) {
                            Log.i("If", "If");
                        } else {
                            ActivityCompat.requestPermissions(EditProfile.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                    REQUEST_PERMISSIONS);
                        }
                    } else {
                        Log.e("Else", "Else");
                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent, PICK_IMAGE_REQUEST);
                    }
//                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//                    photoPickerIntent.setType("image/*");
//                    startActivityForResult(photoPickerIntent, RESULT_LOAD_IMAGE);
                }else{
                    Toast.makeText(EditProfile.this,"Maximum limit reached", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnEditProfileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                updateProfileData();
                finish();
            }
        });
    }

//    private void getImageFromAlbum() {
//        try {
//            Intent i = new Intent(Intent.ACTION_PICK,
//                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//            startActivityForResult(i, RESULT_LOAD_IMAGE);
//        } catch (Exception exp) {
//            Log.i("Error", exp.toString());
//        }
//    }


    private void updateProfileData(){
        String postUrl = "http://faceapp.vindana.com.au/api/v1/faceapp/editprofile";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        JSONArray jsonPassions = new JSONArray(selectedPassionsList);
        JSONObject postData = new JSONObject();
        try {
            postData.put("fbId", Tabs.getProfileId());
            postData.put("showGender", false);
            postData.put("myUniversity", txtMyUniversity.getText());
//            postData.put("myPassions", jsonPassions);
            postData.put("myLocation", Tabs.getProfileLocation());      //Test data
            postData.put("myAge", "35");                         //Need to remove
            postData.put("myJob", txtMyJob.getText());
            postData.put("myCompany", txtMyCompany.getText());
            postData.put("myCity", txtMyCity.getText());
            postData.put("myDescription", txtMyDescription.getText());
            postData.put("isFemale", isFemaleSelected());
//            postData.put("isFemale", isFemaleSelected());                    //Need to add
//            "myPhotos": ["<URL>”","<URL>"]                                    //Need to add
//            boolean test = isFemaleSelected();
//            Log.i("TEST2", "is female "+ test);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("TEST2","response"+ String.valueOf(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.i("TEST2", "error " + error);
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private boolean isFemaleSelected(){
        RadioButton gender;
        boolean isFemaleChecked;
        RadioGroup rdoEditIsFemale = findViewById(R.id.rdoEditIsFemale);
        int selectedId = rdoEditIsFemale.getCheckedRadioButtonId();
        gender = findViewById(selectedId);

        isFemaleChecked = String.valueOf(gender.getText()).equals("Woman"); //Set boolean value without using if statement
        return isFemaleChecked;
    }

//    private boolean isGenderVisible(){
//        SwitchCompat
//    }

    private void initImageSet(){
        imgSet.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image1.1.jpg");
        imgSet.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image2.jpg");
        imgSet.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image3.jpg");
        imgSet.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image4.jpg");
        imgSet.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image5.jpg");
        imgSet.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image6.jpg");
        imgSet.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image7.jpg");
        imgSet.add("https://vu-content.s3-ap-southeast-1.amazonaws.com/uploads/app/faceapp/Image8.jpg");
        imgSet.add("https://faceapp.s3.ap-south-1.amazonaws.com/upload/thumbnail/2021/02/c87e280c39_1613545554.jpg");
        imageSetRecyclerView();
//        setImageSet();
    }

    private void imageSetRecyclerView(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvAddImages);
        AdapterAddImages adapter = new AdapterAddImages(myContext, imgSet);
        recyclerView.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3,LinearLayoutManager.VERTICAL,false);
//        recyclerView.setLayoutManager(new LinearLayoutManager(myContext,LinearLayoutManager.HORIZONTAL,false));
//        recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
        recyclerView.setLayoutManager(gridLayoutManager);
    }

//    private void setImageSet(){
//        for(int i =0; i<imgSet.size(); i++) {
//            android.view.View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.image_set_view, loutSetImages, false);
//            CircleImageView btnAddImage = view.findViewById(R.id.btnAddImage);
//            loutSetImages.addView(view);
//            ImageView imgAdd = findViewById(R.id.imgAdd);
//
//
//            imgAdd.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.i("TEST2", "Working");
//                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//                    photoPickerIntent.setType("image/*");
//                    startActivityForResult(photoPickerIntent, RESULT_LOAD_IMAGE);
//                }
//            });
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri picUri = data.getData();
            filePath = getPath(picUri);
            if (filePath != null) {
                try {
                    Log.i("filePath", String.valueOf(filePath));
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), picUri);
                    Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap, 300, 500, false);
                    uploadBitmap(bitmap2);
                    ImageView imgAdd = findViewById(R.id.imgAdd);
                    imgAdd.setImageBitmap(bitmap2);
//                    imageView.setImageBitmap(bitmap2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                Toast.makeText(EditProfile.this,"no image selected", Toast.LENGTH_LONG).show();
            }
        }

//        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
//            Uri selectedImage = data.getData();
//            Log.i("TEST3", "image: " + selectedImage);
//            ImageView imgAdd = findViewById(R.id.imgAdd);
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
//                String image = getStringImage(bitmap);
//                Log.i("TEST3", "image: " + image);
//                imgAdd.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 240, 300, false));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }



    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void uploadBitmap(final Bitmap bitmap) {

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, ROOT_URL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), obj.getString("imagePath"), Toast.LENGTH_LONG).show();
                            for(int i =0; i<imgSet.size(); i++){
                                if(imgSet.get(i).equals("")){
                                    imgSet.remove(i);
                                    imgSet.add(i,obj.getString("imagePath"));
                                    Log.i("TEST3", "imageSet: "+ imgSet);
                                    imageSetRecyclerView();
                                    break;
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("GotError",""+error.getMessage());
                    }
                }) {


            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("image", new DataPart(imagename + ".jpg", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

//    public String getStringImage(Bitmap bmp){
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] imageBytes = baos.toByteArray();
//        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
//        return encodedImage;
//    }

    private void getDataFromDB(String fbId){
        String postUrl = "http://faceapp.vindana.com.au/api/v1/faceapp/getmyprofile";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject postData = new JSONObject();
        try {
            postData.put("fbId", fbId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("TEST2", String.valueOf(response));
//                if (!response.has("error")){
                    getExistingData(response);
//                } else {
//                    getDataFromFB();
//                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(EditProfile.this, "Please check your internet connection",Toast.LENGTH_LONG).show();
                finish();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void getExistingData(JSONObject response){
        try {
//            ArrayList<String> arrayList = new ArrayList<String>();
            try {
                selectedPassionsList.clear();
                JSONArray jsonArray = response.getJSONArray("myPassions");
                for(int i = 0; i < jsonArray.length(); i++){
                    selectedPassionsList.add(String.valueOf(jsonArray.getString(i)));
                }
            }catch (Exception e){
                Log.i("TEST2", "Error in passions" + e);
            }

            try{
                myPassions = "";
                for(int i = 0; i < selectedPassionsList.size()-1; i++){
                    myPassions = myPassions + selectedPassionsList.get(i) + ", ";
                }
                myPassions = myPassions + selectedPassionsList.get(selectedPassionsList.size()-1);
                txtMyPassions.setText(myPassions);
            } catch (Exception e){
                Log.i("TEST2", e.toString());
            }

            if(!response.getString("myDescription").equals("null")){
                txtMyDescription.setText(response.getString("myDescription"));
            }

            if(!response.getString("myJob").equals("null")){
                txtMyJob.setText(response.getString("myJob"));
            }

            if(!response.getString("myCompany").equals("null")){
                txtMyCompany.setText(response.getString("myCompany"));
            }

            if(!response.getString("myUniversity").equals("null")){
                txtMyUniversity.setText(response.getString("myUniversity"));
            }

            if(!response.getString("myCity").equals("null")){
                txtMyCity.setText(response.getString("myCity"));
            }

            isFemale = response.getBoolean("isFemale");
            if(isFemale){
                btnEditWoman.setChecked(true);
            }else {
                btnEditMan.setChecked(true);
            }

        } catch (Exception e){
            Log.i("TEST2", String.valueOf(e));
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(EditProfile.this, "Updating",Toast.LENGTH_LONG).show();
        updateProfileData();
    }
}