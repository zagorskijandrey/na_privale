package servlet;

import authentication.JwtUtil;
import constant.Constant;
import dao.UserDao;
import dao.UserDaoImpl;
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
 * Created by andrey on 25.06.2017.
 */
@WebServlet("/login")
public class AuthenticationServlet extends HttpServlet{
    private boolean isError = false;

    private BaseHandler handler = null;

    @Override
    public void init(){
        handler = new BaseHandler();
    }

    @Override
    public void doGet(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException, ServletException {
        httpResponse.sendRedirect("authentication.html");
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
        String name = null;
        String password = null;
        try {
            joUser = (JSONObject) parser.parse(sb.toString());
            name = joUser.get("username").toString();
            password = joUser.get("password").toString();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (NullPointerException ne) {
            ne.printStackTrace();
        }

        UserDao userDao = new UserDaoImpl();
        User user = null;
        try {
            user = userDao.getUser(name, password);
            if (name == null || password == null
                    || !user.getUsername().equals(name)
                    || !user.getPassword().equals(password)){
                isError = true;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        handler.setDefaultHeader(httpResponse);
        if (!isError){
            String token = JwtUtil.generateToken(Constant.SIGNING_KEY, name);
            JSONObject object = new JSONObject();
            object.put("token", token);
            handler.responseFactory(httpResponse, object, null);
        } else {
            String error = "Неверное заполнение полей! Повторите.";
            httpResponse.setStatus(401);
            handler.responseFactory(httpResponse, null, error);
        }
    }
}
