package servlet;

import authentication.CookieUtil;
import authentication.JwtUtil;
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
    private static final String jwtTokenCookieName = "JWT-TOKEN";
    private static final String signingKey = "signingKey";
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
        try {
            joUser = (JSONObject) parser.parse(sb.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String name = joUser.get("username").toString();
        String password = joUser.get("password").toString();


//        String name = httpRequest.getParameter("username");
//        String password = httpRequest.getParameter("password");
        UserDao userDao = new UserDaoImpl();
        User user = null;
        try {
            user = userDao.getUser(name, password);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (name == null || password == null
                || !user.getUsername().equals(name)
                || !user.getPassword().equals(password)){
            isError = true;
//            httpResponse.getWriter().write("error");
        }

        String token = JwtUtil.generateToken(signingKey, name);
        CookieUtil.create(httpResponse, jwtTokenCookieName, token, false, -1, "localhost");

        handler.setDefaultHeader(httpResponse);
        if (!isError){
            JSONObject object = new JSONObject();
            object.put("token", token);
            handler.responseFactory(httpResponse, object, null);
        } else {
            String error = "Неверное заполнение полей!";
            httpResponse.setStatus(400);
            handler.responseFactory(httpResponse, null, error);
        }
    }
}
