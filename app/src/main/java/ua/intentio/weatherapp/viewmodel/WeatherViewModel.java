package ua.intentio.weatherapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import ua.intentio.weatherapp.repository.WeatherRepo;
import ua.intentio.weatherapp.repository.local.WeatherEntity;
import ua.intentio.weatherapp.repository.retrofit.Weather;

public class WeatherViewModel extends ViewModel {

    private final WeatherRepo weatherRepo;

    public WeatherViewModel() {
        weatherRepo = new WeatherRepo();
    }

    public LiveData<Weather> requestWeatherFromRetrofit(String cityName){
        MutableLiveData<Weather> mutableLiveData;

        mutableLiveData = weatherRepo.requestWeatherFromRetrofit(cityName);

        return mutableLiveData;
    }

    public LiveData<LiveData<WeatherEntity>> requestWeatherFromDb(){
        MutableLiveData<LiveData<WeatherEntity>> mutableLiveData;

        mutableLiveData = weatherRepo.requestWeatherFromDb();

        return mutableLiveData;
    }


}
