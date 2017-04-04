package fishing_prediction.servlet;

import fishing_prediction.enumeration.Region;
import fishing_prediction.model.WeatherModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by AZagorskyi on 22.03.2017.
 */
@WebServlet("/fishingPrediction")
public class FishingPredictionServlet extends HttpServlet {
    private String moonDataUrl = "http://api.burningsoul.in/moon";
    private String weatherDataTodayUrl = "http://api.openweathermap.org/data/2.5/forecast?q=XXXXX,ua&APPID=bf64caf37de45d7b2e9751adc28f384a";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException{
        JSONObject objectMoonDay = parseMoonDataJson(moonDataUrl);
        int moonDay = getMoonDayTomorrow(objectMoonDay);

        JSONObject objectWeatherData = parseWeatherDataJson(weatherDataTodayUrl, request.getParameter("city"));
        WeatherModel presentWeather = getPresentWeather(objectWeatherData);
        WeatherModel futureWeather = getFutureWeather(objectWeatherData);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(moonDay);
        response.getWriter().write(presentWeather.toString());
        response.getWriter().write(futureWeather.toString());
    }

    private JSONObject parseMoonDataJson(String moonDataUrl) throws IOException {
        URL url = new URL(moonDataUrl);
        URLConnection connection = url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        JSONParser parser = new JSONParser();
        JSONObject object = null;
        try {
            object = (JSONObject) parser.parse(bufferedReader);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return object;
    }

    private int getMoonDayTomorrow(JSONObject object){
        Long valueLong = Math.round(Double.parseDouble((object.get("age")).toString()));
        Integer moonDay = valueLong.intValue() + 1;
        if (moonDay == 30){
            moonDay = 1;
        }
        return moonDay;
    }

    private JSONObject parseWeatherDataJson(String weatherDataTodayUrl, String city) throws IOException{
        String trueUrl = null;
        if (city.equals(Region.valueOf(city).getCity())){
            trueUrl = weatherDataTodayUrl.replace("XXXXX", city);
        }
        URL url = new URL(trueUrl);
        URLConnection connection = url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        JSONParser parser = new JSONParser();
        JSONObject object = null;
        try {
            object = (JSONObject) parser.parse(bufferedReader);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return object;
    }

    private WeatherModel getPresentWeather(JSONObject object){
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

    private WeatherModel getFutureWeather(JSONObject object){
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

    private JSONObject getJSONObjectByName(JSONObject object, String nameArray, String nameObject, int index){
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
