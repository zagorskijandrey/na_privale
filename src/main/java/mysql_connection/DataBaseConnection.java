package mysql_connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBaseConnection {
    private static Logger log = Logger.getLogger(DataBaseConnection.class.getName());
    static String url = "jdbc:mysql://localhost:3306/fisherman_hunter";
    static String user = "root";
    static String password = "root";
    static Connection connection = null;

    public static Connection getConnection() throws ClassNotFoundException {
        String jdbcDriver = "com.mysql.jdbc.Driver";

        Class.forName(jdbcDriver);
        try{
            connection = DriverManager.getConnection(url, user, password);
        }catch (SQLException sql){
            log.info("Connection failed : " + sql);
        }
        return connection;
    }

    public static void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}