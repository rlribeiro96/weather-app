package com.ricardo.weatherapp.model;

import com.google.gson.annotations.SerializedName;

public class LocationSearch {

    @SerializedName("title")
    private String locationName;

    @SerializedName("woeid")
    private String locationId;

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }
}