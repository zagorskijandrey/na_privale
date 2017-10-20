package dao.fishing_page;

import model.FishingPage;

import java.sql.SQLException;

/**
 * Created by AZagorskyi on 04.09.2017.
 */
public interface FishingPageDao {
    void saveFishingPage(String username, FishingPage fishingPage) throws ClassNotFoundException, SQLException;
}
