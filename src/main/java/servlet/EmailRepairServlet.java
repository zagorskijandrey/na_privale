package servlet;

import dao.user.UserDao;
import dao.user.UserDaoImpl;
import mail_dispatch.EmailDispatcher;
import model.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by AZagorskyi on 13.09.2017.
 */
@WebServlet("/email_repair")
public class EmailRepairServlet extends HttpServlet {
    private BaseHandler handler = null;
    private boolean isError = true;

    @Override
    public void init(){
        handler = new BaseHandler();
    }

    @Override
    public void doGet(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException, ServletException {
    }

    @SuppressWarnings("unchecked")
    @Override
    public void doPost(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException, ServletException {
        StringBuffer sb = new StringBuffer();

        BufferedReader reader = httpRequest.getReader();
        while(reader.ready()){
            sb.append(reader.readLine());
        }
        JSONParser parser = new JSONParser();
        JSONObject joUser = null;
        String email = null;
        try {
            joUser = (JSONObject) parser.parse(sb.toString());
            email = joUser.get("email").toString();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (NullPointerException ne) {
            ne.printStackTrace();
        }

        UserDao userDao = new UserDaoImpl();
        User user = null;
        try {
            user = userDao.getUserByEmail(email);
            if (user != null){
                EmailDispatcher emailDispatcher = new EmailDispatcher();
                isError = emailDispatcher.sendEmail("zagorskij.andrey@gmail.com", email, "127.0.0.1", user);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        handler.setDefaultHeader(httpResponse);
        if (isError){
            JSONObject object = new JSONObject();
            object.put("email", email);
            handler.responseFactory(httpResponse, object, null);
        } else {
            String error = "Пользователя с таким email не существует! Попытайтесь повторить.";
            httpResponse.setStatus(401);
            handler.responseFactory(httpResponse, null, error);
        }
    }
}
