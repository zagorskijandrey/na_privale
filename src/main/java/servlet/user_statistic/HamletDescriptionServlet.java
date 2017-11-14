package servlet.user_statistic;

import authentication.JwtUtil;
import constant.Constant;
import json_parser.ObjectToJSONParserForFishingPage;
import org.json.simple.JSONObject;
import servlet.BaseHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by AZagorskyi on 10.11.2017.
 */
@WebServlet("/hamlet_description")
public class HamletDescriptionServlet extends HttpServlet {
    private BaseHandler handler = null;

    @Override
    public void init(){
        handler = new BaseHandler();
    }

    @Override
    public void doGet(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException {
        String hamlet_id = httpRequest.getParameter("id");
        String username = JwtUtil.getSubject(httpRequest);
        if (username != null){
            ObjectToJSONParserForFishingPage objectToJSON = new ObjectToJSONParserForFishingPage();
            JSONObject object = objectToJSON.getJSONObjectHamletsDescription(
                    Constant.SQL_QUERY_GET_HAMLET_DESCRIPTION_LIST_BY_USER_AND_HAMLET_ID,
                    username, Integer.parseInt(hamlet_id));
            if (object != null){
                handler.responseFactory(httpResponse, object, null);
            } else {
                String error = "Ошибка сервиса!";
                httpResponse.setStatus(400);
                handler.responseFactory(httpResponse, null, error);
            }
        } else {
            String error = "Войдите в систему!";
            httpResponse.setStatus(401);
            handler.responseFactory(httpResponse, null, error);
        }
    }

    @Override
    public void doPost(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException, IOException {
        doGet(httpRequest, httpResponse);
    }
}
