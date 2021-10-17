package com.ricardo.weatherapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ricardo.weatherapp.R;
import com.ricardo.weatherapp.model.Weather;
import com.ricardo.weatherapp.viewmodel.WeatherViewModel;

public class MainActivity extends AppCompatActivity {

    private WeatherViewModel weatherViewModel;
    private EditText searchBar;
    private TextView locationName, currentTemperature, temperatureUnit, weatherDescription, highAndLowTemp;
    private ImageView weatherImage;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);


        configViews();
        configObservables();

        setContentView(R.layout.activity_main);
    }

    private void configObservables() {
        weatherViewModel.getCurrentWeather().observe(this, new Observer<Weather>() {
            @Override
            public void onChanged(Weather currentWeather) {
                //config views
            }
        });
    }

    private void configViews() {
        searchBar = this.findViewById(R.id.location_search);
        locationName = this.findViewById(R.id.location_name_label);
        currentTemperature = this.findViewById(R.id.current_temperature);
        temperatureUnit = this.findViewById(R.id.temperature_unit);
        weatherDescription = this.findViewById(R.id.weather_description);
        highAndLowTemp = this.findViewById(R.id.location_high_and_low_temp);
        weatherImage = this.findViewById(R.id.weather_image);
        searchButton = this.findViewById(R.id.search_button);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!searchBar.getText().toString().equals("")){
                    weatherViewModel.getLocationIdByQuery(searchBar.getText().toString());
                }
            }
        });
    }

}
