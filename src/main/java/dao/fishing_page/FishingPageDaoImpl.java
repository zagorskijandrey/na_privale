/**
 * Created by AZagorskyi on 04.09.2017.
 */
package dao.fishing_page;

import constant.Constant;
import model.Fish;
import model.FishingPage;
import mysql_connection.DataBaseConnection;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class FishingPageDaoImpl implements FishingPageDao {
    private String sqlQuery = null;
    private int countFishingPages = -1;

    public FishingPageDaoImpl(){
    }

    public FishingPageDaoImpl(String sql){
        this.sqlQuery = sql;
    }

    @Override
    public void saveFishingPage(String username, FishingPage fishingPage) throws ClassNotFoundException, SQLException {
        Connection connection = DataBaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(Constant.SQL_QUERY_SAVE_FISHING_PAGE, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, fishingPage.getProvince());
        statement.setString(2, fishingPage.getRegion());
        statement.setString(3, fishingPage.getHamlet());
        statement.setString(4, fishingPage.getComment());
        statement.setTimestamp(5, new Timestamp(fishingPage.getDate().getTime()));
        statement.setString(6, username);
        statement.setInt(7, fishingPage.getIdHamlet());
        statement.setInt(8, fishingPage.getIdProvince());
        statement.executeUpdate();

        ResultSet keys = statement.getGeneratedKeys();
        keys.next();
        int idPage = keys.getInt(1);
        statement.close();

        saveFishes(idPage, fishingPage);
        DataBaseConnection.disconnect();
    }

    @Override
    public FishingPage getFishingPageById(int idPage){
        FishingPage fishingPage = null;
        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(this.sqlQuery);
            preparedStatement.setInt(1, idPage);
            ResultSet result = preparedStatement.executeQuery();
            fishingPage = new FishingPage();
            while (result.next()) {
                fishingPage.setId(result.getInt("id_page"));
                fishingPage.setProvince(result.getString("province"));
                fishingPage.setRegion(result.getString("region"));
                fishingPage.setHamlet(result.getString("hamlet"));
                fishingPage.setComment(result.getString("comment"));
                fishingPage.setDate(result.getDate("date"));
                fishingPage.setIdProvince(result.getInt("id_province"));
            }
            result.close();
            preparedStatement.close();
            DataBaseConnection.disconnect();
            fishingPage.setFishes(getFishes(idPage));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fishingPage;
    }

    @Override
    public List<FishingPage> getFishingPageList(String username, int start, int total, String filter, String sort) {
        List<FishingPage> fishingPageList = null;
        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnection();
            if (sort.equals("asc") && this.sqlQuery.contains("desc")){
                this.sqlQuery = this.sqlQuery.replaceAll("desc", sort);
            } else {
                if (sort.equals("desc") && this.sqlQuery.contains("asc")){
                    this.sqlQuery = this.sqlQuery.replaceAll("asc", sort);
                }
            }
            PreparedStatement preparedStatement = connection.prepareStatement(this.sqlQuery);
            preparedStatement.setString(1, username);
            if(filter != null){
                preparedStatement.setString(2, filter + "%");
            } else {
                preparedStatement.setString(2, "%");
            }
            preparedStatement.setInt(3, start);
            preparedStatement.setInt(4, total);
            ResultSet result = preparedStatement.executeQuery();
            fishingPageList = new ArrayList<FishingPage>();
            while (result.next()) {
                FishingPage fishingPage = new FishingPage();
                setFishingPage(fishingPage, result);
                fishingPage.setFishes(getFishes(fishingPage.getId()));
                fishingPageList.add(fishingPage);
            }
            Statement statement = connection.createStatement();
            ResultSet resultCount = statement.executeQuery("SELECT FOUND_ROWS()");
            if (resultCount.next()) {
                this.countFishingPages = resultCount.getInt(1);
            }
            result.close();
            resultCount.close();
            preparedStatement.close();
            statement.close();
            DataBaseConnection.disconnect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fishingPageList;
    }

    public Map<Date, String> getHamletsDescription(String username, int idHamlet){
        Map<Date, String> hamletsDescription = null;
        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(this.sqlQuery);
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, idHamlet);
            ResultSet result = preparedStatement.executeQuery();
            hamletsDescription = new HashMap<Date, String>();
            while (result.next()) {
                hamletsDescription.put(result.getDate("date"), result.getString("comment"));
            }
            Statement statement = connection.createStatement();
//            ResultSet resultCount = statement.executeQuery("SELECT FOUND_ROWS()");
//            if (resultCount.next()) {
//                this.countFishingPages = resultCount.getInt(1);
//            }
            result.close();
//            resultCount.close();
            preparedStatement.close();
            statement.close();
            DataBaseConnection.disconnect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hamletsDescription;
    }

    private void saveFishes(int idPage, FishingPage fishingPage) throws ClassNotFoundException, SQLException {
        if (fishingPage.getFishes().size() > 0){
            Connection connection = DataBaseConnection.getConnection();
            for (int i=0; i<fishingPage.getFishes().size(); i++){
                PreparedStatement statement = connection.prepareStatement(Constant.SQL_QUERY_SAVE_FISHES);
                statement.setString(1, fishingPage.getFishes().get(i).getName());
                statement.setFloat(2, fishingPage.getFishes().get(i).getWeight());
                statement.setFloat(3, fishingPage.getFishes().get(i).getDistance());
                statement.setString(4, fishingPage.getFishes().get(i).getBait());
                statement.setString(5, fishingPage.getFishes().get(i).getTime());
                statement.setInt(6, idPage);
                statement.executeUpdate();
                statement.close();
            }
        }
    }

    private List<Fish> getFishes(int idPage) throws ClassNotFoundException, SQLException {
        List<Fish> fishes = null;
        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.SQL_QUERY_GET_FISHES_BY_FISHING_PAGE_ID);
            preparedStatement.setInt(1, idPage);
            ResultSet result = preparedStatement.executeQuery();
            fishes = new ArrayList<Fish>();
            while (result.next()) {
                Fish fish = new Fish();
                fish.setId(result.getInt("id_fish"));
                fish.setName(result.getString("name"));
                fish.setWeight(result.getFloat("weight"));
                fish.setDistance(result.getFloat("distance"));
                fish.setBait(result.getString("bait"));
                fish.setTime(result.getString("time"));
                fishes.add(fish);
            }
            result.close();
            preparedStatement.close();
            DataBaseConnection.disconnect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fishes;
    }

    private void setFishingPage(FishingPage fishingPage, ResultSet result) {
        try {
            fishingPage.setId(result.getInt("id_page"));
            fishingPage.setProvince(result.getString("province"));
            fishingPage.setRegion(result.getString("region"));
            fishingPage.setHamlet(result.getString("hamlet"));
            fishingPage.setComment(result.getString("comment"));
            fishingPage.setDate(result.getDate("date"));
            fishingPage.setIdProvince(result.getInt("id_province"));
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    @Override
    public int getCountFishingPages() {
        return this.countFishingPages;
    }
}
