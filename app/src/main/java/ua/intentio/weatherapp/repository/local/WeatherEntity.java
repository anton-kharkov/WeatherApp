package ua.intentio.weatherapp.repository.local;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity()
public class WeatherEntity {

    @PrimaryKey
    private final long id;

    private final String cityName;

    private final double temperature;

    public WeatherEntity(long id, String cityName, double temperature) {
        this.id = id;
        this.cityName = cityName;
        this.temperature = temperature;
    }

    public long getId() {
        return id;
    }

    public String getCityName() {
        return cityName;
    }

    public double getTemperature() {
        return temperature;
    }
}
