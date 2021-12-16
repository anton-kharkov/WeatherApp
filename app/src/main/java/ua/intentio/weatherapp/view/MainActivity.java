package ua.intentio.weatherapp.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import ua.intentio.weatherapp.R;
import ua.intentio.weatherapp.repository.retrofit.Weather;
import ua.intentio.weatherapp.viewmodel.WeatherViewModel;

public class MainActivity extends AppCompatActivity {

    WeatherViewModel weatherViewModel = new WeatherViewModel();

    TextView firstBaseCityView;
    TextView firstBaseTempView;
    TextView secondBaseCityView;
    TextView secondBaseTempView;
    TextView userCityView;
    TextView userTempView;
    EditText enterCityName;
    Button getWeatherButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherViewModel = new WeatherViewModel();

        firstBaseCityView = findViewById(R.id.firstBaseCity);
        firstBaseTempView = findViewById(R.id.firstBaseTemp);
        secondBaseCityView = findViewById(R.id.secondBaseCity);
        secondBaseTempView = findViewById(R.id.secondBaseTemp);
        userCityView = findViewById(R.id.userCity);
        userTempView = findViewById(R.id.userTemp);
        enterCityName = findViewById(R.id.enterCityName);
        getWeatherButton = findViewById(R.id.buttonGetWeather);
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkInternetConnection();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(()->{
                    checkInternetConnection();
                });
            }
        };

        timer.schedule(timerTask, 0, 10000);
    }

    public void pressGetWeatherButton(View view){
        String cityName = enterCityName.getText().toString();

        if(!cityName.equals("")){

            weatherViewModel.getWeather(cityName).observe(this, new Observer<Weather>() {
                @Override
                public void onChanged(Weather weather) {
                    userCityView.setText(weather.getCityName());
                    userTempView.setText(Long.toString(Math.round(weather.getTemperature())));
                }
            });
        }
    }

    public void checkInternetConnection(){

        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager.getActiveNetworkInfo() != null
                && connectivityManager.getActiveNetworkInfo().isAvailable()
                && connectivityManager.getActiveNetworkInfo().isConnected()){

            getWeatherButton.setClickable(true);
            getWeatherButton.setText(R.string.get_weather_button);
            enterCityName.setVisibility(View.VISIBLE);
        }else {

            getWeatherButton.setClickable(false);
            enterCityName.setVisibility(View.INVISIBLE);
            getWeatherButton.setText(R.string.offline_mode);
        }
    }
}