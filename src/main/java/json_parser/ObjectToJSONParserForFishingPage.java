package json_parser;

import dao.fishing_page.FishingPageDao;
import dao.fishing_page.FishingPageDaoImpl;
import model.FishingPage;
import model.Story;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

/**
 * Created by AZagorskyi on 25.10.2017.
 */
public class ObjectToJSONParserForFishingPage {
    @SuppressWarnings("unchecked")
    public JSONObject getJSONObjectFishingPage(String sqlQuery, String username, int page_id){
        FishingPageDao fishingPageDao = new FishingPageDaoImpl(sqlQuery);
        FishingPage fishingPage = fishingPageDao.getFishingPageById(username,page_id);
//        JSONObject jsonObject = convertObjectToJSON(fishingPage);
//        StoryDao storyData = new StoryDaoImpl(sqlQuery, page_id);
//        Story story = storyData.getStory();
//        jsonObject.put("id", story.getId());
//        jsonObject.put("name", story.getName());
//        jsonObject.put("text", story.getText());
//        jsonObject.put("author", story.getAuthor());
        return convertObjectToJSON(fishingPage);
    }

    @SuppressWarnings("unchecked")
    public JSONObject getJSONObjectFishingPages(String sqlQuery, String username, int start,int total, String filter, String sort){
        FishingPageDao fishingPageDao = new FishingPageDaoImpl(sqlQuery);
        List<FishingPage> fishingPageList = fishingPageDao.getFishingPageList(username,start, total, filter, sort);
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

    @SuppressWarnings("unchecked")
    private JSONObject convertObjectToJSON(FishingPage fishingPage){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", fishingPage.getId());
        jsonObject.put("province", fishingPage.getProvince());
        jsonObject.put("region", fishingPage.getRegion());
        jsonObject.put("hamlet", fishingPage.getHamlet());
        jsonObject.put("comment", fishingPage.getComment());
        jsonObject.put("date", fishingPage.getDate().toString());
        return jsonObject;
    }

    private String getSubstringText(FishingPage fishingPage){
        String text = fishingPage.getComment();
        if (text.length() > 500){
            text = text.substring(0, 500).concat("...");
        }
        return text;
    }
}
