package com.example.nativeloginpage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyProfile {

    @SerializedName("myName")
    @Expose
    private String myName;

    @SerializedName("myUrl")
    @Expose
    private String myUrl;

    @SerializedName("myAge")
    @Expose
    private Integer myAge;

    @SerializedName("myLocation")
    @Expose
    private String myLocation;

    @SerializedName("myId")
    @Expose
    private int myId;

    @SerializedName("myJob")
    @Expose
    private String myJob;

    @SerializedName("myCompany")
    @Expose
    private String myCompany;

    @SerializedName("mySchool")
    @Expose
    private String mySchool;

    @SerializedName("myZodiac")
    @Expose
    private String myZodiac;

    @SerializedName("showGender")
    @Expose
    private boolean showGender;

    @SerializedName("showAge")
    @Expose
    private boolean showAge;

    @SerializedName("showDistance")
    @Expose
    private boolean showDistance;

    @SerializedName("myPhotos")
    @Expose
    private List<PhotoSet> myPhotos = null;

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public String getMyUrl() {
        return myUrl;
    }

    public void setMyUrl(String myUrl) {
        this.myUrl = myUrl;
    }

    public Integer getMyAge() {
        return myAge;
    }

    public void setMyAge(Integer myAge) {
        this.myAge = myAge;
    }

    public String getMyLocation() {
        return myLocation;
    }

    public void setMyLocation(String myLocation) {
        this.myLocation = myLocation;
    }

    public int getMyId() {
        return myId;
    }

    public void setMyId(int myId) {
        this.myId = myId;
    }

    public String getMyJob() {
        return myJob;
    }

    public void setMyJob(String myJob) {
        this.myJob = myJob;
    }

    public String getMyCompany() {
        return myCompany;
    }

    public void setMyCompany(String myCompany) {
        this.myCompany = myCompany;
    }

    public String getMySchool() {
        return mySchool;
    }

    public void setMySchool(String mySchool) {
        this.mySchool = mySchool;
    }

    public String getMyZodiac() {
        return myZodiac;
    }

    public void setMyZodiac(String myZodiac) {
        this.myZodiac = myZodiac;
    }

    public boolean isShowGender() {
        return showGender;
    }

    public void setShowGender(boolean showGender) {
        this.showGender = showGender;
    }

    public boolean isShowAge() {
        return showAge;
    }

    public void setShowAge(boolean showAge) {
        this.showAge = showAge;
    }

    public boolean isShowDistance() {
        return showDistance;
    }

    public void setShowDistance(boolean showDistance) {
        this.showDistance = showDistance;
    }

    public List<PhotoSet> getMyPhotos() {
        return myPhotos;
    }

    public void setMyPhotos(List<PhotoSet> myPhotos) {
        this.myPhotos = myPhotos;
    }
}
