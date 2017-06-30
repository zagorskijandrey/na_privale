package servlet;

import org.json.simple.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by AZagorskyi on 30.06.2017.
 */
 class BaseHandler {

    void setDefaultHeader(HttpServletResponse httpResponse){
        httpResponse.setContentType("application/json");
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
        httpResponse.setHeader("Access-Control-Allow-Headers, Origin, X-Auth-Token, cache-control, Content-Type, Access-Control-Allow-Headers, Access-Control-Allow-Credentials, Access-Control-Allow-Methods, Authorization", "X-Requested-With");
    }

    @SuppressWarnings("unchecked")
    void responseFactory(HttpServletResponse httpResponse, JSONObject body, String errorMessage){
        JSONObject jsonResponse = new JSONObject();
        String status = "OK";
        if (httpResponse.getStatus() >= 400){
            status = "ERROR";
        }
        jsonResponse.put("status", status);
        jsonResponse.put("body", body);
        jsonResponse.put("status_code", httpResponse.getStatus());
        jsonResponse.put("error_message", getErrorMessage(errorMessage));

        httpResponse.setStatus(200);
        try {
            httpResponse.getWriter().write(jsonResponse.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getErrorMessage(String errorMessage){
        if (errorMessage != null){
            return errorMessage;
        } else {
            return null;
        }
    }
}
