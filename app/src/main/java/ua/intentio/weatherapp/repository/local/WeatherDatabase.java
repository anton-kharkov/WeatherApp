package ua.intentio.weatherapp.repository.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = WeatherEntity.class, version = 1, exportSchema = false)
public abstract class WeatherDatabase extends RoomDatabase {
    public abstract WeatherDao weatherDao();
}
