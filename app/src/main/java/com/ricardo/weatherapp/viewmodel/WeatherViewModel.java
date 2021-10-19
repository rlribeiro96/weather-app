package com.ricardo.weatherapp.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ricardo.weatherapp.model.LocationSearch;
import com.ricardo.weatherapp.model.Weather;
import com.ricardo.weatherapp.service.WeatherRepository;


public class WeatherViewModel extends ViewModel {

    public WeatherRepository repository;
    private MutableLiveData<Weather> currentWeather;
    private MutableLiveData<LocationSearch> locationSearched;
    private MutableLiveData<String> errorMessage;

    public WeatherViewModel() {
        repository = WeatherRepository.getInstance();

        locationSearched = repository.getLocationSearchedResult();
        errorMessage = repository.getErrorMessageResult();
        currentWeather = repository.getCurrentWeatherResult();
    }

    public MutableLiveData<LocationSearch> getLocationSearched() {
        return locationSearched;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public MutableLiveData<Weather> getCurrentWeather() {
        return currentWeather;
    }

    public void callLocationIdByQuery(String locationName, Context context) {
       repository.getLocationIdByQuery(locationName, context);
    }
}
