package com.ricardo.weatherapp.service;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.ricardo.weatherapp.R;
import com.ricardo.weatherapp.model.ConsolidatedWeather;
import com.ricardo.weatherapp.model.LocationSearch;
import com.ricardo.weatherapp.model.Weather;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherRepository {

    private static WeatherRepository instance;
    private API api;

    private MutableLiveData<Weather> currentWeatherResult = new MutableLiveData<>();
    private MutableLiveData<LocationSearch> locationSearchedResult = new MutableLiveData<>();
    private MutableLiveData<String> errorMessageResult = new MutableLiveData<>();


    public static WeatherRepository getInstance() {
        if (instance == null) {
            instance = new WeatherRepository();
        }
        return instance;
    }

    public MutableLiveData<LocationSearch> getLocationSearchedResult() {
        return locationSearchedResult;
    }

    public MutableLiveData<Weather> getCurrentWeatherResult() {
        return currentWeatherResult;
    }

    public MutableLiveData<String> getErrorMessageResult() {
        return errorMessageResult;
    }

    public void getLocationIdByQuery(String locationName, Context context) {
        api = ServiceBuilder.getService().create(API.class);
        Call<ArrayList<LocationSearch>> locationSearchDataCall = api.getLocationIdByQuery(locationName);

        locationSearchDataCall.enqueue(new Callback<ArrayList<LocationSearch>>() {
            @Override
            public void onResponse(Call<ArrayList<LocationSearch>> call, Response<ArrayList<LocationSearch>> response) {
                if (!response.body().isEmpty()) {
                    locationSearchedResult.setValue(response.body().get(0));
                    loadCurrentWeather(response.body().get(0).getLocationId(), context);
                } else {
                    errorMessageResult.setValue(context.getString(R.string.location_not_found_error));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<LocationSearch>> call, Throwable t) {
                System.out.println("getLocationIdByQuery onFailure: " + t.getLocalizedMessage());
                errorMessageResult.setValue(context.getString(R.string.general_error_message));
            }
        });
    }

    private void loadCurrentWeather(String locationId, Context context) {
        api = ServiceBuilder.getService().create(API.class);
        Call<ConsolidatedWeather> consolidateWeatherDataCall = api.getConsolidateWeatherByLocation(locationId);

        consolidateWeatherDataCall.enqueue(new Callback<ConsolidatedWeather>() {
            @Override
            public void onResponse(Call<ConsolidatedWeather> call, Response<ConsolidatedWeather> response) {
                if (response.body() != null) {
                    Weather weather = response.body().getWeathers().get(0);
                    currentWeatherResult.setValue(weather);
                }
            }

            @Override
            public void onFailure(Call<ConsolidatedWeather> call, Throwable t) {
                System.out.println("loadCurrentWeather onFailure: " + t.getLocalizedMessage());
                errorMessageResult.setValue(context.getString(R.string.general_error_message));
            }
        });
    }


}
