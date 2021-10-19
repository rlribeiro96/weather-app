package com.ricardo.weatherapp;

import com.ricardo.weatherapp.service.WeatherRepository;
import com.ricardo.weatherapp.viewmodel.WeatherViewModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

public class WeatherViewModelTest {

    @Mock
    private WeatherRepository weatherRepository;

    private WeatherViewModel weatherViewModel;


    @Before
    public void setupWeatherViewModelTest() {
        MockitoAnnotations.initMocks(this);

        weatherViewModel = new WeatherViewModel();
        weatherViewModel.repository = weatherRepository;
    }

    @Test
    public void validateRepository_getLocationById(){
        weatherViewModel.callLocationIdByQuery(any(), any());

        verify(weatherRepository).getLocationIdByQuery(any(), any());
    }
}
