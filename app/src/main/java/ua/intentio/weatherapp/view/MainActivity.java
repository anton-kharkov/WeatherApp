package ua.intentio.weatherapp.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import ua.intentio.weatherapp.R;
import ua.intentio.weatherapp.viewmodel.WeatherViewModel;

public class MainActivity extends AppCompatActivity {

    WeatherViewModel weatherViewModel = new WeatherViewModel();
    Timer timer;

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

        getWeatherByDb();
    }

    @Override
    protected void onStart() {
        super.onStart();
        autoCheckNetworkStatus();
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
    }

    public void pressGetWeatherButton(View view) {
        String cityName = enterCityName.getText().toString();

        if (!cityName.isEmpty()) {
            ArrayList<String> cityList = new ArrayList<>(3);

            cityList.add("Киев");
            cityList.add("Днепр");
            cityList.add(cityName);

            for (int i = 0; i < cityList.size(); ) {
                weatherViewModel.requestWeatherFromRetrofit(cityList.get(i))
                        .observe(this, weather -> {
                            double temp = weather.getMain().getTemp();

                            switch (weather.getCityName()) {
                                case "Kyiv":
                                    firstBaseCityView.setText(weather.getCityName());
                                    firstBaseTempView.setText(createTemp(temp));
                                    break;
                                case "Dnipro":
                                    secondBaseCityView.setText(weather.getCityName());
                                    secondBaseTempView.setText(createTemp(temp));
                                    break;
                                default:
                                    userCityView.setText(weather.getCityName());
                                    userTempView.setText(createTemp(temp));
                            }
                        });
                i++;
            }
        }
    }

    //Checking the Internet connection and choosing a way to work
    public void checkInternetConnection() {

        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager.getActiveNetworkInfo() != null
                && connectivityManager.getActiveNetworkInfo().isAvailable()
                && connectivityManager.getActiveNetworkInfo().isConnected()) {

            getWeatherButton.setClickable(true);
            getWeatherButton.setText(R.string.get_weather_button);
            enterCityName.setVisibility(View.VISIBLE);
        } else {
            getWeatherByDb();
            getWeatherButton.setClickable(false);
            enterCityName.setVisibility(View.INVISIBLE);
            getWeatherButton.setText(R.string.offline_mode);
        }
    }

    //Automatically check network status every 10 seconds
    private void autoCheckNetworkStatus() {
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> checkInternetConnection());
            }
        };
        timer.schedule(timerTask, 0, 5000);
    }

    public void getWeatherByDb() {
        weatherViewModel.requestWeatherFromDb().getAll().observe(this, weatherEntities -> {
            if (weatherEntities.size() == 3) {

                for (int i = 0; i < weatherEntities.size();) {

                    String cityName = weatherEntities.get(i).getCityName();
                    double temp = weatherEntities.get(i).getTemperature();

                    switch (i) {
                        case 0:
                            firstBaseCityView.setText(cityName);
                            firstBaseTempView.setText(createTemp(temp));
                            break;
                        case 1:
                            secondBaseCityView.setText(cityName);
                            secondBaseTempView.setText(createTemp(temp));
                            break;
                        default:
                            userCityView.setText(cityName);
                            userTempView.setText(createTemp(temp));
                    }
                    i++;
                }
            }
        });
    }

    //StringBuilder or correct creation String temperature
    public String createTemp(double temp){
        return Math.round(temp) + " \u2103";
    }
}
