package fishing_prediction;

import java.util.HashMap;

/**
 * Created by AZagorskyi on 22.03.2017.
 */
public class MoonRelationPeaceFish {

    private static HashMap<Integer, Integer> moonDayRatingMap = null;

    static {
        moonDayRatingMap = new HashMap<Integer, Integer>();
        moonDayRatingMap.put(1, 0);
        moonDayRatingMap.put(2, 2);
        moonDayRatingMap.put(3, 6);
        moonDayRatingMap.put(4, 7);
        moonDayRatingMap.put(5, 8);
        moonDayRatingMap.put(6, 10);
        moonDayRatingMap.put(7, 9);
        moonDayRatingMap.put(8, 8);
        moonDayRatingMap.put(9, 7);
        moonDayRatingMap.put(10, 6);
        moonDayRatingMap.put(11, 4);
        moonDayRatingMap.put(12, 3);
        moonDayRatingMap.put(13, 2);
        moonDayRatingMap.put(14, 1);
        moonDayRatingMap.put(15, 1);
        moonDayRatingMap.put(16, 2);
        moonDayRatingMap.put(17, 4);
        moonDayRatingMap.put(18, 6);
        moonDayRatingMap.put(19, 9);
        moonDayRatingMap.put(20, 7);
        moonDayRatingMap.put(21, 7);
        moonDayRatingMap.put(22, 7);
        moonDayRatingMap.put(23, 6);
        moonDayRatingMap.put(24, 5);
        moonDayRatingMap.put(25, 4);
        moonDayRatingMap.put(26, 4);
        moonDayRatingMap.put(27, 3);
        moonDayRatingMap.put(28, 3);
        moonDayRatingMap.put(29, 2);
        moonDayRatingMap.put(30, 0);
    }

    public static int getRatingByMoonDay(int day){
        return moonDayRatingMap.get(day);
    }
}
