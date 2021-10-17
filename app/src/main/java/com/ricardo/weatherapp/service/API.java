package com.ricardo.weatherapp.service;

import com.ricardo.weatherapp.model.ConsolidatedWeather;
import com.ricardo.weatherapp.model.LocationSearch;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {

    @POST("/api/location/search/")
    Call<LocationSearch> getLocationIdByQuery(@Query("query") String locationName);

    @POST("/api/location/{location_id}")
    Call<ConsolidatedWeather> getConsolidateWeatherByLocation(@Path("location_id") String locationId);

}
