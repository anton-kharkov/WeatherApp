package ua.intentio.weatherapp.repository.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherApi {
    static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";

    public static WeatherApiService getApi() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherApiService weatherApiService = retrofit.create(WeatherApiService.class);
        return weatherApiService;
    }
}