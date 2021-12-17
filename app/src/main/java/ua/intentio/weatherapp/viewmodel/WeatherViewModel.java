package ua.intentio.weatherapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ua.intentio.weatherapp.repository.WeatherRepo;
import ua.intentio.weatherapp.repository.local.WeatherDao;
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

    public WeatherDao requestWeatherFromDb(){

        return weatherRepo.requestWeatherFromDb();
    }


}
