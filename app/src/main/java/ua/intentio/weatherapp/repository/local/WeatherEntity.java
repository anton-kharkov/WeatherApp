package ua.intentio.weatherapp.repository.local;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class WeatherEntity {

    @PrimaryKey
    private long id;

    private String cityName;

    private long temperature;

    public WeatherEntity(long id, String cityName, long temperature) {
        this.id = id;
        this.cityName = cityName;
        this.temperature = temperature;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public long getTemperature() {
        return temperature;
    }

    public void setTemperature(long temperature) {
        this.temperature = temperature;
    }
}
