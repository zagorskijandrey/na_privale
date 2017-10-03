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
 * Created by andrey on 24.06.2017.
 */
@WebServlet("/h_stories")
public class HunterStoriesServlet extends HttpServlet{
    private BaseHandler handler = null;

    @Override
    public void init(){
        handler = new BaseHandler();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void doGet(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException, IOException {
        int start = Integer.parseInt(httpRequest.getParameter("start"));
        int total = Integer.parseInt(httpRequest.getParameter("total"));
        ObjectToJSONParserForStory objectToJSON = new ObjectToJSONParserForStory();
        JSONObject object = objectToJSON.getJSONObjectStories(Constant.SQL_QUERY_GET_HUNTER_STORIES, start, total);
//        JSONArray jsonArray = objectToJSON.getJSONArrayStories(Constant.SQL_QUERY_GET_HUNTER_STORIES, 0, 2);

//        JSONObject object = null;
        handler.setDefaultHeader(httpResponse);
        if (object != null){
//            object = new JSONObject();
//            object.put("stories", jsonArray);
            handler.responseFactory(httpResponse, object, null);
        } else {
            String error = "Ошибка сервиса!";
            httpResponse.setStatus(400);
            handler.responseFactory(httpResponse, null, error);
        }
    }

    @Override
    public void doPost(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException,IOException{
        doGet(httpRequest, httpResponse);
    }
}
