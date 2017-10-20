/**
 * Created by AZagorskyi on 04.09.2017.
 */
package dao.fishing_page;

import constant.Constant;
import dao.fishing_page.FishingPageDao;
import model.FishingPage;
import mysql_connection.DataBaseConnection;

import java.sql.*;

public class FishingPageDaoImpl implements FishingPageDao {

    public void saveFishingPage(String username, FishingPage fishingPage) throws ClassNotFoundException, SQLException {
        Connection connection = DataBaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(Constant.SQL_QUERY_SAVE_FISHING_PAGE, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, fishingPage.getProvince());
        statement.setString(2, fishingPage.getRegion());
        statement.setString(3, fishingPage.getHamlet());
        statement.setString(4, fishingPage.getComment());
        statement.setTimestamp(5, new Timestamp(fishingPage.getDate().getTime()));
        statement.setString(6, username);
        statement.executeUpdate();

        ResultSet keys = statement.getGeneratedKeys();
        keys.next();
        int idPage = keys.getInt(1);
        statement.close();

        saveFishes(idPage, fishingPage);
        DataBaseConnection.disconnect();
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
}
