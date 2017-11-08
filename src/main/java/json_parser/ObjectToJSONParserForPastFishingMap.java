package json_parser;

import dao.user.UserDao;
import dao.user.UserDaoImpl;
import model.Hamlet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Map;

/**
 * Created by AZagorskyi on 06.11.2017.
 */
public class ObjectToJSONParserForPastFishingMap {

    @SuppressWarnings("unchecked")
    public JSONObject getJSONObjectPastFishingLocation(String username){
        UserDao userDao = new UserDaoImpl();
        Map<Hamlet, Integer> hamletMap = userDao.getPastFishingLocationByUser(username);
        JSONArray jsonArray = new JSONArray();
        for (Map.Entry<Hamlet, Integer> hamlet: hamletMap.entrySet()){
            jsonArray.add(convertObjectToJSON(hamlet));
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hamlet_data_list", jsonArray);
        return jsonObject;
    }

    @SuppressWarnings("unchecked")
    private JSONObject convertObjectToJSON(Map.Entry<Hamlet, Integer> hamlet){
        JSONObject jsonObjectHamlet = new JSONObject();
        jsonObjectHamlet.put("id", hamlet.getKey().getId());
        jsonObjectHamlet.put("name", hamlet.getKey().getName());
        jsonObjectHamlet.put("lon", hamlet.getKey().getLongitude());
        jsonObjectHamlet.put("lat", hamlet.getKey().getLatitude());
        jsonObjectHamlet.put("amount", hamlet.getValue());
        return jsonObjectHamlet;
    }
}
