package dao;

import constant.Constant;
import model.User;
import mysql_connection.DataBaseConnection;

import java.sql.*;
import java.util.Date;

/**
 * Created by andrey on 25.06.2017.
 */
public class UserDaoImpl implements UserDao{

    @Override
    public User getUser(String name, String password) throws ClassNotFoundException, SQLException {
        Connection connection = DataBaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(Constant.SQL_QUERY_GET_USER);
        statement.setString(1, name);
        statement.setString(2, password);
        ResultSet result = statement.executeQuery();
        User user = null;
        while (result.next()){
            user = new User();
            user.setUsername(result.getString("username"));
            user.setPassword(result.getString("password"));
        }
        result.close();
        statement.close();
        DataBaseConnection.disconnect();
        return user;
    }

    @Override
    public boolean saveUser(User user){
        Connection connection = null;
        boolean isSave = false;
        try {
            connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(Constant.SQL_QUERY_GET_USER);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setTimestamp(4, new Timestamp(new Date().getTime()));
            statement.setInt(5, 2);
            statement.close();
            DataBaseConnection.disconnect();
        } catch (ClassNotFoundException e) {
            isSave = false;
        } catch (SQLException e) {
            isSave = false;
        }
        return isSave;
    }
}
