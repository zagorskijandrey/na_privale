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
import java.util.logging.Logger;

/**
 * Created by AZagorskyi on 06.04.2017.
 */
public class CollectWeatherData {
    private static Logger log = Logger.getLogger(CollectWeatherData.class.getName());

    public List<Integer> getPressures(Region city, String [] sqlQuery){
        List<Integer> pressures = new ArrayList<Integer>();
        for (int i = 0; i < sqlQuery.length; i++){
            WeatherModel weatherModel = getWeatherByCity(city, sqlQuery[i]);
            pressures.add(weatherModel.getPressure());
        }
        return pressures;
    }

    public int getWindRout(Region city, String sqlQuery){
        WeatherModel model = getWeatherByCity(city, sqlQuery);
        return model.getWindRout();
    }

    public int getWindSpeed(Region city, String sqlQuery){
        WeatherModel model = getWeatherByCity(city, sqlQuery);
        return model.getWindSpeed();
    }

    public WeatherModel getWeatherByCity(Region city, String sqlQuery){
        WeatherModel model = null;
        try {
            model = new WeatherModel();
            Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1,city.getRegion());
            ResultSet result = statement.executeQuery();
            while (result.next()){
                model.setWindSpeed(Integer.parseInt(result.getString("wind_speed")));
                model.setWindRout(Integer.parseInt(result.getString("wind_rout")));
                model.setPressure(Integer.parseInt(result.getString("pressure")));
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
}
