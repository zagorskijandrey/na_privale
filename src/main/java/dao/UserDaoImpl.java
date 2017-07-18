package dao;

import constant.Constant;
import model.User;
import mysql_connection.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        return user;
    }
}
