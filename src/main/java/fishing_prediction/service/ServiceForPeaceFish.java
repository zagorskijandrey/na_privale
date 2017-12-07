package fishing_prediction.service;

import fishing_prediction.AverageWaterTemperatureInMonth;
import fishing_prediction.MoonRelationPeaceFish;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by AZagorskyi on 22.03.2017.
 */
public class ServiceForPeaceFish {

    public Map<String, Integer> calculatePeacePrediction(int moonDay, int month, List<Integer> arrayAtmospherePressure, int windRout, int windSpeed){
        Map<String, Integer> map = new LinkedHashMap<>();
        int ratingForMoonDay = MoonRelationPeaceFish.getRatingByMoonDay(moonDay);
        map.put("moonMark", ratingForMoonDay);
        int temperature = AverageWaterTemperatureInMonth.getTemperatureByMonth(month);
        int temperaturePredictionValue = getTemperaturePredictionValue(temperature);
        map.put("temperatureMark", temperaturePredictionValue);
        int atmospherePressurePredictionValue = getAtmospherePressurePredictionValue(arrayAtmospherePressure);
        map.put("atmospherePressureMark", atmospherePressurePredictionValue);
        int windRoutValue = getWindRoutPredictionValue(windRout, temperature);
        map.put("windRoutMark", windRoutValue);
        int windSpeedValue = getWindSpeedValue(windSpeed);
        map.put("windSpeedMark", windSpeedValue);
        return map;

//        return (ratingForMoonDay + temperaturePredictionValue + atmospherePressurePredictionValue +
//                windRoutValue + windSpeedValue)/5;
    }

    private int getTemperaturePredictionValue(int temperature){
        int temperaturePredictionValue = 0;
        switch (temperature){
            case 3:
            case 4:
                temperaturePredictionValue = 1;
                break;
            case 5:
                temperaturePredictionValue = 2;
                break;
            case 9:
                temperaturePredictionValue = 3;
                break;
            case 16:
                temperaturePredictionValue = 5;
                break;
            case 18:
                temperaturePredictionValue = 9;
                break;
            case 20:
                temperaturePredictionValue = 10;
                break;
            case 22:
                temperaturePredictionValue = 8;
                break;
            case 23:
                temperaturePredictionValue = 7;
                break;
        }
        return temperaturePredictionValue;
    }

    private int getAtmospherePressurePredictionValue(List<Integer> arrayAtmospherePressure){
        int atmospherePressurePredictionValue = 0;
        int averageDifferencesAtmospherePressure = 0;
        for (int i = 1; i < arrayAtmospherePressure.size(); i++){
            averageDifferencesAtmospherePressure = + Math.abs(arrayAtmospherePressure.get(i - 1) - arrayAtmospherePressure.get(i));
        }
        averageDifferencesAtmospherePressure = averageDifferencesAtmospherePressure/arrayAtmospherePressure.size();

        if (averageDifferencesAtmospherePressure >= 0 && averageDifferencesAtmospherePressure <= 3)
            atmospherePressurePredictionValue = 10;
        if (averageDifferencesAtmospherePressure > 3 && averageDifferencesAtmospherePressure <= 5)
            atmospherePressurePredictionValue = 9;
        if (averageDifferencesAtmospherePressure > 5 && averageDifferencesAtmospherePressure <= 7)
            atmospherePressurePredictionValue = 8;
        if (averageDifferencesAtmospherePressure > 7 && averageDifferencesAtmospherePressure <= 9)
            atmospherePressurePredictionValue = 7;
        if (averageDifferencesAtmospherePressure > 9 && averageDifferencesAtmospherePressure <= 11)
            atmospherePressurePredictionValue = 5;
        if (averageDifferencesAtmospherePressure > 11 && averageDifferencesAtmospherePressure <= 15)
            atmospherePressurePredictionValue = 4;
        if (averageDifferencesAtmospherePressure > 15)
            atmospherePressurePredictionValue = 0;
        return atmospherePressurePredictionValue;
    }

    private int getWindRoutPredictionValue(int windRout, int temperatureWater){
        int windRoutValue = 2;
        if (windRout <= 22 || windRout > 338){
            if (temperatureWater >= 22)
                windRoutValue = 10;
        }
        if (windRout > 22 && windRout <= 112)
            windRoutValue = 0;
        if (windRout > 112 && windRout <= 158)
            windRoutValue = 5;
        if (windRout > 158 && windRout <= 248)
            windRoutValue = 8;
        if (windRout > 248 && windRout <= 292)
            windRoutValue = 10;
        if (windRout > 292 && windRout <= 315)
            windRoutValue = 6;
        return windRoutValue;
    }

    private int getWindSpeedValue(int windSpeed){
        int windSpeedValue = 1;
        if (windSpeed < 3)
            windSpeedValue = 3;
        if (windSpeed >= 3 && windSpeed < 5)
            windSpeedValue = 6;
        if (windSpeed >=5 && windSpeed < 10)
            windSpeedValue = 10;
        if (windSpeed >= 10 && windSpeed < 15)
            windSpeedValue = 6;
        if (windSpeed >= 15 && windSpeed < 20)
            windSpeedValue = 3;
        return windSpeedValue;
    }
}
