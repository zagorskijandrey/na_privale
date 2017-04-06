package archiving.update;

import archiving.collect.CollectWeatherData;
import constant.Constant;
import enumeration.Region;
import model.WeatherModel;
import mysql_connection.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by AZagorskyi on 05.04.2017.
 */
public class UpdateWeatherData {

    public void updateWeatherData(WeatherModel model, Region city, String sqlQuery){
        try{
            PreparedStatement statement = DataBaseConnection.getConnection().prepareStatement(sqlQuery);
            statement.setString(0, String.valueOf(model.getWindSpeed()));
            statement.setString(1, String.valueOf(model.getWindRout()));
            statement.setString(2, String.valueOf(model.getPressure()));
            statement.setString(3, city.getCity());
            statement.execute();
            statement.close();
        } catch (SQLException sql){
            sql.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateBeforeYesterdayData(Region city){
//        try{
//            WeatherModel model = CollectWeatherData.getWeatherByCity(city, Constant.SQL_QUERY_SELECT_YESTERDAY_WEATHER);
//            PreparedStatement statement = DataBaseConnection.getConnection().prepareStatement(Constant.SQL_QUERY_UPDATE_BEFORE_YESTERDAY);
//            statement.setString(0, String.valueOf(model.getWindSpeed()));
//            statement.setString(1, String.valueOf(model.getWindRout()));
//            statement.setString(2, String.valueOf(model.getPressure()));
//            statement.setString(3, city.getCity());
//            statement.execute();
//            statement.close();
//        } catch (SQLException sql){
//            sql.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
    }
}
