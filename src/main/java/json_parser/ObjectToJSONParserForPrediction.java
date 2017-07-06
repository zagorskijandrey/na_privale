package json_parser;

import model.Region;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

/**
 * Created by AZagorskyi on 04.07.2017.
 */
public class ObjectToJSONParserForPrediction {

    @SuppressWarnings("unchecked")
    public JSONArray getJSONArrayRegions(ArrayList<Region> regions){
        JSONArray jsonArray = new JSONArray();
        for (Region region: regions){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", region.getId());
            jsonObject.put("name", region.getName());
            jsonObject.put("latitude", region.getLatitude());
            jsonObject.put("longitude", region.getLongitude());
            jsonObject.put("prediction", region.getPrediction());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
}
