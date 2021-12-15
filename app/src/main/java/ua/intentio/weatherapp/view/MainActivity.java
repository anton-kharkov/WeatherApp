package ua.intentio.weatherapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.intentio.weatherapp.R;
import ua.intentio.weatherapp.repository.retrofit.Weather;
import ua.intentio.weatherapp.repository.retrofit.WeatherApi;
import ua.intentio.weatherapp.repository.retrofit.WeatherApiService;

public class MainActivity extends AppCompatActivity {

    private static WeatherApiService weatherApiService;
    Weather weather;

    TextView cityNameView;
    TextView temperatureView;
    EditText enterCityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityNameView = findViewById(R.id.firstBaseCity);
        temperatureView = findViewById(R.id.firstBaseTemp);

        enterCityName = findViewById(R.id.enterCityName);

        weatherApiService = WeatherApi.getApi();
        weather = new Weather();
    }

    public void getWeather(View view){

        view.setClickable(false);

        String cityName = enterCityName.getText().toString();

        weatherApiService.getWeatherByCityName(cityName).enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                weather = response.body();
                cityNameView.setText(weather.getCityName());
                temperatureView.setText(
                        Long.toString(Math.round(weather.getTemperature())) + " \u2103"
                );
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Toast.makeText(MainActivity.this, "An error", Toast.LENGTH_SHORT).show();
            }
        });

        view.setClickable(true);
    }
}