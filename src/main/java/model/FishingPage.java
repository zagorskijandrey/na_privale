/**
 * Created by AZagorskyi on 04.09.2017.
 */
package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FishingPage {
    private int id;
    private String province;
    private String region;
    private String hamlet;
    private String comment;
    private List<Fish> fishes;
    private Date date;
    private int idHamlet;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getHamlet() {
        return hamlet;
    }

    public void setHamlet(String hamlet) {
        this.hamlet = hamlet;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Fish> getFishes() {
        return fishes;
    }

    public void setFishes(List<Fish> fishes) {
        this.fishes = fishes;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getIdHamlet() {
        return idHamlet;
    }

    public void setIdHamlet(int idHamlet) {
        this.idHamlet = idHamlet;
    }
}
