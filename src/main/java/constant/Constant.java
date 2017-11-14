package constant;

/**
 * Created by AZagorskyi on 06.04.2017.
 */
public class Constant {
    public static double FACTOR_PRESSURE = 1.333;
    public static String TOKEN_PREFIX = "Bearer ";
    public static String SIGNING_KEY = "user_api";

    public static String SQL_QUERY_SAVE_WEATHER = "INSERT INTO weather SET time=?, wind_speed=?, " +
            "wind_rout=?, pressure=?, id_region=(SELECT id_region FROM region WHERE name=?)";

    public static String SQL_QUERY_SAVE_MOON = "INSERT INTO moon SET moon_phase=?, time=?, distance=?";

    public static String SQL_QUERY_GET_FISHING_PAGE_LIST = "SELECT SQL_CALC_FOUND_ROWS * FROM fishing_page " +
            "WHERE id_user=(SELECT id_user FROM user WHERE username=?) AND region LIKE ? ORDER BY date desc LIMIT ?, ? ";
    public static String SQL_QUERY_GET_HAMLET_DESCRIPTION_LIST_BY_USER_AND_HAMLET_ID = "SELECT SQL_CALC_FOUND_ROWS comment, date " +
            "FROM fishing_page WHERE id_user=(SELECT id_user FROM user WHERE username=?) AND id_hamlet=?";
    public static String SQL_QUERY_SAVE_FISHING_PAGE = "INSERT INTO fishing_page SET province=?, region=?, " +
            "hamlet=?, comment=?, date=?, id_user=(SELECT id_user FROM user WHERE username=?), id_hamlet=?";

    public static String SQL_QUERY_SAVE_FISHES = "INSERT INTO fish SET name=?, weight=?, " +
            "distance=?, bait=?, time=?, id_page=(SELECT id_page FROM fishing_page WHERE id_page=?)";

    public static String SQL_QUERY_SELECT_WEATHER = "SELECT * FROM weather WHERE id_region=(SELECT id_region " +
            "FROM region WHERE name=?)";
    public static String SQL_QUERY_SELECT_WEATHER_ALL_REGIONS = "SELECT wind_speed, wind_rout, id_region, MAX(time) FROM weather GROUP BY id_region";
    //    public static String SQL_QUERY_SELECT_PRESSURES = "SELECT pressure FROM weather WHERE id_region=(SELECT id_region " +
//            "FROM region WHERE name=?) AND time BETWEEN ? AND ?";
    public static String SQL_QUERY_SELECT_PRESSURES = "SELECT pressure FROM weather WHERE id_region=? AND time BETWEEN ? AND ?";
    public static String SQL_QUERY_SELECT_PREDICTIONS = "SELECT * FROM region";
    public static String SQL_QUERY_SELECT_LAST_MOON = "SELECT * FROM moon ORDER BY id_moon DESC LIMIT 1";

//    public static String MOON_DATA_URL = "http://api.burningsoul.in/moon";
    public static String MOON_DATA_URL = "http://farmsense-prod.apigee.net/v1/moonphases/?d=";
    public static String WEATHER_DATA_TODAY_URL = "http://api.openweathermap.org/data/2.5/forecast?q=XXXXX,ua&APPID=bf64caf37de45d7b2e9751adc28f384a";

    public static String SQL_QUERY_GET_FISHING_STORY_BY_ID = "SELECT * FROM fishing_story where id_story=?";
    public static String SQL_QUERY_GET_FISHING_STORIES = "SELECT SQL_CALC_FOUND_ROWS * FROM fishing_story WHERE name LIKE ? LIMIT ?, ?";

    public static String SQL_QUERY_GET_HUNTER_STORY_BY_ID = "SELECT * FROM hunter_story where id_story=?";
    public static String SQL_QUERY_GET_HUNTER_STORIES = "SELECT SQL_CALC_FOUND_ROWS * FROM hunter_story WHERE name LIKE ? LIMIT ?, ?";

    public static String SQL_QUERY_GET_USER = "SELECT * FROM user WHERE username=? AND password=?";
    public static String SQL_QUERY_SAVE_USER = "INSERT INTO user SET  username=?, password=?, email=?, create_time=?, id_role=?";
    public static String SQL_QUERY_GET_USER_BY_EMAIL = "SELECT * FROM user WHERE create_time=(SELECT MAX(create_time) FROM user WHERE email=?)";
    public static String SQL_QUERY_GET_PAST_FISHING_LOCATION_BY_USER = "SELECT fishing_page.id_hamlet, fishing_page.id_user," +
            "hamlet.id_hamlet, hamlet.name, hamlet.lon, hamlet.lat FROM fishing_page, hamlet " +
            "WHERE fishing_page.id_hamlet=hamlet.id_hamlet AND id_user=(SELECT id_user FROM user WHERE username=?) ";

    public static String SQL_QUERY_GET_COUNTRIES = "SELECT * FROM country";
    public static String SQL_QUERY_GET_PROVINCES_BY_COUNTRY_ID = "SELECT * FROM province WHERE id_country=?";
    public static String SQL_QUERY_GET_REGIONS_BY_PROVINCE_ID = "SELECT * FROM reg WHERE id_province=?";
    public static String SQL_QUERY_GET_HAMLETS_BY_REGION_ID = "SELECT * FROM hamlet WHERE id_reg=?";
}
