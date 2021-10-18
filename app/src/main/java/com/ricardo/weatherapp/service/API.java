package com.ricardo.weatherapp.service;

import com.ricardo.weatherapp.model.ConsolidatedWeather;
import com.ricardo.weatherapp.model.LocationSearch;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {

    @GET("/api/location/search/")
    Call<ArrayList<LocationSearch>> getLocationIdByQuery(@Query("query") String locationName);

    @GET("/api/location/{location_id}")
    Call<ConsolidatedWeather> getConsolidateWeatherByLocation(@Path("location_id") String locationId);

}
