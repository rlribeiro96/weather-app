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

    public void setWeathers(List<Weather> weathers) {
        this.weathers = weathers;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
