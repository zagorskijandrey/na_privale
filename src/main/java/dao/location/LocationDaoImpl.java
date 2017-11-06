/**
 * Created by AZagorskyi on 06.11.2017.
 */
package dao.location;

import model.Hamlet;
import mysql_connection.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

public class LocationDaoImpl implements LocationDao{

    private String sqlQuery;

    public LocationDaoImpl(String sqlQuery){
        this.sqlQuery = sqlQuery;
    }

    @Override
    public Map<Integer, String> getCountries() {
        Map<Integer, String> countries = null;
        try {
            Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            ResultSet result = statement.executeQuery();
            countries = new TreeMap<Integer, String>();
            while (result.next()) {
                countries.put(Integer.parseInt(result.getString("id_country")), result.getString("name"));
            }
            result.close();
            statement.close();
            connection.close();

        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return countries;
    }

    @Override
    public Map<Integer, String> getProvincesByCountryId(Integer id) {
        Map<Integer, String> provinces = null;
        try {
            Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            provinces = new TreeMap<Integer, String>();
            while (result.next()) {
                provinces.put(Integer.parseInt(result.getString("id_province")), result.getString("name"));
            }
            result.close();
            statement.close();
            connection.close();

        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return provinces;
    }

    @Override
    public Map<Integer, String> getRegionsByProvinceId(Integer id) {
        Map<Integer, String> regions = null;
        try {
            Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            regions = new TreeMap<Integer, String>();
            while (result.next()) {
                regions.put(Integer.parseInt(result.getString("id_reg")), result.getString("name"));
            }
            result.close();
            statement.close();
            connection.close();

        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return regions;
    }

    @Override
    public Map<Integer, String> getHamletsByRegionId(Integer id) {
        Map<Integer, String> hamlets = null;
        try {
            Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            hamlets = new TreeMap<Integer, String>();
            while (result.next()) {
                hamlets.put(Integer.parseInt(result.getString("id_hamlet")), result.getString("name"));
            }
            result.close();
            statement.close();
            connection.close();

        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return hamlets;
    }
}
