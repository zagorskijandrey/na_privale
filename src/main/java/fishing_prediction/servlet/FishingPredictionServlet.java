package fishing_prediction.servlet;

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

/**
 * Created by AZagorskyi on 22.03.2017.
 */
@WebServlet("/fishingPrediction")
public class FishingPredictionServlet extends HttpServlet {
    private String moonDataUrl = "http://api.burningsoul.in/moon";
    private String weatherDataTodayUrl = "http://api.openweathermap.org/data/2.5/forecast?q=XXXXX,ua&APPID=bf64caf37de45d7b2e9751adc28f384a";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException{
        JSONObject objectMoonDay = JSONDataParser.parseMoonDataJson(moonDataUrl);
        int moonDay = JSONDataParser.getMoonDayTomorrow(objectMoonDay);

        JSONObject objectWeatherData = JSONDataParser.parseWeatherDataJson(weatherDataTodayUrl, request.getParameter("city"));
        WeatherModel presentWeather = JSONDataParser.getPresentWeather(objectWeatherData);
        WeatherModel futureWeather = JSONDataParser.getFutureWeather(objectWeatherData);

        Date today = new Date(presentWeather.getTimeInSeconds()*1000);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);


        ArrayList<Integer> pressures = new ArrayList<Integer>();
        pressures.add(presentWeather.getPressure());
        pressures.add(presentWeather.getPressure());
        int rating = Service.calculatePeacePrediction(moonDay, calendar.get(Calendar.MONTH), pressures);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(moonDay);
        response.getWriter().write(presentWeather.toString());
        response.getWriter().write(futureWeather.toString());
        response.getWriter().write(rating);
    }
}
