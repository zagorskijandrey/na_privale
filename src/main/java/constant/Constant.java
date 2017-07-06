package constant;

import java.util.LinkedHashMap;

/**
 * Created by AZagorskyi on 06.04.2017.
 */
public class Constant {
    public static double FACTOR_PRESSURE = 1.333;

    public static String SQL_QUERY_SAVE_WEATHER = "INSERT INTO weather SET time=?, wind_speed=?, " +
            "wind_rout=?, pressure=?, id_region=(SELECT id_region FROM region WHERE name=?)";

    public static String SQL_QUERY_SELECT_WEATHER = "SELECT * FROM weather WHERE id_region=(SELECT id_region " +
            "FROM region WHERE name=?)";
    public static String SQL_QUERY_SELECT_WEATHER_ALL_REGIONS = "SELECT wind_speed, wind_rout, id_region, MAX(time) FROM weather GROUP BY id_region";
//    public static String SQL_QUERY_SELECT_PRESSURES = "SELECT pressure FROM weather WHERE id_region=(SELECT id_region " +
//            "FROM region WHERE name=?) AND time BETWEEN ? AND ?";
    public static String SQL_QUERY_SELECT_PRESSURES = "SELECT pressure FROM weather WHERE id_region=? AND time BETWEEN ? AND ?";
    public static String SQL_QUERY_SELECT_PREDICTIONS = "SELECT * FROM region";

    public static String MOON_DATA_URL = "http://api.burningsoul.in/moon";
    public static String WEATHER_DATA_TODAY_URL = "http://api.openweathermap.org/data/2.5/forecast?q=XXXXX,ua&APPID=bf64caf37de45d7b2e9751adc28f384a";

    public static String SQL_QUERY_GET_FISHING_STORY_BY_ID = "SELECT * FROM fishing_story where id_fishing_story=?";
    public static String SQL_QUERY_GET_FISHING_STORIES = "SELECT * FROM fishing_story";

    public static String SQL_QUERY_GET_FISH_HUNTER_STORY_BY_ID = "SELECT * FROM fish_hunter_story where id_fish_hunter_story=?";
    public static String SQL_QUERY_GET_FISH_HUNTER_STORIES = "SELECT * FROM fish_hunter_story";

    public static String SQL_QUERY_GET_USER = "SELECT * FROM user where name=? AND password=?";
}
