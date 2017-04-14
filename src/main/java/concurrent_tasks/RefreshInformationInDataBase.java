package concurrent_tasks;

import archiving.collect.CollectWeatherData;
import archiving.collect.ICollectWeatherData;
import archiving.collect.proxy.ProxyCollectWeatherData;
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

    public void executeForRegion(Region region) {
        for (Map.Entry<String, String> key_value: Constant.MAP_QUERY_REFRESH_DATA_BASE.entrySet()){
            completeRefreshInformationTask(region, key_value.getKey(), key_value.getValue());
            log.info(region.getRegion() + new Date().toString());
        }
        updateWeather.updateWeatherDataFromDynamicJSON(region);
    }

    private void completeRefreshInformationTask(Region region, String sqlQuerySelect, String sqlQueryUpdate){
        ICollectWeatherData collectWeatherData = new ProxyCollectWeatherData();
        WeatherModel weatherModel = collectWeatherData.getWeatherByCity(region, sqlQuerySelect);
        updateWeather.updateWeatherData(weatherModel, region, sqlQueryUpdate);
    }
}
