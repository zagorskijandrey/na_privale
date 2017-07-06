package concurrent_tasks;

import archiv.save.SaveWeatherData;
import enumeration.RegionEnum;

import java.util.logging.Logger;

/**
 * Created by AZagorskyi on 07.04.2017.
 */
public class RefreshInformationInDataBase {
    private static Logger log = Logger.getLogger(RefreshInformationInDataBase.class.getName());
    private SaveWeatherData saveWeather = null;

    RefreshInformationInDataBase() {
        this.saveWeather = new SaveWeatherData();
    }

    void executeForRegion(RegionEnum regionEnum){
        this.saveWeather.saveWeatherDataFromDynamicJSON(regionEnum);
    }
}
