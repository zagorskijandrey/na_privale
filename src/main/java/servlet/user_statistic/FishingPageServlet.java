/**
 * Created by AZagorskyi on 04.09.2017.
 */
package servlet.user_statistic;

import authentication.JwtUtil;
import dao.fishing_page.FishingPageDao;
import dao.fishing_page.FishingPageDaoImpl;
import json_parser.JSONToObjectParserForFishingPage;
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

@WebServlet("/f_page")
public class FishingPageServlet extends HttpServlet {

    private BaseHandler handler = null;

    @Override
    public void init(){
        handler = new BaseHandler();
    }

    @Override
    public void doGet(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException {
    }

    @SuppressWarnings("unchecked")
    @Override
    public void doPost(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException, IOException {

        String username = JwtUtil.getSubject(httpRequest);
        if (username != null){
            JSONToObjectParserForFishingPage jsonToObjectParserForFishingPage = new JSONToObjectParserForFishingPage();
            FishingPage fishingPage = jsonToObjectParserForFishingPage.getFishingPageFromJSON(httpRequest);
            FishingPageDao fishingPageDao = new FishingPageDaoImpl();
            try {
                fishingPageDao.saveFishingPage(username, fishingPage);
            } catch (ClassNotFoundException e) {
                String error = "Ошибка сервиса! ClassNotFoundException";
                httpResponse.setStatus(500);
                handler.responseFactory(httpResponse, null, error);
            } catch (SQLException e) {
                String error = "Ошибка сервиса! SQLException";
                httpResponse.setStatus(500);
                handler.responseFactory(httpResponse, null, error);
            }
            JSONObject object = new JSONObject();
            object.put("body", "success");
            httpResponse.setStatus(200);
            handler.responseFactory(httpResponse, object, null);
        } else {
            String error = "Войдите в систему!";
            httpResponse.setStatus(401);
            handler.responseFactory(httpResponse, null, error);
        }
    }
}