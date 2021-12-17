package ua.intentio.weatherapp.component;

import android.app.Application;

import androidx.room.Room;

import ua.intentio.weatherapp.repository.local.WeatherDatabase;

public class AppDb extends Application {

    public static AppDb instance;

    public WeatherDatabase weatherDatabase;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        weatherDatabase = Room.databaseBuilder(this, WeatherDatabase.class, "databace")
                .build();
    }

    public static AppDb getInstance(){
        return instance;
    }
}
