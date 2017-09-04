package dao;

import model.FishingPage;

/**
 * Created by AZagorskyi on 04.09.2017.
 */
public interface FishingPageDao {
    void saveFishingPage(String username, FishingPage fishingPage);
}
