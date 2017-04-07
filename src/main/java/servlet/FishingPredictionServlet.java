package servlet;

import archiving.collect.CollectWeatherData;
import constant.Constant;
import enumeration.Region;
import model.JSONDataParser;
import model.WeatherModel;
import fishing_prediction.service.Service;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by AZagorskyi on 22.03.2017.
 */
@WebServlet("/fishingPrediction")
public class FishingPredictionServlet extends HttpServlet {
    private Region city = null;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException{
        JSONDataParser jsonDataParser = new JSONDataParser();

        JSONObject objectMoonDay = jsonDataParser.parseMoonDataJson(Constant.MOON_DATA_URL);
        int moonDay = jsonDataParser.getMoonDayTomorrow(objectMoonDay);

        Region city = getCity(request.getParameter("city"));
        JSONObject objectWeatherData = jsonDataParser.parseWeatherDataJson(Constant.WEATHER_DATA_TODAY_URL, city);
        WeatherModel presentWeather = jsonDataParser.getPresentWeather(objectWeatherData);
        WeatherModel futureWeather = jsonDataParser.getFutureWeather(objectWeatherData);

        Date today = new Date(presentWeather.getTimeInSeconds()*1000);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);


        String [] sqlQuery = {Constant.SQL_QUERY_SELECT_THIRD_YESTERDAY_WEATHER, Constant.SQL_QUERY_SELECT_TWICE_YESTERDAY_WEATHER,
                Constant.SQL_QUERY_SELECT_ONCE_YESTERDAY_WEATHER, Constant.SQL_QUERY_SELECT_TODAY_WEATHER,
                Constant.SQL_QUERY_SELECT_TOMORROW_WEATHER};
        CollectWeatherData collectWeatherData = new CollectWeatherData();
        List<Integer> pressures = collectWeatherData.getPressures(city, sqlQuery);
        pressures.add(presentWeather.getPressure());
        pressures.add(futureWeather.getPressure());
        int rating = Service.calculatePeacePrediction(moonDay, calendar.get(Calendar.MONTH), pressures);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(moonDay);
        response.getWriter().write(presentWeather.toString());
        response.getWriter().write(futureWeather.toString());
        response.getWriter().write(rating);
    }

    @Override
    public void doPost(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException,IOException{
        doGet(httpRequest, httpResponse);
    }

    private Region getCity(String urlParameter){
        Region city = Region.Kyiv;
        if (urlParameter.equals(Region.valueOf(urlParameter).getCity())){
            city = Region.valueOf(urlParameter);
        }
        return city;
    }
}
