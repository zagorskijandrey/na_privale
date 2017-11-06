package servlet.user_statistic;

import authentication.JwtUtil;
import constant.Constant;
import dao.fishing_page.FishingPageDao;
import dao.fishing_page.FishingPageDaoImpl;
import json_parser.JSONToObjectParserForFishingPage;
import json_parser.ObjectToJSONParserForFishingPage;
import json_parser.ObjectToJSONParserForStory;
import model.FishingPage;
import org.json.simple.JSONObject;
import servlet.BaseHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by AZagorskyi on 25.10.2017.
 */
@WebServlet("/fishing")
public class FishingPageListServlet extends HttpServlet {
    private BaseHandler handler = null;

    @Override
    public void init(){
        handler = new BaseHandler();
    }

    @Override
    public void doGet(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException {
        String username = JwtUtil.getSubject(httpRequest);
        if (username != null){
            int start = Integer.parseInt(httpRequest.getParameter("start"));
            int total = Integer.parseInt(httpRequest.getParameter("total"));
            String filter = httpRequest.getParameter("filter");
            String sort = httpRequest.getParameter("sort");
            ObjectToJSONParserForFishingPage objectToJSON = new ObjectToJSONParserForFishingPage();
            JSONObject object = objectToJSON.getJSONObjectFishingPages(Constant.SQL_QUERY_GET_FISHING_PAGE_LIST, username, start, total, filter, sort);
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
