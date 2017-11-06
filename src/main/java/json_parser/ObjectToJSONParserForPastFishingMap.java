package json_parser;

import dao.fishing_page.FishingPageDao;
import dao.fishing_page.FishingPageDaoImpl;
import dao.user.UserDao;
import dao.user.UserDaoImpl;
import model.FishingPage;
import model.Hamlet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by AZagorskyi on 06.11.2017.
 */
public class ObjectToJSONParserForPastFishingMap {

    @SuppressWarnings("unchecked")
    public JSONObject getJSONObjectFishingPages(String sqlQuery, String username){
        UserDao userDao = new UserDaoImpl();
        Map<Integer, Hamlet> fishingPageList = userDao.getPastFishingLocationByUser().getFishingPageList(username);
        JSONArray jsonArray = new JSONArray();
        for (FishingPage fishingPage: fishingPageList){
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("id", fishingPage.getId());
//            jsonObject.put("province", fishingPage.getProvince());
//            jsonObject.put("region", fishingPage.getRegion());
//            jsonObject.put("hamlet", fishingPage.getHamlet());
//            jsonObject.put("comment", fishingPage.getComment());
//            jsonObject.put("date", fishingPage.getDate());
            jsonArray.add(convertObjectToJSON(fishingPage));
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("fishing_page_list", jsonArray);
        jsonObject.put("count_fishing_pages", fishingPageDao.getCountFishingPages());
        return jsonObject;
    }
}
