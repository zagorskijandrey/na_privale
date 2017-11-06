package servlet.location;

import constant.Constant;
import json_parser.ObjectToJSONParserForLocation;
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
@WebServlet("/province")
public class ProvinceServlet extends HttpServlet {
    private BaseHandler handler = null;

    @Override
    public void init(){
        handler = new BaseHandler();
    }

    @Override
    public void doGet(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException {
        String country_id = httpRequest.getParameter("id");
        ObjectToJSONParserForLocation objectToJSON = new ObjectToJSONParserForLocation();
        JSONObject jsonObject = objectToJSON.getJSONObjectProvinces(Constant.SQL_QUERY_GET_PROVINCES_BY_COUNTRY_ID, Integer.parseInt(country_id));
        if (jsonObject != null){
            handler.responseFactory(httpResponse, jsonObject, null);
        } else {
            String error = "Данная область не найдена!";
            httpResponse.setStatus(400);
            handler.responseFactory(httpResponse, null, error);
        }
    }

    @Override
    public void doPost(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException{
        doGet(httpRequest, httpResponse);
    }
}
