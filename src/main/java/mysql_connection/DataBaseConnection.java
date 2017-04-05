package mysql_connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBaseConnection {
    static String url = "jdbc:mysql://localhost:3306/fisherman_hunter";
    static String user = "root";
    static String password = "root";

    public static Connection getConnection() throws ClassNotFoundException {
        String jdbcDriver = "com.mysql.jdbc.Driver";

        Class.forName(jdbcDriver);
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(url, user, password);
        }catch (SQLException sql){
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, sql);
        }
        return connection;
    }
}