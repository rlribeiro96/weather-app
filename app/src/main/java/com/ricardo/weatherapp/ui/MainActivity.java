package com.ricardo.weatherapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ricardo.weatherapp.R;
import com.ricardo.weatherapp.model.LocationSearch;
import com.ricardo.weatherapp.model.Weather;
import com.ricardo.weatherapp.viewmodel.WeatherViewModel;

public class MainActivity extends AppCompatActivity {

    private WeatherViewModel weatherViewModel;
    private EditText searchBar;
    private TextView locationName, currentTemperature, temperatureUnit, weatherDescription, highAndLowTemp;
    private ImageView weatherImage;
    private Button searchButton;
    private ProgressBar loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        configViews();
        configObservables();
    }

    private void configObservables() {
        weatherViewModel.getLocationSearched().observe(this, locationSearch -> {
            locationName.setText(locationSearch.getLocationName());
        });

        weatherViewModel.getCurrentWeatherValue().observe(this, currentWeather -> {
            currentTemperature.setText(formatTemperature((currentWeather.getCurrentTemp())));
            weatherDescription.setText(currentWeather.getStateName());
            highAndLowTemp.setText(getString(R.string.high_and_low_temperatures,
                    formatTemperature(currentWeather.getMaxTemp()),
                    formatTemperature(currentWeather.getMinTemp())));

            hideLoading();
            // weatherImage
        });
    }

    private void configViews() {
        searchBar = findViewById(R.id.location_search);
        locationName = findViewById(R.id.location_name_label);
        currentTemperature = findViewById(R.id.current_temperature);
        temperatureUnit = findViewById(R.id.temperature_unit);
        weatherDescription = findViewById(R.id.weather_description);
        highAndLowTemp = findViewById(R.id.location_high_and_low_temp);
        weatherImage = findViewById(R.id.weather_image);
        searchButton = findViewById(R.id.search_button);
        loadingBar = findViewById(R.id.loading_bar);


        searchButton.setOnClickListener(v -> {
            if (!searchBar.getText().toString().equals("")) {
                weatherViewModel.getLocationIdByQuery(searchBar.getText().toString(), getApplicationContext());
                showLoading();

                InputMethodManager imm = (InputMethodManager) getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
        });
    }

    private void hideLoading() {
        loadingBar.setVisibility(View.INVISIBLE);

        searchBar.setVisibility(View.VISIBLE);
        locationName.setVisibility(View.VISIBLE);
        currentTemperature.setVisibility(View.VISIBLE);
        weatherDescription.setVisibility(View.VISIBLE);
        highAndLowTemp.setVisibility(View.VISIBLE);
        weatherImage.setVisibility(View.VISIBLE);
        searchButton.setVisibility(View.VISIBLE);
        temperatureUnit.setVisibility(View.VISIBLE);
    }

    private void showLoading() {
        loadingBar.setVisibility(View.VISIBLE);

        searchBar.setVisibility(View.INVISIBLE);
        locationName.setVisibility(View.INVISIBLE);
        currentTemperature.setVisibility(View.INVISIBLE);
        weatherDescription.setVisibility(View.INVISIBLE);
        highAndLowTemp.setVisibility(View.INVISIBLE);
        weatherImage.setVisibility(View.INVISIBLE);
        searchButton.setVisibility(View.INVISIBLE);
        temperatureUnit.setVisibility(View.INVISIBLE);
    }

    private String formatTemperature(String temperature) {
        return String.valueOf(Math.round(Double.parseDouble(temperature)));
    }
}
