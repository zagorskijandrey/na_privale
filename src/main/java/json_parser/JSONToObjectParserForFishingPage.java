package json_parser;

import model.Fish;
import model.FishingPage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by AZagorskyi on 05.09.2017.
 */
public class JSONToObjectParserForFishingPage {

    public FishingPage getFishingPageFromJSON(HttpServletRequest httpRequest) throws ServletException, IOException {
        StringBuilder stringBuffer = new StringBuilder();

        BufferedReader reader = httpRequest.getReader();
        while(reader.ready()){
            stringBuffer.append(reader.readLine());
        }
        JSONParser parser = new JSONParser();
        JSONObject joFishingPage = null;
        FishingPage fishingPage = null;
        try {
            joFishingPage = (JSONObject) parser.parse(stringBuffer.toString());
            fishingPage = new FishingPage();
            if (joFishingPage.get("province") != null)
                fishingPage.setProvince(joFishingPage.get("province").toString());
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
            if (joFishingPage.get("date") != null){
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
                String date = joFishingPage.get("date").toString();
                fishingPage.setDate(dateFormat.parse(date));
            }
            if (joFishingPage.get("id_hamlet") != null){
                fishingPage.setIdHamlet(Integer.parseInt(joFishingPage.get("id_hamlet").toString()));
            }
            if (joFishingPage.get("id_province") != null){
                fishingPage.setIdProvince(Integer.parseInt(joFishingPage.get("id_province").toString()));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (NullPointerException ne) {
            ne.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return fishingPage;
    }

    private List<Fish> getFishFromJSON(JSONArray jsonArray){
        List<Fish> fishes = new ArrayList<Fish>();
        if (jsonArray.size() > 0){
            for(int i=0; i<jsonArray.size(); i++){
                Fish fish = new Fish();
                JSONObject jsonObjectArray = (JSONObject) jsonArray.get(i);
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
