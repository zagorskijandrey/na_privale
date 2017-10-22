package dao.weather;

import constant.Constant;
import enumeration.RegionEnum;
import json_parser.JSONToObjectParserForWeather;
import model.Moon;
import model.WeatherModel;
import mysql_connection.DataBaseConnection;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Created by AZagorskyi on 05.04.2017.
 */
public class WeatherDaoSaveImpl implements WeatherDaoSave {
    private static Logger log = Logger.getLogger(WeatherDaoSaveImpl.class.getName());

    public void saveWeatherDataFromDynamicJSON(RegionEnum regionEnum) {
        JSONToObjectParserForWeather jsonDataParserForWeather = new JSONToObjectParserForWeather();
        JSONObject objectWeatherData = null;
        try {
            objectWeatherData = jsonDataParserForWeather.parseWeatherDataJson(Constant.WEATHER_DATA_TODAY_URL, regionEnum);
            WeatherModel presentWeather = jsonDataParserForWeather.getPresentWeather(objectWeatherData);
            saveWeatherData(presentWeather, regionEnum, Constant.SQL_QUERY_SAVE_WEATHER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveMoonDataFromDynamicJSON(){
        Date date = new Date();
        long seconds = Math.round(date.getTime()/1000);
        JSONToObjectParserForWeather jsonDataParserForWeather = new JSONToObjectParserForWeather();
        JSONObject objectMoonDay = null;
        try {
            objectMoonDay = jsonDataParserForWeather.parseMoonDataJson(Constant.MOON_DATA_URL + seconds);
            Moon moon = jsonDataParserForWeather.getMoonDayTomorrow(objectMoonDay);
            saveMoonData(moon, Constant.SQL_QUERY_SAVE_MOON, date.getTime());
            log.info("Save moon date");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveWeatherData(WeatherModel model, RegionEnum regionEnum, String sqlQuery){
        try{
            Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setTimestamp(1, new Timestamp(model.getDate().getTime()));
            statement.setString(2, String.valueOf(model.getWindSpeed()));
            statement.setString(3, String.valueOf(model.getWindRout()));
            statement.setString(4, String.valueOf(model.getPressure()));
            statement.setString(5, regionEnum.getRegion());
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

    private void saveMoonData(Moon moon, String sqlQuery, long milliseconds){
        try{
            Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, moon.getPhase());
            statement.setTimestamp(2, new Timestamp(milliseconds));
            statement.setFloat(3, moon.getDistance());
            statement.execute();
            statement.close();
            connection.close();
        } catch(SQLException sql) {
            sql.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
