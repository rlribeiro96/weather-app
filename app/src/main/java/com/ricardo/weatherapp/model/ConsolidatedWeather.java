package com.ricardo.weatherapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ConsolidatedWeather {

    @SerializedName("consolidated_weather")
    private List<Weather> weathers;

    @SerializedName("title")
    private String city;

    public List<Weather> getWeathers() {
        return weathers;
    }

    public String getCity() {
        return city;
    }
}
