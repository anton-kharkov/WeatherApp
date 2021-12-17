package ua.intentio.weatherapp.repository.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiInterface {

    @GET("weather?units=metric&appid=ce8a6c84cc1b757e9d8ae4b513bb14b4")
    Call<Weather> getWeatherByCityName(@Query("q") String cityName);
}
