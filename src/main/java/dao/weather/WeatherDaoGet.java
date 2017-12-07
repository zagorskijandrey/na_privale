package dao.weather;

import model.Moon;
import model.WeatherModel;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by andrey on 12.04.2017.
 */
public interface WeatherDaoGet {

    WeatherModel getLastWeatherByRegionId(String sqlQuery, String regionId);
    List<Integer> getPressuresByRegionId(String sqlQuery, int regionId, Date date);
    Map<Integer, WeatherModel> getLastWeatherForRegions(String sqlQuery);
    Moon getLastMoonDate(String sqlQuery);
    int getMoonPhaseByDate(String sqlQuery, Date date);
    WeatherModel getWeatherByProvinceIdAndDate(String sqlQuery, int provinceId, Date date);
//    List<Integer> getPressuresByProvinceIdAndDate(String sqlQuery, int provinceId, Date date);
}
