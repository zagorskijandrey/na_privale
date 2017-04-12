package model;

/**
 * Created by andrey on 12.04.2017.
 */
public class DateModel {
    private int day = 0;
    private int month = 0;

    public DateModel(int day, int month){
        this.day = day;
        this.month = month;
    }

    public int getDay(){
        return day;
    }

    public int getMonth(){
        return month;
    }

    @Override
    public boolean equals(Object object){
        if (object == null)
            return false;
        if (!(object instanceof DateModel))
            return false;
        if (((DateModel) object).getDay() == this.day && ((DateModel) object).getMonth() == this.month)
            return true;
        else
            return false;
    }
}
