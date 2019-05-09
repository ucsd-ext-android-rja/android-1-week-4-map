package com.ucsdextandroid1.snapmap;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by rjaylward on 2019-05-04
 *
 * This is a class that represents the response we want to get from the active users locations
 * endpoint. It is basically just a wrapper around a list of user locations.
 */
//TODO added in class 5
public class ActiveUserLocationsResponse {

    private List<UserLocationData> userLocations;

    public List<UserLocationData> getUserLocations() {
        return userLocations;
    }

    public void setUserLocations(List<UserLocationData> userLocations) {
        this.userLocations = userLocations;
    }

}