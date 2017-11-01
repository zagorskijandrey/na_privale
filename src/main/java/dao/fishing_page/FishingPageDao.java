package dao.fishing_page;

import model.FishingPage;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by AZagorskyi on 04.09.2017.
 */
public interface FishingPageDao {
    void saveFishingPage(String username, FishingPage fishingPage) throws ClassNotFoundException, SQLException;
    List<FishingPage> getFishingPageList(String username, int start, int total, String filter, String sort);
    FishingPage getFishingPageById(String username, int id);
    int getCountFishingPages();
}
