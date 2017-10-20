package dao.prediction;

import model.Region;

import java.util.ArrayList;

/**
 * Created by AZagorskyi on 20.10.2017.
 */
public interface PredictionDataDao {
    ArrayList<Region> getPredictionForRegions();
}
