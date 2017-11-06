package servlet.location;

import constant.Constant;
import json_parser.ObjectToJSONParserForLocation;
import json_parser.ObjectToJSONParserForStory;
import org.json.simple.JSONObject;
import servlet.BaseHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by AZagorskyi on 06.11.2017.
 */
@WebServlet("/country")
public class CountryServlet extends HttpServlet{
    private BaseHandler handler = null;

    @Override
    public void init(){
        handler = new BaseHandler();
    }

    @Override
    public void doGet(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException {
        ObjectToJSONParserForLocation objectToJSON = new ObjectToJSONParserForLocation();
        JSONObject jsonObject = objectToJSON.getJSONObjectCountries(Constant.SQL_QUERY_GET_COUNTRIES);
        if (jsonObject != null){
            handler.responseFactory(httpResponse, jsonObject, null);
        } else {
            String error = "Данная страна не найдена!";
            httpResponse.setStatus(400);
            handler.responseFactory(httpResponse, null, error);
        }
    }

    @Override
    public void doPost(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException{
        doGet(httpRequest, httpResponse);
    }
}
