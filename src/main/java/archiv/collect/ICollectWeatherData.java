package archiv.collect;

import enumeration.Region;
import model.WeatherModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrey on 12.04.2017.
 */
public interface ICollectWeatherData {
//    List<Integer> getPressures();
//    int getWindRout();
//    int getWindSpeed();
//    WeatherModel getWeatherByCity(Region region, String sqlQuery);
    WeatherModel getLastWeatherByRegion(Region region, String sqlQuery);
    List<Integer> getPressuresByRegion(Region region, String sqlQuery, WeatherModel model);
}
