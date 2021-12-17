package ua.intentio.weatherapp.repository.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;


@Dao
public interface WeatherDao {

    @Query("SELECT * FROM weather_entity")
    LiveData<WeatherEntity> getAll();

    @Insert
    void insert(WeatherEntity weatherEntity);

    @Update
    void update(WeatherEntity weatherEntity);
}
