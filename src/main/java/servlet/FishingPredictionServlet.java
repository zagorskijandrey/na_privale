package servlet;

import archiving.collect.CollectWeatherData;
import constant.Constant;
import enumeration.Region;
import model.JSONDataParser;
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
        JSONDataParser jsonDataParser = new JSONDataParser();

        JSONObject objectMoonDay = jsonDataParser.parseMoonDataJson(Constant.MOON_DATA_URL);
        int moonDay = jsonDataParser.getMoonDayTomorrow(objectMoonDay);

        Region city = getCity(request.getParameter("city"));

        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);


        String [] sqlQuery = {Constant.SQL_QUERY_SELECT_THIRD_YESTERDAY_WEATHER, Constant.SQL_QUERY_SELECT_TWICE_YESTERDAY_WEATHER,
                Constant.SQL_QUERY_SELECT_ONCE_YESTERDAY_WEATHER, Constant.SQL_QUERY_SELECT_TODAY_WEATHER,
                Constant.SQL_QUERY_SELECT_TOMORROW_WEATHER};
        CollectWeatherData collectWeatherData = new CollectWeatherData();
        List<Integer> pressures = collectWeatherData.getPressures(city, sqlQuery);
        int windRout = collectWeatherData.getWindRout(city, Constant.SQL_QUERY_SELECT_TOMORROW_WEATHER);
        int windSpeed = collectWeatherData.getWindSpeed(city, Constant.SQL_QUERY_SELECT_TOMORROW_WEATHER);

        int rating = new ServiceForPeaceFish().calculatePeacePrediction(moonDay, calendar.get(Calendar.MONTH),
                pressures, windRout, windSpeed);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(moonDay);
        response.getWriter().write(rating);
    }

    @Override
    public void doPost(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException,IOException{
        doGet(httpRequest, httpResponse);
    }

    private Region getCity(String urlParameter){
        Region city = Region.Kiev;
        if (urlParameter.equals(Region.valueOf(urlParameter).getRegion())){
            city = Region.valueOf(urlParameter);
        }
        return city;
    }
}
