package archiving.update;

import constant.Constant;
import enumeration.Region;
import model.JSONDataParser;
import model.WeatherModel;
import mysql_connection.DataBaseConnection;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Created by AZagorskyi on 05.04.2017.
 */
public class UpdateWeatherData {
    private static Logger log = Logger.getLogger(UpdateWeatherData.class.getName());

    public void updateWeatherData(WeatherModel model, Region city, String sqlQuery){
        try{
            Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, String.valueOf(model.getWindSpeed()));
            statement.setString(2, String.valueOf(model.getWindRout()));
            statement.setString(3, String.valueOf(model.getPressure()));
            statement.setString(4, city.getRegion());
            statement.execute();
            statement.close();
            connection.close();
        } catch (SQLException sql){
            log.info(sql.getSQLState());
            sql.printStackTrace();
        } catch (ClassNotFoundException e) {
            log.info(e.toString());
            e.printStackTrace();
        }
    }

    public void updateWeatherDataFromDynamicJSON(Region city){
        JSONDataParser jsonDataParser = new JSONDataParser();
        JSONObject objectWeatherData = null;
        try {
            objectWeatherData = jsonDataParser.parseWeatherDataJson(Constant.WEATHER_DATA_TODAY_URL, city);
            WeatherModel presentWeather = jsonDataParser.getPresentWeather(objectWeatherData);
            WeatherModel futureWeather = jsonDataParser.getFutureWeather(objectWeatherData);
            updateWeatherData(presentWeather, city, Constant.SQL_QUERY_UPDATE_TODAY_WEATHER);
            updateWeatherData(futureWeather, city, Constant.SQL_QUERY_UPDATE_TOMORROW_WEATHER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
