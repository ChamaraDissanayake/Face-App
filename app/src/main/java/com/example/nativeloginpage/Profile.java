package com.example.nativeloginpage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Profile {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("url")
    @Expose
    private String imageUrl;

    @SerializedName("zodiac")
    @Expose
    private String zodiac;

    @SerializedName("age")
    @Expose
    private Integer age;

    @SerializedName("location")
    @Expose
    private String location;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("university")
    @Expose
    private String university;

    @SerializedName("id")
    @Expose
    private int identity;

    @SerializedName("isFemale")
    @Expose
    private boolean isFemale;

    @SerializedName("photos")
    @Expose
    private List<PhotoSet> photos = null;
//    private List<PhotoSet> photos = new ArrayList<PhotoSet>();

    public List<PhotoSet> getPhotos() { return photos; }

    public void setPhotos(List<PhotoSet> photos) { this.photos = photos; }

    public int getIdentity() { return identity; }

    public void setIdentity(int identity) { this.identity = identity; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getImageUrl() { return imageUrl; }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getZodiac() { return zodiac; }

    public void setZodiac(String zodiac) { this.zodiac = zodiac; }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getUniversity() { return university; }

    public void setUniversity(String university) { this.university = university; }

    public boolean isFemale() { return isFemale; }

    public void setFemale(boolean female) { isFemale = female; }
}
