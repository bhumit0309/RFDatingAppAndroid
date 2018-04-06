package com.example.macstudent.rfapp;

import java.time.format.DateTimeFormatter;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by macstudent on 2018-01-18.
 */

public class AndroidVersion extends RealmObject {

    private String displayName;
    private String signature;
    private String city;
    private int gender;
    private int height;
    private double latitude;
    private double longitude;
    private String mainImage;
    @PrimaryKey
    private String id;

//    public AndroidVersion(String displayName, String signature, String city, int gender, int height, double latitude, double longitude, String mainImage) {
//        this.displayName = displayName;
//        this.signature = signature;
//        this.city = city;
//        this.gender = gender;
//        this.height = height;
//        this.latitude = latitude;
//        this.longitude = longitude;
//        this.mainImage = mainImage;
//    }

    public AndroidVersion() {
    }

    public AndroidVersion(String displayName, String signature, String city, int gender, int height, double latitude, double longitude, String mainImage, String id) {
        setDisplayName(displayName);
        setSignature(signature);
        setCity(city);
        setGender(gender);
        setHeight(height);
        setLatitude(latitude);
        setLongitude(longitude);
        setMainImage(mainImage);
        setId(id);
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getGender()
    {
        return gender;
    }

    public String getSignature() {
        return signature;
    }

    public String getCity() {
        return city;
    }

    public int getHeight() { return height; }

    public double getLatitude() { return latitude; }

    public double getLongitude() { return longitude; }

    public String getMainImage() {
        return mainImage;
    }

    public String getId() { return id; }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public void setId(String id) {
        this.id = id;
    }
}