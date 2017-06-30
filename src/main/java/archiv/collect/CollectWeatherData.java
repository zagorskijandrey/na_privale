package archiv.collect;

import enumeration.Region;
import model.WeatherModel;
import mysql_connection.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by AZagorskyi on 06.04.2017.
 */
public class CollectWeatherData implements ICollectWeatherData{
    private static Logger log = Logger.getLogger(CollectWeatherData.class.getName());

    private List<Integer> pressures = null;
    private int windRout = 0;
    private int windSpeed = 0;

//    public void setPressures(Region region, String [] sqlQuery){
//        pressures = new ArrayList<Integer>();
//        for (int i = 0; i < sqlQuery.length; i++){
//            WeatherModel weatherModel = getWeatherByCity(region, sqlQuery[i]);
//            pressures.add(weatherModel.getPressure());
//        }
//        log.info("This collect setPressures");
//    }
//
//    @Override
//    public List<Integer> getPressures(){
//        log.info("This collect getPressures");
//        return pressures;
//    }
//
//    public void setWindRout(Region region, String sqlQuery){
//        WeatherModel model = getWeatherByCity(region, sqlQuery);
//        windRout = model.getWindRout();
//        log.info("This collect setWindRout");
//    }
//
//    @Override
//    public int getWindRout(){
//        log.info("This collect getWindRout");
//        return windRout;
//    }
//
//    public void setWindSpeed(Region region, String sqlQuery){
//        WeatherModel model = getWeatherByCity(region, sqlQuery);
//        windSpeed = model.getWindSpeed();
//        log.info("This collect setWindSpeed");
//    }
//
//    @Override
//    public int getWindSpeed(){
//        log.info("This collect getWindSpeed");
//        return windSpeed;
//    }
//
//    @Override
//    public WeatherModel getWeatherByCity(Region region, String sqlQuery) {
//        return null;
//    }

    public WeatherModel getLastWeatherByRegion(Region region, String sqlQuery){
        WeatherModel model = null;
        try {
            model = new WeatherModel();
            Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1,region.getRegion());
            ResultSet result = statement.executeQuery();
            while (result.next()){
                model.setDate(result.getDate("time"));
                model.setWindSpeed(Integer.parseInt(result.getString("wind_speed")));
                model.setWindRout(Integer.parseInt(result.getString("wind_rout")));
//                model.setPressure(Integer.parseInt(result.getString("pressure")));
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

    public ArrayList<Integer> getPressuresByRegion(Region region, String sqlQuery, WeatherModel model){
        ArrayList<Integer> pressures = null;
        try {
            pressures = new ArrayList<Integer>();
            Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, region.getRegion());
            statement.setTimestamp(2, new Timestamp(model.getDate().getTime()));
            statement.setTimestamp(3, new Timestamp(model.getDate().getTime()));
            ResultSet result = statement.executeQuery();
            while (result.next()){
                pressures.add(Integer.parseInt(result.getString("pressure")));
//                model.setDate(result.getDate("time"));
//                model.setWindSpeed(Integer.parseInt(result.getString("wind_speed")));
//                model.setWindRout(Integer.parseInt(result.getString("wind_rout")));
//                model.setPressure(Integer.parseInt(result.getString("pressure")));
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
}
