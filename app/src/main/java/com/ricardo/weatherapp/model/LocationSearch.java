package com.ricardo.weatherapp.model;

import com.google.gson.annotations.SerializedName;

public class LocationSearch {

    @SerializedName("title")
    private String locationName;

    @SerializedName("woeid")
    private String locationId = "";

    public String getLocationName() {
        return locationName;
    }

    public String getLocationId() {
        return locationId;
    }
}