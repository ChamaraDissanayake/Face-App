package com.example.nativeloginpage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Profile {
    @SerializedName("user_name")
    @Expose
    private String name;

//    @SerializedName("url")
//    @Expose
//    private String imageUrl;

    @SerializedName("zodiac")
    @Expose
    private String zodiac;

    @SerializedName("age")
    @Expose
    private String age;

    @SerializedName("location")
    @Expose
    private String location;

    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("university")
    @Expose
    private String university;

    @SerializedName("user_id")
    @Expose
    private String identity;

    @SerializedName("isFemale")
    @Expose
    private boolean isFemale;

    @SerializedName("photos")
    @Expose
    private ArrayList photos;

    @SerializedName("passions")
    @Expose
    private ArrayList passions;


    public ArrayList getPassions() {
        return passions;
    }

    public void setPassions(ArrayList passions) {
        this.passions = passions;
    }

    public ArrayList getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList photos) {
        this.photos = photos;
    }

//    @SerializedName("photos")
//    @Expose
//    private List<PhotoSet> photos = null;
//    public List<PhotoSet> getPhotos() { return photos; }
//    public void setPhotos(List<PhotoSet> photos) { this.photos = photos; }

    public String getIdentity() { return identity; }

    public void setIdentity(String identity) { this.identity = identity; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

//    public String getImageUrl() { return imageUrl; }
//
//    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getZodiac() { return zodiac; }

    public void setZodiac(String zodiac) { this.zodiac = zodiac; }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
//
//    public String getLocation() { return location; }
//
//    public void setLocation(String location) {
//        this.location = location;
//    }

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getUniversity() { return university; }

    public void setUniversity(String university) { this.university = university; }

    public boolean isFemale() { return isFemale; }

    public void setFemale(boolean female) { isFemale = female; }
}
