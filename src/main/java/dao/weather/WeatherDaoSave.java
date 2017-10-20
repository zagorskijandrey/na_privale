package dao.weather;

import enumeration.RegionEnum;

/**
 * Created by AZagorskyi on 20.10.2017.
 */
public interface WeatherDaoSave {
    void saveWeatherDataFromDynamicJSON(RegionEnum regionEnum);
    void saveMoonDataFromDynamicJSON();
}
