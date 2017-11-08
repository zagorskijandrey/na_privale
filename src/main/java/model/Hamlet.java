package model;

/**
 * Created by AZagorskyi on 06.11.2017.
 */
public class Hamlet {
    private int id;
    private String name;
    private double latitude;
    private double longitude;

    public Hamlet(){}

    public Hamlet(int id, String name, double latitude, double longitude){
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof Hamlet)) {
            return false;
        }

        Hamlet hamlet = (Hamlet) o;

        return hamlet.name.equals(name) &&
                hamlet.id == id;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + name.hashCode();
        result = 31 * result + id;
        return result;
    }
}
