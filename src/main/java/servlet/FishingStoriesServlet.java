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
    @Override
    public void doGet(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException, IOException {
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

        JSONObject object = new JSONObject();
        object.put("body", jsonArray);
        httpResponse.setContentType("application/json");
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
        httpResponse.setHeader("Access-Control-Allow-Headers, Origin, X-Auth-Token, cache-control, Content-Type, Access-Control-Allow-Headers, Access-Control-Allow-Credentials, Access-Control-Allow-Methods, Authorization", "X-Requested-With");
        httpResponse.getWriter().write(jsonArray.toJSONString());
    }

    @Override
    public void doPost(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException,IOException{
        doGet(httpRequest, httpResponse);
    }
}
