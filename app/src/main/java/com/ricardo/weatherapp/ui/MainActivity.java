package com.ricardo.weatherapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ricardo.weatherapp.R;
import com.ricardo.weatherapp.util.Constants;
import com.ricardo.weatherapp.viewmodel.WeatherViewModel;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private WeatherViewModel weatherViewModel;
    private EditText searchBar;
    private TextView locationName, currentTemperature, temperatureUnit, weatherDescription, highAndLowTemp;
    private ImageView weatherImage;
    private Button searchButton;
    private ProgressBar loadingBar;

    private String currentCoords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE}, 1);

        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        configViews();
        configObservables();
    }

    private void configObservables() {
        //to get first access location
        //weatherViewModel.getLocationByCoords(currentCoords, getApplicationContext());

        //Temporary method while getting the user
        //location at first access is not done
        firstAccessSearch();

        weatherViewModel.getLocationSearched().observe(this, locationSearch -> {
            locationName.setText(locationSearch.getLocationName());
        });

        weatherViewModel.getCurrentWeather().observe(this, currentWeather -> {
            currentTemperature.setText(formatTemperature((currentWeather.getCurrentTemp())));
            weatherDescription.setText(currentWeather.getStateName());
            highAndLowTemp.setText(getString(R.string.high_and_low_temperatures,
                    formatTemperature(currentWeather.getMaxTemp()),
                    formatTemperature(currentWeather.getMinTemp())));

            hideLoading();
            Picasso.get().load(getWeatherImage(currentWeather.getStateAbbreviation())).into(weatherImage);
        });

        weatherViewModel.getErrorMessage().observe(this, errorMessage -> {
            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
            hideLoading();
        });
    }

    private String getWeatherImage(String stateAbbreviation) {
        return Constants.IMAGE_URL + stateAbbreviation + Constants.IMAGE_EXT_PNG;
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
                weatherViewModel.callLocationIdByQuery(searchBar.getText().toString(), getApplicationContext());
                showLoading();

                //This block hides the keyboard after submiting the location
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

    private void firstAccessSearch(){
        weatherViewModel.callLocationIdByQuery("São Paulo", getApplicationContext());
        showLoading();
    }

}