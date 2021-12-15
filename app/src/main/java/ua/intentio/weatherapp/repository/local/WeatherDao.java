package ua.intentio.weatherapp.repository.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface WeatherDao {

    @Query("SELECT * FROM weatherentity")
    LiveData<WeatherEntity> getAll();

    @Insert
    void insert(WeatherEntity weatherEntity);

    @Update
    void update(WeatherEntity weatherEntity);
}
