package model;

import enumeration.Region;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by AZagorskyi on 05.04.2017.
 */
public class JSONDataParser {
    public static JSONObject parseMoonDataJson(String moonDataUrl) throws IOException {
        URL url = new URL(moonDataUrl);
        URLConnection connection = url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
        JSONObject object = null;
        try {
            object = (JSONObject) parser.parse(bufferedReader);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return object;
    }

    public static int getMoonDayTomorrow(JSONObject object){
        Long valueLong = Math.round(Double.parseDouble((object.get("age")).toString()));
        Integer moonDay = valueLong.intValue() + 1;
        if (moonDay == 30){
            moonDay = 1;
        }
        return moonDay;
    }

    public static JSONObject parseWeatherDataJson(String weatherDataTodayUrl, String city) throws IOException{
        String trueUrl = null;
        if (city.equals(Region.valueOf(city).getCity())){
            trueUrl = weatherDataTodayUrl.replace("XXXXX", city);
        }
        URL url = new URL(trueUrl);
        URLConnection connection = url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
        JSONObject object = null;
        try {
            object = (JSONObject) parser.parse(bufferedReader);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return object;
    }

    public static WeatherModel getPresentWeather(JSONObject object){
        WeatherModel presentWeather = new WeatherModel();
        JSONObject dt = getJSONObjectByName(object, "list", "dt", 1);
        JSONObject main = getJSONObjectByName(object, "list", "main", 1);
        JSONObject wind = getJSONObjectByName(object, "list", "wind", 1);

        Long timeSeconds = Long.parseLong(dt.get("dt").toString());
        presentWeather.setTimeInSeconds(timeSeconds);

        Long speed = Math.round(Double.parseDouble((wind.get("speed")).toString()));
        presentWeather.setWindSpeed(speed.intValue());

        Long degree = Math.round(Double.parseDouble((wind.get("deg")).toString()));
        presentWeather.setWindRout(degree.intValue());

        Long pressure = Math.round(Double.parseDouble((main.get("pressure")).toString()));
        presentWeather.setPressure(pressure.intValue());

        return presentWeather;
    }

    public static WeatherModel getFutureWeather(JSONObject object){
        WeatherModel presentWeather = new WeatherModel();
        JSONObject dt = getJSONObjectByName(object, "list", "dt", 10);
        JSONObject main = getJSONObjectByName(object, "list", "main", 10);
        JSONObject wind = getJSONObjectByName(object, "list", "wind", 10);
        presentWeather.setTimeInSeconds(Long.parseLong(dt.get("dt").toString()));

        Long speed = Math.round(Double.parseDouble((wind.get("speed")).toString()));
        presentWeather.setWindSpeed(speed.intValue());

        Long degree = Math.round(Double.parseDouble((wind.get("deg")).toString()));
        presentWeather.setWindRout(degree.intValue());

        Long pressure = Math.round(Double.parseDouble((main.get("pressure")).toString()));
        presentWeather.setPressure(pressure.intValue());

        return presentWeather;
    }

    private static JSONObject getJSONObjectByName(JSONObject object, String nameArray, String nameObject, int index){
        JSONArray array = (JSONArray) object.get(nameArray);
        JSONObject objectFromArray = null;
        JSONObject ob = (JSONObject) array.get(index);
        if (ob.containsKey(nameObject)){
            if (nameObject.equals("dt")){
                objectFromArray = new JSONObject();
                objectFromArray.put("dt", ob.get("dt"));
            } else {
                objectFromArray = (JSONObject) ob.get(nameObject);
            }
        }
        return objectFromArray;
    }
}