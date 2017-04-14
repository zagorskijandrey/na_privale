package servlet;

import archiv.collect.ICollectWeatherData;
import archiv.collect.proxy.ProxyCollectWeatherData;
import constant.Constant;
import enumeration.Region;
import model.DateModel;
import json_parser.JSONToObjectParserForWeather;
import fishing_prediction.service.ServiceForPeaceFish;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by AZagorskyi on 22.03.2017.
 */
@WebServlet("/fishingPrediction")
public class FishingPredictionServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException{
        JSONToObjectParserForWeather jsonDataParserForWeather = new JSONToObjectParserForWeather();

        JSONObject objectMoonDay = jsonDataParserForWeather.parseMoonDataJson(Constant.MOON_DATA_URL);
        int moonDay = jsonDataParserForWeather.getMoonDayTomorrow(objectMoonDay);

        Region region = getCity(request.getParameter("region"));

        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        DateModel dateModel = new DateModel(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH));

        ICollectWeatherData proxyCollectWeatherData = new ProxyCollectWeatherData(region, Constant.SQL_QUERY_SELECT_TOMORROW_WEATHER);
        List<Integer> pressures = proxyCollectWeatherData.getPressures();
        int windRout = proxyCollectWeatherData.getWindRout();
        int windSpeed = proxyCollectWeatherData.getWindSpeed();

        int rating = new ServiceForPeaceFish().calculatePeacePrediction(moonDay, dateModel.getMonth(),
                pressures, windRout, windSpeed);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.valueOf(moonDay));
        response.getWriter().write(String.valueOf(rating));
    }

    @Override
    public void doPost(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException,IOException{
        doGet(httpRequest, httpResponse);
    }

    private Region getCity(String urlParameter){
        Region city = Region.Kiev;
        if (urlParameter.equals(Region.valueOf(urlParameter).toString())){
            city = Region.valueOf(urlParameter);
        }
        return city;
    }
}
