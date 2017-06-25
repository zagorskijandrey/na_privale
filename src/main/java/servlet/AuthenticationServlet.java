package servlet;

import authentication.CookieUtil;
import authentication.JwtUtil;
import dao.UserDao;
import dao.UserDaoImpl;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by andrey on 25.06.2017.
 */
@WebServlet("/authentication")
public class AuthenticationServlet extends HttpServlet{
    private static final String jwtTokenCookieName = "JWT-TOKEN";
    private static final String signingKey = "signingKey";

    @Override
    public void doGet(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException, ServletException {
        httpResponse.sendRedirect("authentication.html");
    }

    @Override
    public void doPost(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException, ServletException {
        String name = httpRequest.getParameter("username");
        String password = httpRequest.getParameter("password");
        UserDao userDao = new UserDaoImpl();
        User user = null;
        try {
            user = userDao.getUser(name, password);

        } catch (ClassNotFoundException e) {
            //e.printStackTrace();
        } catch (SQLException e) {
            //e.printStackTrace();
        }

        if (name == null || password == null
                || !user.getName().equals(name)
                || !user.getPassword().equals(password)){
            httpResponse.getWriter().write("error");
        }

        String token = JwtUtil.generateToken(signingKey, name);
        CookieUtil.create(httpResponse, jwtTokenCookieName, token, false, -1, "localhost");
    }
}
