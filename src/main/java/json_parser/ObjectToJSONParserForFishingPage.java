package json_parser;

import dao.fishing_page.FishingPageDao;
import dao.fishing_page.FishingPageDaoImpl;
import model.Fish;
import model.FishingPage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by AZagorskyi on 25.10.2017.
 */
public class ObjectToJSONParserForFishingPage {
    @SuppressWarnings("unchecked")
    public JSONObject getJSONObjectFishingPage(String sqlQuery, int page_id){
        FishingPageDao fishingPageDao = new FishingPageDaoImpl(sqlQuery);
        FishingPage fishingPage = fishingPageDao.getFishingPageById(page_id);
        return convertObjectToJSON(fishingPage);
    }

    @SuppressWarnings("unchecked")
    public JSONObject getJSONObjectFishingPages(String sqlQuery, String username, int start,int total, String filter, String sort){
        FishingPageDao fishingPageDao = new FishingPageDaoImpl(sqlQuery);
        List<FishingPage> fishingPageList = fishingPageDao.getFishingPageList(username,start, total, filter, sort);
        JSONArray jsonArray = new JSONArray();
        for (FishingPage fishingPage: fishingPageList){
            jsonArray.add(convertObjectToJSON(fishingPage));
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("fishing_page_list", jsonArray);
        jsonObject.put("count_fishing_pages", fishingPageDao.getCountFishingPages());
        return jsonObject;
    }

    @SuppressWarnings("unchecked")
    public JSONObject getJSONObjectHamletsDescription(String sqlQuery, String username, int idHamlet){
        FishingPageDao fishingPageDao = new FishingPageDaoImpl(sqlQuery);
        Map<Date, String> hamletsDescription = fishingPageDao.getHamletsDescription(username, idHamlet);
        JSONArray jsonArray = new JSONArray();
        for (Map.Entry<Date, String> hamlet: hamletsDescription.entrySet()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("date", hamlet.getKey().toString());
            jsonObject.put("comment", hamlet.getValue());
            jsonArray.add(jsonObject);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hamlets_description", jsonArray);
        return jsonObject;
    }

    @SuppressWarnings("unchecked")
    private JSONObject convertObjectToJSON(FishingPage fishingPage){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", fishingPage.getId());
        jsonObject.put("province", fishingPage.getProvince());
        jsonObject.put("region", fishingPage.getRegion());
        jsonObject.put("hamlet", fishingPage.getHamlet());
        jsonObject.put("comment", fishingPage.getComment());
        jsonObject.put("date", fishingPage.getDate().toString());
        jsonObject.put("fishes", convertArrayToJSON(fishingPage.getFishes()));
        jsonObject.put("id_province", fishingPage.getIdProvince());
        return jsonObject;
    }

    @SuppressWarnings("unchecked")
    private JSONArray convertArrayToJSON(List<Fish> fishes){
        JSONArray jsonArray = new JSONArray();
        for(Fish fish : fishes){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", fish.getId());
            jsonObject.put("name", fish.getName());
            jsonObject.put("weight", fish.getWeight());
            jsonObject.put("distance", fish.getDistance());
            jsonObject.put("bait", fish.getBait());
            jsonObject.put("time", fish.getTime());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    private String getSubstringText(FishingPage fishingPage){
        String text = fishingPage.getComment();
        if (text.length() > 500){
            text = text.substring(0, 500).concat("...");
        }
        return text;
    }
}
