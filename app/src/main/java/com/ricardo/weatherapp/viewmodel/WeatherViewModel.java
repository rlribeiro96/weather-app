package com.ricardo.weatherapp.viewmodel;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ricardo.weatherapp.R;
import com.ricardo.weatherapp.model.ConsolidatedWeather;
import com.ricardo.weatherapp.model.LocationSearch;
import com.ricardo.weatherapp.model.Weather;
import com.ricardo.weatherapp.service.API;
import com.ricardo.weatherapp.service.ServiceBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WeatherViewModel extends ViewModel {

    private API api;
    private MutableLiveData<Weather> currentWeather;
    private MutableLiveData<LocationSearch> locationSearched;
    private MutableLiveData<String> errorMessage;

    public WeatherViewModel() {
        currentWeather = new MutableLiveData<>();
        locationSearched = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
    }

    public MutableLiveData<Weather> getCurrentWeatherValue() {
        return currentWeather;
    }

    public MutableLiveData<LocationSearch> getLocationSearched() {
        return locationSearched;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void getLocationIdByQuery(String locationName, Context context) {
        api = ServiceBuilder.getService().create(API.class);

        Call<ArrayList<LocationSearch>> locationSearchDataCall = api.getLocationIdByQuery(locationName);

        locationSearchDataCall.enqueue(new Callback<ArrayList<LocationSearch>>() {
            @Override
            public void onResponse(Call<ArrayList<LocationSearch>> call, Response<ArrayList<LocationSearch>> response) {
                if (!response.body().isEmpty()) {
                    locationSearched.setValue(response.body().get(0));
                    loadCurrentWeather(response.body().get(0).getLocationId(), context);
                } else {
                    errorMessage.setValue(context.getString(R.string.location_not_found_error));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<LocationSearch>> call, Throwable t) {
                System.out.println("getLocationIdByQuery onFailure: " + t.getLocalizedMessage());
                errorMessage.setValue(context.getString(R.string.general_error_message));
            }
        });
    }

    public void getLocationByCoords(String lattlong, Context context) {
        api = ServiceBuilder.getService().create(API.class);

        Call<ArrayList<LocationSearch>> locationSearchDataCall = api.getLocationIdByQuery(lattlong);

        locationSearchDataCall.enqueue(new Callback<ArrayList<LocationSearch>>() {
            @Override
            public void onResponse(Call<ArrayList<LocationSearch>> call, Response<ArrayList<LocationSearch>> response) {
                if (response.body() != null) {
                    if (!response.body().isEmpty()) {
                        locationSearched.setValue(response.body().get(0));
                        loadCurrentWeather(response.body().get(0).getLocationId(), context);
                    }
                } else {
                    errorMessage.setValue(context.getString(R.string.location_not_found_error));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<LocationSearch>> call, Throwable t) {
                System.out.println("getLocationIdByQuery onFailure: " + t.getLocalizedMessage());
                errorMessage.setValue(context.getString(R.string.current_location_not_found_error));
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
                    currentWeather.setValue(weather);
                }
            }

            @Override
            public void onFailure(Call<ConsolidatedWeather> call, Throwable t) {
                System.out.println("loadCurrentWeather onFailure: " + t.getLocalizedMessage());
                errorMessage.setValue(context.getString(R.string.general_error_message));
            }
        });
    }
}
