package concurrent_tasks;

import archiving.collect.CollectWeatherData;
import archiving.update.UpdateWeatherData;
import constant.Constant;
import enumeration.Region;
import model.WeatherModel;

import java.util.Date;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by AZagorskyi on 07.04.2017.
 */
public class RefreshInformationInDataBase{
    private static Logger log = Logger.getLogger(RefreshInformationInDataBase.class.getName());
    private UpdateWeatherData updateWeather = null;

    public RefreshInformationInDataBase(){
        this.updateWeather = new UpdateWeatherData();
    }

    public void executeForRegion(Region city) {
        for (Map.Entry<String, String> key_value: Constant.MAP_QUERY_REFRESH_DATA_BASE.entrySet()){
            completeRefreshInformationTask(city, key_value.getKey(), key_value.getValue());
            log.info(city.getRegion() + new Date().toString());
        }
        updateWeather.updateWeatherDataFromDynamicJSON(city);
    }

    private void completeRefreshInformationTask(Region city, String sqlQuerySelect, String sqlQueryUpdate){
        CollectWeatherData collectWeatherData = new CollectWeatherData();
        WeatherModel weatherModel = collectWeatherData.getWeatherByCity(city, sqlQuerySelect);
        updateWeather.updateWeatherData(weatherModel, city, sqlQueryUpdate);
    }
}
