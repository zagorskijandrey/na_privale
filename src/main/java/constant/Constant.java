package constant;

/**
 * Created by AZagorskyi on 06.04.2017.
 */
public class Constant {
    public static double FACTOR_PRESSURE = 1.333;

    public static String SQL_QUERY_UPDATE_YESTERDAY = "UPDATE yesterday_weather SET yesterday_wind_speed=?, " +
            "yesterday_wind_rout=?, yesterday_pressure=? WHERE city=?";
    public static String SQL_QUERY_UPDATE_BEFORE_YESTERDAY = "UPDATE before_yesterday_weather SET before_yesterday_wind_speed=?, " +
            "before_yesterday_wind_rout=?, before_yesterday_pressure=? WHERE city=?";
    public static String SQL_QUERY_SELECT_YESTERDAY_WEATHER = "SELECT * FROM yesterday_weather WHERE city=?";
    public static String SQL_QUERY_SELECT_BEFORE_YESTERDAY_WEATHER = "SELECT * FROM before_yesterday_weather WHERE city=?";
    public static String MOON_DATA_URL = "http://api.burningsoul.in/moon";
    public static String WEATHER_DATA_TODAY_URL = "http://api.openweathermap.org/data/2.5/forecast?q=XXXXX,ua&APPID=bf64caf37de45d7b2e9751adc28f384a";
}
