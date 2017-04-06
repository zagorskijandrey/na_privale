package constant;

/**
 * Created by AZagorskyi on 06.04.2017.
 */
public class Constant {
    public static double FACTOR_PRESSURE = 1.333;

    public static String SQL_QUERY_UPDATE_TOMORROW_WEATHER = "UPDATE tomorrow_weather SET wind_speed=?, " +
            "wind_rout=?, pressure=? WHERE city=?";
    public static String SQL_QUERY_UPDATE_TODAY_WEATHER = "UPDATE today_weather SET wind_speed=?, " +
            "wind_rout=?, pressure=? WHERE city=?";
    public static String SQL_QUERY_UPDATE_ONCE_YESTERDAY_WEATHER = "UPDATE once_yesterday_weather SET wind_speed=?, " +
            "wind_rout=?, pressure=? WHERE city=?";
    public static String SQL_QUERY_UPDATE_TWICE_YESTERDAY_WEATHER = "UPDATE twice_yesterday_weather SET wind_speed=?, " +
            "wind_rout=?, pressure=? WHERE city=?";
    public static String SQL_QUERY_UPDATE_THIRD_YESTERDAY_WEATHER = "UPDATE third_yesterday_weather SET wind_speed=?, " +
            "wind_rout=?, pressure=? WHERE city=?";

    public static String SQL_QUERY_SELECT_TOMORROW_WEATHER = "SELECT * FROM tomorrow_weather WHERE city=?";
    public static String SQL_QUERY_SELECT_TODAY_WEATHER = "SELECT * FROM today_weather WHERE city=?";
    public static String SQL_QUERY_SELECT_ONCE_YESTERDAY_WEATHER = "SELECT * FROM once_yesterday_weather WHERE city=?";
    public static String SQL_QUERY_SELECT_TWICE_YESTERDAY_WEATHER = "SELECT * FROM twice_yesterday_weather WHERE city=?";
    public static String SQL_QUERY_SELECT_THIRD_YESTERDAY_WEATHER = "SELECT * FROM third_yesterday_weather WHERE city=?";

    public static String MOON_DATA_URL = "http://api.burningsoul.in/moon";
    public static String WEATHER_DATA_TODAY_URL = "http://api.openweathermap.org/data/2.5/forecast?q=XXXXX,ua&APPID=bf64caf37de45d7b2e9751adc28f384a";
}
