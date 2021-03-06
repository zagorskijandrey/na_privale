/**
 * Created by AZagorskyi on 04.09.2017.
 */
package servlet.user_statistic;

import authentication.JwtUtil;
import constant.Constant;
import dao.fishing_page.FishingPageDao;
import dao.fishing_page.FishingPageDaoImpl;
import json_parser.JSONToObjectParserForFishingPage;
import json_parser.ObjectToJSONParserForFishingPage;
import model.FishingPage;
import org.json.simple.JSONObject;
import service.CalculatePredictionPeace;
import servlet.BaseHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/f_page")
public class FishingPageServlet extends HttpServlet {

    private BaseHandler handler = null;

    @Override
    public void init(){
        handler = new BaseHandler();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void doGet(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException, IOException {
        String username = JwtUtil.getSubject(httpRequest);
        if (username != null) {
            String fishing_page_id = httpRequest.getParameter("id");
            ObjectToJSONParserForFishingPage objectToJSON = new ObjectToJSONParserForFishingPage();
            JSONObject jsonObjectPage = objectToJSON.getJSONObjectFishingPage(Constant.SQL_QUERY_GET_FISHING_PAGE, Integer.parseInt(fishing_page_id));
            Date date = null;
            try {
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                date = format.parse( jsonObjectPage.get("date").toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            JSONObject jsonObjectPeaceMarks = new CalculatePredictionPeace().calculatePastPrediction(Integer.parseInt(jsonObjectPage.get("id").toString()), date);

            if (jsonObjectPage != null) {
                JSONObject object = new JSONObject();
                object.put("page", jsonObjectPage);
                object.put("peace_marks", jsonObjectPeaceMarks);
                handler.responseFactory(httpResponse, object, null);
            } else {
                String error = "Данная рыбалка не найдена!";
                httpResponse.setStatus(400);
                handler.responseFactory(httpResponse, null, error);
            }
        } else {
            String error = "Войдите в систему!";
            httpResponse.setStatus(401);
            handler.responseFactory(httpResponse, null, error);
        }
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