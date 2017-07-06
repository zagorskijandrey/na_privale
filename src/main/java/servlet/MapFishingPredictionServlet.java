package servlet;

import archiv.collect.CollectWeatherData;
import archiv.collect.ICollectWeatherData;
import archiv.get.GetPredictionData;
import constant.Constant;
import enumeration.RegionEnum;
import json_parser.ObjectToJSONParserForPrediction;
import model.DateModel;
import json_parser.JSONToObjectParserForWeather;
import fishing_prediction.service.ServiceForPeaceFish;
import model.Region;
import model.WeatherModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by AZagorskyi on 22.03.2017.
 */
@WebServlet("/mapFishingPrediction")
public class MapFishingPredictionServlet extends HttpServlet {
    private BaseHandler handler = null;

    @Override
    public void init(){
        handler = new BaseHandler();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException{
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
        JSONArray jsonArray = objectToJSONParserForPrediction.getJSONArrayRegions(regionList);
        JSONObject object = null;
        handler.setDefaultHeader(response);
        if (jsonArray.size() > 0){
            object = new JSONObject();
            object.put("regions", jsonArray);
            handler.responseFactory(response, object, null);
        } else {
            String error = "Ошибка сервиса!";
            response.setStatus(400);
            handler.responseFactory(response, null, error);
        }
    }

    @Override
    public void doPost(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException,IOException{
        doGet(httpRequest, httpResponse);
    }

    private RegionEnum getCity(String urlParameter){
        RegionEnum city = RegionEnum.Kiev;
        if (urlParameter.equals(RegionEnum.valueOf(urlParameter).toString())){
            city = RegionEnum.valueOf(urlParameter);
        }
        return city;
    }
}
