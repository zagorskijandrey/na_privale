package servlet.story;

import constant.Constant;
import json_parser.ObjectToJSONParserForStory;
import org.json.simple.JSONObject;
import servlet.BaseHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by andrey on 22.10.2017.
 */
@WebServlet("/hunterStory")
public class HunterStoryServlet extends HttpServlet {

    private BaseHandler handler = null;

    @Override
    public void init(){
        handler = new BaseHandler();
    }

    @Override
    public void doGet(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException {
        String story_id = httpRequest.getParameter("id");
        ObjectToJSONParserForStory objectToJSON = new ObjectToJSONParserForStory();
        JSONObject jsonObject = objectToJSON.getJSONStory(Constant.SQL_QUERY_GET_HUNTER_STORY_BY_ID, story_id);
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

    @Override
    public void doPost(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException{
        doGet(httpRequest, httpResponse);
    }
}