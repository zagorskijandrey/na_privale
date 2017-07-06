package servlet;

import constant.Constant;
import json_parser.ObjectToJSONParserForStory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by AZagorskyi on 18.04.2017.
 */
@WebServlet("/fishingStories")
public class FishingStoriesServlet extends HttpServlet{

    private BaseHandler handler = null;

    @Override
    public void init(){
        handler = new BaseHandler();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void doGet(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException{
        ObjectToJSONParserForStory objectToJSON = new ObjectToJSONParserForStory();
//        JSONArray jsonArray = objectToJSON.getJSONArrayStories(Constant.SQL_QUERY_GET_FISHING_STORIES);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", 1);
        jsonObject.put("name", "Рыбалка ночью");
        jsonObject.put("text", "Отлично клевало.");
        jsonArray.add(jsonObject);

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("id", 2);
        jsonObject1.put("name", "Рыбалка днем");
        jsonObject1.put("text", "Не клевало.");
        jsonArray.add(jsonObject1);

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("id", 2);
        jsonObject2.put("name", "Рыбалка на выходных");
        jsonObject2.put("text", "Много людей ходят вдоль берега и отпугивают рыбу.");
        jsonArray.add(jsonObject2);

        JSONObject object = null;
        handler.setDefaultHeader(httpResponse);
        if (jsonArray.size() > 0){
            object = new JSONObject();
            object.put("stories", jsonArray);
            handler.responseFactory(httpResponse, object, null);
        } else {
            String error = "Ошибка сервиса!";
            httpResponse.setStatus(400);
            handler.responseFactory(httpResponse, null, error);
        }
    }

    @Override
    public void doPost(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException{
        doGet(httpRequest, httpResponse);
    }
}
