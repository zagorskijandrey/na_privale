/**
 * Created by AZagorskyi on 06.11.2017.
 */
package json_parser;

import dao.location.LocationDao;
import dao.location.LocationDaoImpl;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Map;

public class ObjectToJSONParserForLocation {

    @SuppressWarnings("unchecked")
    public JSONObject getJSONObjectCountries(String sqlQuery){
        LocationDao locationData = new LocationDaoImpl(sqlQuery);
        Map<Integer, String> countryList = locationData.getCountries();
        JSONArray jsonArray = new JSONArray();
        for (Map.Entry<Integer, String> country: countryList.entrySet()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", country.getKey());
            jsonObject.put("name", country.getValue());
            jsonArray.add(jsonObject);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("country_list", jsonArray);
        return jsonObject;
    }

    @SuppressWarnings("unchecked")
    public JSONObject getJSONObjectProvinces(String sqlQuery, int id){
        LocationDao locationData = new LocationDaoImpl(sqlQuery);
        Map<Integer, String> provinceList = locationData.getProvincesByCountryId(id);
        JSONArray jsonArray = new JSONArray();
        for (Map.Entry<Integer, String> province: provinceList.entrySet()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", province.getKey());
            jsonObject.put("name", province.getValue());
            jsonArray.add(jsonObject);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("province_list", jsonArray);
        return jsonObject;
    }

    @SuppressWarnings("unchecked")
    public JSONObject getJSONObjectRegions(String sqlQuery, int id){
        LocationDao locationData = new LocationDaoImpl(sqlQuery);
        Map<Integer, String> regionList = locationData.getRegionsByProvinceId(id);
        JSONArray jsonArray = new JSONArray();
        for (Map.Entry<Integer, String> region: regionList.entrySet()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", region.getKey());
            jsonObject.put("name", region.getValue());
            jsonArray.add(jsonObject);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("region_list", jsonArray);
        return jsonObject;
    }

    @SuppressWarnings("unchecked")
    public JSONObject getJSONObjectHamlets(String sqlQuery, int id){
        LocationDao locationData = new LocationDaoImpl(sqlQuery);
        Map<Integer, String> hamletList = locationData.getHamletsByRegionId(id);
        JSONArray jsonArray = new JSONArray();
        for (Map.Entry<Integer, String> hamlet: hamletList.entrySet()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", hamlet.getKey());
            jsonObject.put("name", hamlet.getValue());
            jsonArray.add(jsonObject);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hamlet_list", jsonArray);
        return jsonObject;
    }
}
