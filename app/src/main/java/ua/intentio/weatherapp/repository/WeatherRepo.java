package ua.intentio.weatherapp.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.intentio.weatherapp.component.AppDb;
import ua.intentio.weatherapp.repository.local.WeatherDao;
import ua.intentio.weatherapp.repository.local.WeatherDatabase;
import ua.intentio.weatherapp.repository.local.WeatherEntity;
import ua.intentio.weatherapp.repository.retrofit.Weather;
import ua.intentio.weatherapp.repository.retrofit.WeatherApi;
import ua.intentio.weatherapp.repository.retrofit.WeatherApiInterface;

public class WeatherRepo {

    private final String TAG = getClass().getSimpleName();

    WeatherDatabase weatherDatabase;
    WeatherDao weatherDao;

    public WeatherRepo() {

        weatherDatabase = AppDb.getInstance().weatherDatabase;
        weatherDao = weatherDatabase.weatherDao();

    }

    public MutableLiveData<Weather> requestWeatherFromRetrofit(String cityName) {
        final MutableLiveData<Weather> mutableLiveData = new MutableLiveData<>();

        WeatherApiInterface apiService = WeatherApi.getApi();

        apiService.getWeatherByCityName(cityName).enqueue(new Callback<Weather>() {
            Thread thread;
            @Override
            public void onResponse(@NonNull Call<Weather> call,
                                   @NonNull Response<Weather> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Weather weather = response.body();
                    thread = new Thread(() -> {
                        addWeatherToDb(weather);
                        thread.interrupt();
                    });
                    thread.start();
                    mutableLiveData.setValue(weather);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Weather> call, @NonNull Throwable t) {
                Log.e(TAG, "getProdList onFailure" + call.toString());
            }
        });

        return mutableLiveData;

    }

    public WeatherDao requestWeatherFromDb() {
        return weatherDao;
    }

    public void addWeatherToDb(Weather weather) {
        long id;
        switch (weather.getCityName()) {
            case "Kyiv":
                id = 1;
                break;
            case "Dnipro":
                id = 2;
                break;
            default:
                id = 3;
        }

        WeatherEntity weatherEntity = new WeatherEntity(
                id, weather.getCityName(), weather.getMain().getTemp());

        try {
            weatherDao.insert(weatherEntity);
        } catch (Throwable throwable) {
            weatherDao.update(weatherEntity);
        }
    }
}
