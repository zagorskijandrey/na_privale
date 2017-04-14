package json_parser;

import archiv.get.GetStoryData;
import model.Story;
import org.json.simple.JSONObject;

/**
 * Created by AZagorskyi on 14.04.2017.
 */
public class ObjectToJSONParserForStory {

    public JSONObject getJSONStory(String sqlQuery){
        JSONObject jsonObject = new JSONObject();
        GetStoryData storyData = new GetStoryData(sqlQuery);
        Story story = storyData.getStory();
        jsonObject.put("id", story.getId());
        jsonObject.put("name", story.getName());
        jsonObject.put("story", story.getText());
        jsonObject.put("imageName", story.getImageName());
        return jsonObject;
    }
}
