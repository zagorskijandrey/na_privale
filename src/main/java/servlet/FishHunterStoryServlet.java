package servlet;

import constant.Constant;
import json_parser.ObjectToJSONParserForStory;
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
@WebServlet("/fishHunterStory")
public class FishHunterStoryServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException, IOException {
        String story_id = httpRequest.getParameter("id");
        ObjectToJSONParserForStory objectToJSON = new ObjectToJSONParserForStory();
        JSONObject jsonObject = objectToJSON.getJSONStory(Constant.SQL_QUERY_GET_HUNTER_STORY_BY_ID, story_id);
        httpResponse.setContentType("application/json");
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.getWriter().write(jsonObject.toJSONString());
    }

    @Override
    public void doPost(HttpServletRequest httpRequest, HttpServletResponse httpResponse)throws ServletException, IOException{
        doGet(httpRequest, httpResponse);
    }
}
