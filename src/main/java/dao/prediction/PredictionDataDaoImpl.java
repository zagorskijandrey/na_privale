package dao.prediction;

import model.Region;
import mysql_connection.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by AZagorskyi on 04.07.2017.
 */
public class PredictionDataDaoImpl implements PredictionDataDao{
    private String sqlQuery = null;

    public PredictionDataDaoImpl(String sqlQuery) {
        this.sqlQuery = sqlQuery;
    }

    public ArrayList<Region> getPredictionForRegions() {
        ArrayList<Region> storiesList = null;
        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            ResultSet result = statement.executeQuery();
            storiesList = new ArrayList<Region>();
            while (result.next()) {
                Region region = new Region();
                setPredictionForRegion(region, result);
                storiesList.add(region);
            }
            result.close();
            statement.close();
            DataBaseConnection.disconnect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return storiesList;
    }

    private void setPredictionForRegion(Region region, ResultSet result) {
        try {
            region.setId(Integer.parseInt(result.getString("id_region")));
            region.setName(result.getString("name"));
            region.setLongitude(Double.parseDouble(result.getString("lon")));
            region.setLatitude(Double.parseDouble(result.getString("lat")));
        } catch (SQLException e) {
            e.getMessage();
        }
    }
}
