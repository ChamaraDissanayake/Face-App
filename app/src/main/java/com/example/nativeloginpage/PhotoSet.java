package com.example.nativeloginpage;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhotoSet {

    @SerializedName("url")
    @Expose
    private String url;

    public String getUrls()
    {
        Log.i("TEST2", "get success "+ url);

        return url;
    }

    public void setUrls(String url) {
        Log.i("TEST2", "set success "+ url);

        this.url = url;
    }
}
