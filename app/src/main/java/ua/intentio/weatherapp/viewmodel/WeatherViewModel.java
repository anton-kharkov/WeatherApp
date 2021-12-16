package ua.intentio.weatherapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ua.intentio.weatherapp.repository.WeatherRepo;
import ua.intentio.weatherapp.repository.retrofit.Weather;

public class WeatherViewModel extends ViewModel {

    private WeatherRepo weatherRepo;
    private MutableLiveData<Weather> mutableLiveData;

    public WeatherViewModel() {
        weatherRepo = new WeatherRepo();
    }

    public LiveData<Weather> getWeather(String cityName){
        mutableLiveData = weatherRepo.requestWeather(cityName);

        return mutableLiveData;
    }
}
