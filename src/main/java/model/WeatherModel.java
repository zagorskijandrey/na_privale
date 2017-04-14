package model;

/**
 * Created by AZagorskyi on 23.03.2017.
 */
public class WeatherModel {
    private long timeInSeconds;
    private int windSpeed;
    private int windRout;
    private int pressure;

    public long getTimeInSeconds(){
        return this.timeInSeconds;
    }
    public void setTimeInSeconds(long timeInSeconds){
        this.timeInSeconds = timeInSeconds;
    }

    public int getWindSpeed(){
        return this.windSpeed;
    }
    public void setWindSpeed(int windSpeed){
        this.windSpeed = windSpeed;
    }

    public int getWindRout(){
        return this.windRout;
    }
    public void setWindRout(int windRout){
        this.windRout = windRout;
    }

    public int getPressure(){
        return this.pressure;
    }
    public void setPressure(int pressure){
        this.pressure = pressure;
    }

    @Override
    public String toString(){
        return "Weather: time seconds - " + getTimeInSeconds() + ", wind speed - " + getWindSpeed() + ", wind rout - "
                + getWindRout() + ", pressure - " + getPressure();
    }
}
