package archiving.saving;

import enumeration.Region;
import model.WeatherModel;
import mysql_connection.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 * Created by AZagorskyi on 05.04.2017.
 */
public class UpdateWeatherData {
    private String sqlUpdateYesterday = "UPDATE yesterday_weather SET yesterday_wind_speed=?, " +
            "yesterday_wind_rout=?, yesterday_pressure=? WHERE city=?";
    private String sqlUpdateBeforeYesterday = "UPDATE before_yesterday_weather SET before_yesterday_wind_speed=?, " +
            "before_yesterday_wind_rout=?, before_yesterday_pressure=? WHERE city=?";
    private String sqlYesterdayQuery = "SELECT * FROM yesterday_weather WHERE city=?";

    public void updateYesterdayData(WeatherModel model, Region city){
        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try{
            PreparedStatement statement = connection.prepareStatement(sqlUpdateYesterday);
            statement.setString(0, String.valueOf(model.getWindSpeed()));
            statement.setString(1, String.valueOf(model.getWindRout()));
            statement.setString(2, String.valueOf(model.getPressure()));
            statement.setString(3, city.getCity());
            statement.execute();
            statement.close();
        } catch (SQLException sql){
            sql.printStackTrace();
        }
    }

    public void updateBeforeYesterdayData(Region city){
        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try{
            WeatherModel model = getYesterdayData(city, sqlYesterdayQuery);
            PreparedStatement statement = connection.prepareStatement(sqlUpdateBeforeYesterday);
            statement.setString(0, String.valueOf(model.getWindSpeed()));
            statement.setString(1, String.valueOf(model.getWindRout()));
            statement.setString(2, String.valueOf(model.getPressure()));
            statement.setString(3, city.getCity());
            statement.execute();
            statement.close();
        } catch (SQLException sql){
            sql.printStackTrace();
        }
    }

    private WeatherModel getYesterdayData(Region city, String sqlQuery){
        WeatherModel model = null;
        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try{
            model = new WeatherModel();
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1,city.getCity());
            ResultSet result = statement.executeQuery();
            model.setWindSpeed(Integer.parseInt(result.getString("yesterday_wind_speed")));
            model.setWindRout(Integer.parseInt(result.getString("yesterday_wind_rout")));
            model.setPressure(Integer.parseInt(result.getString("yesterday_pressure")));
//            while (result.next()){
//                int id = Integer.parseInt(result.getString("id_fishing_story"));
//                String name = result.getString("fishing_story_name");
//                String history = result.getString("fishing_story");
//
//                jsonObject.put("id", id);
//                jsonObject.put("name", name);
//                jsonObject.put("history", history);
//            }
            result.close();
            statement.close();
        } catch (SQLException sql){
            sql.printStackTrace();
        }
        return model;
    }
}
