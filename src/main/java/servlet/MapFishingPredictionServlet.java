package servlet;

import authentication.JwtUtil;
import service.CalculatePredictionPeace;
import enumeration.RegionEnum;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by AZagorskyi on 22.03.2017.
 */
@WebServlet("/p_map")
public class MapFishingPredictionServlet extends HttpServlet {
    private BaseHandler handler = null;

    @Override
    public void init(){
        handler = new BaseHandler();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException{

        JSONArray jsonArray = null;
        String username = JwtUtil.getSubject(request);
        if (username != null){
            jsonArray = new CalculatePredictionPeace().calculatePrediction();
            JSONObject object = null;
            if (jsonArray.size() > 0){
                object = new JSONObject();
                object.put("regions", jsonArray);
                handler.responseFactory(response, object, null);
            } else {
                String error = "Ошибка сервиса!";
                response.setStatus(500);
                handler.responseFactory(response, null, error);
            }
        } else {
            String error = "Войдите в систему!";
            response.setStatus(401);
            handler.responseFactory(response, null, error);
        }
    }

    @Override
    public void doPost(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException,IOException{
        doGet(httpRequest, httpResponse);
    }

    private RegionEnum getCity(String urlParameter){
        RegionEnum city = RegionEnum.Kiev;
        if (urlParameter.equals(RegionEnum.valueOf(urlParameter).toString())){
            city = RegionEnum.valueOf(urlParameter);
        }
        return city;
    }
}