package service;

import constant.Constant;
import dao.prediction.PredictionDataDao;
import fishing_prediction.service.ServiceForPeaceFish;
import json_parser.ObjectToJSONParserForPrediction;
import model.DateModel;
import model.Moon;
import model.Region;
import model.WeatherModel;
import org.json.simple.JSONArray;
import dao.weather.WeatherDaoGetImpl;
import dao.weather.WeatherDaoGet;
import dao.prediction.PredictionDataDaoImpl;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * Created by AZagorskyi on 29.08.2017.
 */
public class CalculatePredictionPeace extends CalculatePrediction{

    @Override
    public JSONArray calculatePrediction() throws IOException {
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        DateModel dateModel = new DateModel(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH));

        WeatherDaoGet collectWeatherData = new WeatherDaoGetImpl();
        Map<Integer, WeatherModel> mapWeather = collectWeatherData.getLastWeatherForRegions(Constant.SQL_QUERY_SELECT_WEATHER_ALL_REGIONS);
        Moon moon = collectWeatherData.getLastMoonDate(Constant.SQL_QUERY_SELECT_LAST_MOON);
        PredictionDataDao regionData = new PredictionDataDaoImpl(Constant.SQL_QUERY_SELECT_PREDICTIONS);
        ArrayList<Region> regionList = regionData.getPredictionForRegions();
        for (Region region: regionList){
            WeatherModel weatherModel = mapWeather.get(region.getId());
            List<Integer> pressures = collectWeatherData.getPressuresByRegionId(Constant.SQL_QUERY_SELECT_PRESSURES, region.getId(), null);
            int windRout = weatherModel.getWindRout();
            int windSpeed = weatherModel.getWindSpeed();
            Map<String, Integer> mapMarks = new ServiceForPeaceFish().calculatePeacePrediction(moon.getPhase(), dateModel.getMonth(),
                    pressures, windRout, windSpeed);

            int ratingSum = 0;
            for(Map.Entry<String, Integer> pare: mapMarks.entrySet()){
                ratingSum += pare.getValue();
            }
//            int rating = new ServiceForPeaceFish().calculatePeacePrediction(moon.getPhase(), dateModel.getMonth(),
//                    pressures, windRout, windSpeed);
            region.setPrediction(ratingSum/5);
        }

        ObjectToJSONParserForPrediction objectToJSONParserForPrediction = new ObjectToJSONParserForPrediction();
        return objectToJSONParserForPrediction.getJSONArrayRegions(regionList);
    }

    @SuppressWarnings("unchecked")
    public JSONObject calculatePastPrediction(int provinceId, Date date) throws IOException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);

        WeatherDaoGet collectWeatherData = new WeatherDaoGetImpl();
        WeatherModel weatherModel = collectWeatherData.getWeatherByProvinceIdAndDate(Constant.SQL_QUERY_SELECT_WEATHER_BY_PROVINCE_ID, provinceId, date);
        int phase = collectWeatherData.getMoonPhaseByDate(Constant.SQL_QUERY_SELECT_MOON_BY_DATE, date);
        List<Integer> pressures = collectWeatherData.getPressuresByRegionId(Constant.SQL_QUERY_SELECT_PRESSURES, provinceId, date);
        int windRout = weatherModel.getWindRout();
        int windSpeed = weatherModel.getWindSpeed();
        Map<String, Integer> mapMarks = new ServiceForPeaceFish().calculatePeacePrediction(phase, month,
                pressures, windRout, windSpeed);

        JSONObject jsonObject = new JSONObject();
        for(Map.Entry<String, Integer> pare: mapMarks.entrySet()){
            jsonObject.put(pare.getKey(), pare.getValue());
        }
        return jsonObject;
    }
}
