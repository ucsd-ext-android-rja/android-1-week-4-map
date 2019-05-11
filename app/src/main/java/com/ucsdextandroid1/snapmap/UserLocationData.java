package com.ucsdextandroid1.snapmap;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rjaylward on 2019-04-27
 */
public class UserLocationData {

    @SerializedName("color")
    private String color;
    @SerializedName("latitude")
    private double latitude;
    @SerializedName("longitude")
    private double longitude;
    @SerializedName("location_name")
    private String locationName;
    //added this custom annotation in class 4
    @SerializedName("user_id")
    @RemoveFromJson
    private String userId;
    @SerializedName("user_name")
    private String userName;

    public UserLocationData(String color, double latitude, double longitude, String locationName, String userId, String userName) {
        this.color = color;
        this.latitude = latitude;
        this.longitude = longitude;
        this.locationName = locationName;
        this.userId = userId;
        this.userName = userName;
    }

    public String getColor() {
        return color;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
