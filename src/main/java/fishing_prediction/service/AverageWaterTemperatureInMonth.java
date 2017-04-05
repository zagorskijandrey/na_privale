package fishing_prediction.service;

import java.util.HashMap;

/**
 * Created by AZagorskyi on 22.03.2017.
 */
public class AverageWaterTemperatureInMonth {

    private static HashMap<Integer, Integer> monthTemperatureMap = null;

    static{
        monthTemperatureMap = new HashMap<Integer, Integer>();
        monthTemperatureMap.put(0, 3);
        monthTemperatureMap.put(1, 3);
        monthTemperatureMap.put(2, 5);
        monthTemperatureMap.put(3, 9);
        monthTemperatureMap.put(4, 16);
        monthTemperatureMap.put(5, 20);
        monthTemperatureMap.put(6, 22);
        monthTemperatureMap.put(7, 23);
        monthTemperatureMap.put(8, 18);
        monthTemperatureMap.put(9, 9);
        monthTemperatureMap.put(10, 4);
        monthTemperatureMap.put(11, 3);
    }

    public static int getTemperatureByMonth(int month){
        return monthTemperatureMap.get(month);
    }
}
