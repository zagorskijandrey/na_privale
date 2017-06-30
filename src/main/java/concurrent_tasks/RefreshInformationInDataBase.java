package concurrent_tasks;

import archiv.collect.ICollectWeatherData;
import archiv.collect.proxy.ProxyCollectWeatherData;
import archiv.save.SaveWeatherData;
import enumeration.Region;
import model.WeatherModel;

import java.text.ParseException;
import java.util.logging.Logger;

/**
 * Created by AZagorskyi on 07.04.2017.
 */
public class RefreshInformationInDataBase {
    private static Logger log = Logger.getLogger(RefreshInformationInDataBase.class.getName());
    private SaveWeatherData saveWeather = null;

    public RefreshInformationInDataBase() {
        this.saveWeather = new SaveWeatherData();
    }

    public void executeForRegion(Region region){
        this.saveWeather.saveWeatherDataFromDynamicJSON(region);
    }
}
