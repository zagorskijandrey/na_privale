package fishing_prediction.service;

import fishing_prediction.service.MoonRelationPeaceFish;

/**
 * Created by AZagorskyi on 22.03.2017.
 */
public class Service {

    public int calculatePeacePrediction(int moonDay, int month, int [] arrayAtmospherePressure){
        int predictionValue = 0;
        int ratingForMoonDay = MoonRelationPeaceFish.getRatingByMoonDay(moonDay);
        int temperature = AverageWaterTemperatureInMonth.getTemperatureByMonth(month);
        int temperaturePredictionValue = getTemperaturePredictionValue(temperature);
        int atmospherePressurePredictionValue = getAtmospherePressurePredictionValue(arrayAtmospherePressure);
        predictionValue = (ratingForMoonDay + temperaturePredictionValue + atmospherePressurePredictionValue)/3;
        return predictionValue;
    }

    private int getTemperaturePredictionValue(int temperature){
        int temperaturePredictionValue = 0;
        switch (temperature){
            case 3:
            case 4:
                temperaturePredictionValue = 3;
                break;
            case 5:
                temperaturePredictionValue = 4;
                break;
            case 9:
                temperaturePredictionValue = 5;
                break;
            case 16:
                temperaturePredictionValue = 6;
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

    private int getAtmospherePressurePredictionValue(int [] arrayAtmospherePressure){
        int atmospherePressurePredictionValue = 0;
        int averageDifferencesAtmospherePressure = 0;
        for (int i = 0; i < arrayAtmospherePressure.length; i++){
            averageDifferencesAtmospherePressure = + Math.abs(arrayAtmospherePressure[i] - arrayAtmospherePressure[i + 1]);
        }
        averageDifferencesAtmospherePressure = averageDifferencesAtmospherePressure/arrayAtmospherePressure.length;

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
}
