package ua.intentio.weatherapp.repository.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;


@Dao
public interface WeatherDao {

    @Query("SELECT * FROM weatherentity")
    LiveData<List<WeatherEntity>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WeatherEntity weatherEntity);
}
