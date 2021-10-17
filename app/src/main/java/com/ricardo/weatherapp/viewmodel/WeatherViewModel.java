package com.ricardo.weatherapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ricardo.weatherapp.model.ConsolidatedWeather;
import com.ricardo.weatherapp.model.LocationSearch;
import com.ricardo.weatherapp.model.Weather;
import com.ricardo.weatherapp.service.API;
import com.ricardo.weatherapp.service.ServiceBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherViewModel extends ViewModel {

    private API api = ServiceBuilder.getService().create(API.class);
    private MutableLiveData<Weather> currentWeather;

    public void getLocationIdByQuery(String locationName) {
        Call<LocationSearch> locationSearchDataCall = api.getLocationIdByQuery(locationName);
        locationSearchDataCall.enqueue(new Callback<LocationSearch>() {
            @Override
            public void onResponse(Call<LocationSearch> call, Response<LocationSearch> response) {
                loadCurentWeather(response.body().getLocationId());
            }

            @Override
            public void onFailure(Call<LocationSearch> call, Throwable t) {
                System.out.println("@@@@ getLocationIdByQuery onFailure: " + t.getLocalizedMessage());
            }
        });

    }

    public void loadCurentWeather(String locationId) {
        Call<ConsolidatedWeather> consolidateWeatherDataCall = api.getConsolidateWeatherByLocation(locationId);
        consolidateWeatherDataCall.enqueue(new Callback<ConsolidatedWeather>() {
            @Override
            public void onResponse(Call<ConsolidatedWeather> call, Response<ConsolidatedWeather> response) {
                currentWeather.setValue(response.body().getWeathers().get(0));
            }

            @Override
            public void onFailure(Call<ConsolidatedWeather> call, Throwable t) {
                System.out.println("@@@@ loadCurentWeather onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    public MutableLiveData<Weather> getCurrentWeather() {
        return currentWeather;
    }
}
