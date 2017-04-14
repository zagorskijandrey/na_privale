package archiving.collect.proxy;

import archiving.collect.CollectWeatherData;
import archiving.collect.ICollectWeatherData;
import constant.Constant;
import enumeration.Region;
import model.DateModel;
import model.WeatherModel;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by AZagorskyi on 12.04.2017.
 */
public class ProxyCollectWeatherData implements ICollectWeatherData {
    private Logger log = Logger.getLogger(ProxyCollectWeatherData.class.getName());

    private String [] sqlQueries = {Constant.SQL_QUERY_SELECT_THIRD_YESTERDAY_WEATHER, Constant.SQL_QUERY_SELECT_TWICE_YESTERDAY_WEATHER,
            Constant.SQL_QUERY_SELECT_ONCE_YESTERDAY_WEATHER, Constant.SQL_QUERY_SELECT_TODAY_WEATHER,
            Constant.SQL_QUERY_SELECT_TOMORROW_WEATHER};

    private CollectWeatherData collectWeatherData = null;
    private Region region = null;
    private String sqlQuery = null;

    public ProxyCollectWeatherData(){}

    public ProxyCollectWeatherData(Region region, String sqlQuery){
        this.region = region;
        this.sqlQuery = sqlQuery;
    }

    @Override
    public List<Integer> getPressures() {
        if (collectWeatherData == null){
            createCollectWeatherData(region, sqlQueries, sqlQuery);
        }
        return collectWeatherData.getPressures();
    }

    @Override
    public int getWindRout() {
        if (collectWeatherData == null){
            createCollectWeatherData(region, sqlQueries, sqlQuery);
        }
        return collectWeatherData.getWindRout();
    }

    @Override
    public int getWindSpeed() {
        if (collectWeatherData == null){
            createCollectWeatherData(region, sqlQueries, sqlQuery);
        }
        return collectWeatherData.getWindSpeed();
    }

    @Override
    public WeatherModel getWeatherByCity(Region region, String sqlQuery){
        if (collectWeatherData == null){
            collectWeatherData = new CollectWeatherData();
        }
        return collectWeatherData.getWeatherByCity(region, sqlQuery);
    }

    private void createCollectWeatherData(Region region, String [] sqlQueries, String sqlQuery){
        collectWeatherData = new CollectWeatherData();
        collectWeatherData.setPressures(region, sqlQueries);
        collectWeatherData.setWindRout(region, sqlQuery);
        collectWeatherData.setWindSpeed(region, sqlQuery);
    }
}
