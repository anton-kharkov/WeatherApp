package ua.intentio.weatherapp.repository.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Weather {

    @SerializedName("name")
    @Expose
    private String cityName;

    @SerializedName("main")
    @Expose
    private WeatherTemperature weatherTemperature;

    public String getCityName() {
        return cityName;
    }

    public WeatherTemperature getMain() {
        return weatherTemperature;
    }
}
