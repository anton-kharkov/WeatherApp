package ua.intentio.weatherapp.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.intentio.weatherapp.repository.retrofit.Weather;
import ua.intentio.weatherapp.repository.retrofit.WeatherApi;
import ua.intentio.weatherapp.repository.retrofit.WeatherApiInterface;

public class WeatherRepo {

    private final String TAG = getClass().getSimpleName();

    public MutableLiveData<Weather> requestWeather(String cityName){
        final MutableLiveData<Weather> mutableLiveData = new MutableLiveData<>();

        WeatherApiInterface apiService = WeatherApi.getApi();

        apiService.getWeatherByCityName(cityName).enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                if (response.isSuccessful() && response.body()!= null){
                    Weather weather = response.body();
                    mutableLiveData.setValue(weather);
                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.e(TAG, "getProdList onFailure" + call.toString());
            }
        });

        return mutableLiveData;
    }
}
