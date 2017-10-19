package service.collect;

import model.Moon;
import model.WeatherModel;

import java.util.List;
import java.util.Map;

/**
 * Created by andrey on 12.04.2017.
 */
public interface ICollectWeatherData {

    WeatherModel getLastWeatherByRegionId(String regionId, String sqlQuery);
    List<Integer> getPressuresByRegionId(String regionId, String sqlQuery, WeatherModel model);
    Map<Integer, WeatherModel> getLastWeatherForRegions(String sqlQuery);
    Moon getLastMoonDate(String sqlQuery);
}
