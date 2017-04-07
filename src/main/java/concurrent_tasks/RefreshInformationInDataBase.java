package concurrent_tasks;

import archiving.collect.CollectWeatherData;
import archiving.update.UpdateWeatherData;
import constant.Constant;
import enumeration.Region;
import model.WeatherModel;

import java.util.Map;
import java.util.TimerTask;
import java.util.logging.Logger;

/**
 * Created by AZagorskyi on 07.04.2017.
 */
public class RefreshInformationInDataBase extends TimerTask{

    public void run() {
        for (Region city: Region.values()){
            for (Map.Entry<String, String> key_value: Constant.MAP_QUERY_REFRESH_DATA_BASE.entrySet()){
                synchronized (this){
                    completeRefreshInformationTask(city, key_value.getKey(), key_value.getValue());
                    Logger.getLogger("key_value");
                }
            }
            Logger.getLogger("city" + city.getCity());
        }
    }

    private void completeRefreshInformationTask(Region city, String sqlQuerySelect, String sqlQueryUpdate){
        CollectWeatherData collectWeatherData = new CollectWeatherData();
        WeatherModel weatherModel = collectWeatherData.getWeatherByCity(city, sqlQuerySelect);
        UpdateWeatherData updateWeather = new UpdateWeatherData();
        updateWeather.updateWeatherData(weatherModel, city, sqlQueryUpdate);
    }
}
