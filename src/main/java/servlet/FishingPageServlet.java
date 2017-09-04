/**
 * Created by AZagorskyi on 04.09.2017.
 */
package servlet;

import model.Fish;
import model.FishingPage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/f_page")
public class FishingPageServlet extends HttpServlet {

    private BaseHandler handler = null;

    @Override
    public void init(){
        handler = new BaseHandler();
    }

    @Override
    public void doGet(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException {
//        String story_id = httpRequest.getParameter("id");
//        ObjectToJSONParserForStory objectToJSON = new ObjectToJSONParserForStory();
//        JSONObject jsonObject = objectToJSON.getJSONStory(Constant.SQL_QUERY_GET_FISHING_STORY_BY_ID, story_id);
//        handler.setDefaultHeader(httpResponse);
//        if (jsonObject != null){
//            JSONObject object = new JSONObject();
//            object.put("story", jsonObject);
//            handler.responseFactory(httpResponse, object, null);
//        } else {
//            String error = "Данная статья не найдена!";
//            httpResponse.setStatus(400);
//            handler.responseFactory(httpResponse, null, error);
//        }
    }

    @Override
    public void doPost(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException, IOException {
        FishingPage fishingPage = this.getFishingPageFromJSON(httpRequest);

        JSONObject jsonObject = null;
        handler.setDefaultHeader(httpResponse);
        if (jsonObject != null){
            JSONObject object = new JSONObject();
            object.put("story", jsonObject);
            handler.responseFactory(httpResponse, object, null);
        } else {
            String error = "Данная статья не найдена!";
            httpResponse.setStatus(400);
            handler.responseFactory(httpResponse, null, error);
        }
    }

    private FishingPage getFishingPageFromJSON(HttpServletRequest httpRequest) throws ServletException, IOException {
        StringBuffer sb = new StringBuffer();

        BufferedReader reader = httpRequest.getReader();
        while(reader.ready()){
            sb.append(reader.readLine());
        }
        JSONParser parser = new JSONParser();
        JSONObject joFishingPage = null;
        FishingPage fishingPage = null;
        try {
            joFishingPage = (JSONObject) parser.parse(sb.toString());
            fishingPage = new FishingPage();
//            fishingPage.setProvince(joFishingPage.get("province") != null ? joFishingPage.get("province").toString() : "");
//            fishingPage.setRegion(joFishingPage.get("region") != null ? joFishingPage.get("region").toString() : "");
//            fishingPage.setHamlet(joFishingPage.get("hamlet") != null ? joFishingPage.get("hamlet").toString() : "");
//            fishingPage.setComment(joFishingPage.get("comment") != null ? joFishingPage.get("comment").toString() : "");
//            if (joFishingPage.get("fishes") != null) {
//                JSONArray jsonArray = (JSONArray) joFishingPage.get("fishes");
//                fishingPage.setFishes(this.getFishFromJSON(jsonArray));
//            }

            if (joFishingPage.get("province") != null)
                fishingPage.setProvince(joFishingPage.get("province") != null ? joFishingPage.get("province").toString() : "");
            if (joFishingPage.get("region") != null)
                fishingPage.setRegion(joFishingPage.get("region").toString());
            if (joFishingPage.get("hamlet") != null)
                fishingPage.setHamlet(joFishingPage.get("hamlet").toString());
            if (joFishingPage.get("comment") != null)
                fishingPage.setComment(joFishingPage.get("comment").toString());
            if (joFishingPage.get("fishes") != null){
                JSONArray jsonArray = (JSONArray) joFishingPage.get("fishes");
                fishingPage.setFishes(this.getFishFromJSON(jsonArray));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (NullPointerException ne) {
            ne.printStackTrace();
        }
        return fishingPage;
    }

    private List<Fish> getFishFromJSON(JSONArray jsonArray){
        List<Fish> fishes = new ArrayList<Fish>();
        if (jsonArray.size() > 0){
            for (int i=0; i<jsonArray.size(); i++){
                Fish fish = new Fish();
                JSONObject jsonObjectArray = (JSONObject) jsonArray.get(i);
//                fish.setName(jsonObjectArray.get("name") != null? jsonObjectArray.get("name").toString() : "");
//                fish.setWeight(Float.parseFloat(jsonObjectArray.get("weight") != null? jsonObjectArray.get("weight").toString() : "0"));
//                fish.setDistance(Float.parseFloat(jsonObjectArray.get("distance") != null? jsonObjectArray.get("distance").toString() : "0"));
//                fish.setBait(jsonObjectArray.get("bait") != null? jsonObjectArray.get("bait").toString() : "");
//                fish.setTime(jsonObjectArray.get("time") != null? jsonObjectArray.get("time").toString() : "");

                if (jsonObjectArray.get("name") != null)
                    fish.setName(jsonObjectArray.get("name").toString());
                if (jsonObjectArray.get("weight") != null)
                    fish.setWeight(Float.parseFloat(jsonObjectArray.get("weight").toString()));
                if (jsonObjectArray.get("distance") != null)
                    fish.setDistance(Float.parseFloat(jsonObjectArray.get("distance").toString()));
                if (jsonObjectArray.get("bait") != null)
                    fish.setBait(jsonObjectArray.get("bait").toString());
                if (jsonObjectArray.get("time") != null)
                    fish.setTime(jsonObjectArray.get("time").toString());
                fishes.add(fish);
            }
        }
        return fishes;
    }
}