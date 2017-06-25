package constant;

import java.util.HashMap;
import java.util.LinkedHashMap;

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

    public static LinkedHashMap <String, String> MAP_QUERY_REFRESH_DATA_BASE = null;

    static{
        MAP_QUERY_REFRESH_DATA_BASE = new LinkedHashMap<String, String>();
        MAP_QUERY_REFRESH_DATA_BASE.put(SQL_QUERY_SELECT_TWICE_YESTERDAY_WEATHER, SQL_QUERY_UPDATE_THIRD_YESTERDAY_WEATHER);
        MAP_QUERY_REFRESH_DATA_BASE.put(SQL_QUERY_SELECT_ONCE_YESTERDAY_WEATHER, SQL_QUERY_UPDATE_TWICE_YESTERDAY_WEATHER);
        MAP_QUERY_REFRESH_DATA_BASE.put(SQL_QUERY_SELECT_TODAY_WEATHER, SQL_QUERY_UPDATE_ONCE_YESTERDAY_WEATHER);
    }

    public static String SQL_QUERY_GET_FISHING_STORY_BY_ID = "SELECT * FROM fishing_story where id_fishing_story=?";
    public static String SQL_QUERY_GET_FISHING_STORIES = "SELECT * FROM fishing_story";

    public static String SQL_QUERY_GET_FISH_HUNTER_STORY_BY_ID = "SELECT * FROM fish_hunter_story where id_fish_hunter_story=?";
    public static String SQL_QUERY_GET_FISH_HUNTER_STORIES = "SELECT * FROM fish_hunter_story";

    public static String SQL_QUERY_GET_USER = "SELECT * FROM user where name=? AND password=?";
}
