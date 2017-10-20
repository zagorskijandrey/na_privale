package concurrent_tasks;

import dao.weather.WeatherDaoSave;
import dao.weather.WeatherDaoSaveImpl;
import enumeration.RegionEnum;

import java.util.logging.Logger;

/**
 * Created by AZagorskyi on 07.04.2017.
 */
public class RefreshInformationInDataBase {
    private static Logger log = Logger.getLogger(RefreshInformationInDataBase.class.getName());
    private WeatherDaoSave saveWeather = null;

    RefreshInformationInDataBase() {
        this.saveWeather = new WeatherDaoSaveImpl();
    }

    void executeForRegion(RegionEnum regionEnum){
        this.saveWeather.saveWeatherDataFromDynamicJSON(regionEnum);
    }
}
