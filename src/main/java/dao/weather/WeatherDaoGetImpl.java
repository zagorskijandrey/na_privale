package dao.weather;

import model.Moon;
import model.WeatherModel;
import mysql_connection.DataBaseConnection;

import java.sql.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * Created by AZagorskyi on 06.04.2017.
 */
public class WeatherDaoGetImpl implements WeatherDaoGet {
    private static Logger log = Logger.getLogger(WeatherDaoGetImpl.class.getName());

    public Map<Integer, WeatherModel> getLastWeatherForRegions(String sqlQuery) {
        Map<Integer, WeatherModel> mapWeather = null;
        try {
            mapWeather = new ConcurrentHashMap<Integer, WeatherModel>();
            Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            ResultSet result = statement.executeQuery();
            while (result.next()){
                WeatherModel model = new WeatherModel();
                model.setWindSpeed(Integer.parseInt(result.getString("wind_speed")));
                model.setWindRout(Integer.parseInt(result.getString("wind_rout")));
                mapWeather.put(Integer.parseInt(result.getString("id_region")), model);
            }
            result.close();
            statement.close();
            connection.close();
        } catch (SQLException sql){
            log.info(sql.getSQLState());
            sql.printStackTrace();
        } catch (ClassNotFoundException e) {
            log.info(e.toString());
            e.printStackTrace();
        }
        return mapWeather;
    }

    public WeatherModel getLastWeatherByRegionId(String regionId, String sqlQuery){
        WeatherModel model = null;
        try {
            model = new WeatherModel();
            Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, regionId);
            ResultSet result = statement.executeQuery();
            while (result.next()){
                model.setDate(result.getDate("time"));
                model.setWindSpeed(Integer.parseInt(result.getString("wind_speed")));
                model.setWindRout(Integer.parseInt(result.getString("wind_rout")));
            }
            result.close();
            statement.close();
            connection.close();
        } catch (SQLException sql){
            log.info(sql.getSQLState());
            sql.printStackTrace();
        } catch (ClassNotFoundException e) {
            log.info(e.toString());
            e.printStackTrace();
        }
        return model;
    }

    public ArrayList<Integer> getPressuresByRegionId(String regionId, String sqlQuery, WeatherModel model){
        ArrayList<Integer> pressures = null;
        try {
            pressures = new ArrayList<Integer>();
            Connection connection = DataBaseConnection.getConnection();
            Calendar calendar = new GregorianCalendar();
            calendar.add(Calendar.DAY_OF_YEAR, -3);
            Timestamp timeFrom = new Timestamp(calendar.getTime().getTime());
            calendar.add(Calendar.DAY_OF_YEAR, 5);
            Timestamp timeTo = new Timestamp(calendar.getTime().getTime());
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, regionId);
            statement.setTimestamp(2, timeFrom);
            statement.setTimestamp(3, timeTo);
            ResultSet result = statement.executeQuery();
            while (result.next()){
                pressures.add(Integer.parseInt(result.getString("pressure")));
            }
            result.close();
            statement.close();
            connection.close();
        } catch (SQLException sql){
            log.info(sql.getSQLState());
            sql.printStackTrace();
        } catch (ClassNotFoundException e) {
            log.info(e.toString());
            e.printStackTrace();
        }
        log.info("This collect setPressures");
        return pressures;
    }

    public Moon getLastMoonDate(String sqlQuery){
        Moon moon = null;
        try {
            moon = new Moon();
            Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            ResultSet result = statement.executeQuery();
            while (result.next()){
                moon.setPhase(result.getInt("moon_phase"));
                moon.setCreateTime(result.getDate("time"));
                moon.setDistance(result.getFloat("distance"));
            }
            result.close();
            statement.close();
            connection.close();
        } catch (SQLException sql){
            log.info(sql.getSQLState());
            sql.printStackTrace();
        } catch (ClassNotFoundException e) {
            log.info(e.toString());
            e.printStackTrace();
        }
        return moon;
    }
}