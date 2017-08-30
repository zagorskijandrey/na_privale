package service;

import constant.Constant;
import fishing_prediction.service.ServiceForPeaceFish;
import json_parser.JSONToObjectParserForWeather;
import json_parser.ObjectToJSONParserForPrediction;
import model.DateModel;
import model.Region;
import model.WeatherModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import service.collect.CollectWeatherData;
import service.collect.ICollectWeatherData;
import service.get.GetPredictionData;

import java.io.IOException;
import java.util.*;

/**
 * Created by AZagorskyi on 29.08.2017.
 */
public class CalculatePredictionPeace extends CalculatePrediction{

    @Override
    public JSONArray calculatePrediction() throws IOException {
        JSONToObjectParserForWeather jsonDataParserForWeather = new JSONToObjectParserForWeather();

        JSONObject objectMoonDay = jsonDataParserForWeather.parseMoonDataJson(Constant.MOON_DATA_URL);
        int moonDay = jsonDataParserForWeather.getMoonDayTomorrow(objectMoonDay);

        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        DateModel dateModel = new DateModel(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH));

        ICollectWeatherData proxyCollectWeatherData = new CollectWeatherData();
        Map<Integer, WeatherModel> mapWeather = proxyCollectWeatherData.getLastWeatherForRegions(Constant.SQL_QUERY_SELECT_WEATHER_ALL_REGIONS);
        GetPredictionData regionData = new GetPredictionData(Constant.SQL_QUERY_SELECT_PREDICTIONS);
        ArrayList<Region> regionList = regionData.getPredictionForRegions();
        for (Region region: regionList){
            WeatherModel weatherModel = mapWeather.get(region.getId());
            List<Integer> pressures = proxyCollectWeatherData.getPressuresByRegionId(Integer.toString(region.getId()), Constant.SQL_QUERY_SELECT_PRESSURES, weatherModel);
            int windRout = weatherModel.getWindRout();
            int windSpeed = weatherModel.getWindSpeed();
            int rating = new ServiceForPeaceFish().calculatePeacePrediction(moonDay, dateModel.getMonth(),
                    pressures, windRout, windSpeed);
            region.setPrediction(rating);
        }

        ObjectToJSONParserForPrediction objectToJSONParserForPrediction = new ObjectToJSONParserForPrediction();
        return objectToJSONParserForPrediction.getJSONArrayRegions(regionList);
    }
}
