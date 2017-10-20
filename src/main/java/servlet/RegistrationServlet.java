/**
 * Created by AZagorskyi on 07.09.2017.
 */
package servlet;

import dao.user.UserDao;
import dao.user.UserDaoImpl;
import json_parser.JSONToObjectParserForUser;
import model.User;
import org.json.simple.JSONObject;
import redis_connection.RedisService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    private BaseHandler handler = null;

    @Override
    public void init(){
        handler = new BaseHandler();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        String error = null;
        boolean isSaveUser = false;

        JSONToObjectParserForUser jsonToObjectParserForUser = new JSONToObjectParserForUser();
        User user = jsonToObjectParserForUser.getUserFromJSON(request);
        handler.setDefaultHeader(response);

        RedisService redisService = new RedisService();
        if (redisService.getFieldWithRedis(user.getUsername(), "username") != null){
            error = "Пользователь с таким логином уже существует!";
        }
        UserDao userDao = new UserDaoImpl();
        try {
            User userWithDB = userDao.getUser(user.getUsername(), user.getPassword());
            if(userWithDB != null && userWithDB.getUsername().equals(user.getUsername())){
                error = "Пользователь с таким логином уже существует!";
            } else {
                isSaveUser = userDao.saveUser(user);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (isSaveUser != false){
            JSONObject object = new JSONObject();
            object.put("registration", new JSONObject().put("username", user.getUsername()));
            handler.responseFactory(response, object, null);
        } else {
            response.setStatus(401);
            handler.responseFactory(response, null, error);
        }
    }

    @Override
    public void doPost(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException,IOException{
        doGet(httpRequest, httpResponse);
    }
}
