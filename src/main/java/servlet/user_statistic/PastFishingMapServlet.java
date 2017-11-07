package servlet.user_statistic;

import authentication.JwtUtil;
import json_parser.ObjectToJSONParserForPastFishingMap;
import org.json.simple.JSONObject;
import servlet.BaseHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by AZagorskyi on 06.11.2017.
 */
@WebServlet("/past_fishing_map")
public class PastFishingMapServlet extends HttpServlet {
    private BaseHandler handler = null;

    @Override
    public void init(){
        handler = new BaseHandler();
    }

    @Override
    public void doGet(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException {
        String username = JwtUtil.getSubject(httpRequest);
        if (username != null){
            ObjectToJSONParserForPastFishingMap objectToJSON = new ObjectToJSONParserForPastFishingMap();
            JSONObject object = objectToJSON.getJSONObjectPastFishingLocation(username);
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
