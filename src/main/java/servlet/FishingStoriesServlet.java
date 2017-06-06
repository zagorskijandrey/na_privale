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
        JSONArray jsonArray = objectToJSON.getJSONArrayStories(Constant.SQL_QUERY_GET_FISHING_STORIES);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("FishingStories", jsonArray);
        httpResponse.setContentType("application/json");
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.getWriter().write(jsonArray.toJSONString());
    }

    @Override
    public void doPost(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException,IOException{
        doGet(httpRequest, httpResponse);
    }
}
