package json_parser;

import archiv.get.GetStoryData;
import model.Story;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

/**
 * Created by AZagorskyi on 14.04.2017.
 */
public class ObjectToJSONParserForStory {

    public JSONObject getJSONStory(String sqlQuery, String story_id){
        JSONObject jsonObject = new JSONObject();
        GetStoryData storyData = new GetStoryData(sqlQuery, story_id);
        Story story = storyData.getStory();
        jsonObject.put("id", story.getId());
        jsonObject.put("name", story.getName());
        jsonObject.put("story", story.getText());
//        jsonObject.put("imageName", story.getImageName());
        return jsonObject;
    }

    public JSONArray getJSONArrayStories(String sqlQuery){
        GetStoryData storyData = new GetStoryData(sqlQuery);
        ArrayList<Story> storiesList = storyData.getStoriesList();
        JSONArray jsonArray = new JSONArray();
        for (Story story: storiesList){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", story.getId());
            jsonObject.put("name", story.getName());
            jsonObject.put("story", story.getText().substring(0, 500).concat("..."));
//            jsonObject.put("imageName", story.getImageName());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
}
