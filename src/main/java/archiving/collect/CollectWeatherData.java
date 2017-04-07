package archiving.collect;

import enumeration.Region;
import model.WeatherModel;
import mysql_connection.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AZagorskyi on 06.04.2017.
 */
public class CollectWeatherData {

    public List<Integer> getPressures(Region city, String [] sqlQuery){
        List<Integer> pressures = new ArrayList<Integer>();
        for (int i = 0; i < sqlQuery.length; i++){
            WeatherModel weatherModel = getWeatherByCity(city, sqlQuery[i]);
            pressures.add(weatherModel.getPressure());
        }
        return pressures;
    }

    public WeatherModel getWeatherByCity(Region city, String sqlQuery){
        WeatherModel model = null;
        try{
            model = new WeatherModel();
            PreparedStatement statement = DataBaseConnection.getConnection().prepareStatement(sqlQuery);
            statement.setString(1,city.getCity());
            ResultSet result = statement.executeQuery();
            while (result.next()){
                model.setWindSpeed(Integer.parseInt(result.getString("wind_speed")));
                model.setWindRout(Integer.parseInt(result.getString("wind_rout")));
                model.setPressure(Integer.parseInt(result.getString("pressure")));
            }
            result.close();
            statement.close();
        } catch (SQLException sql){
            sql.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return model;
    }
}
