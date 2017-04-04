import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by AZagorskyi on 22.02.2017.
 */
@WebServlet(urlPatterns = {"/helloServlet"})
public class AppServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String url = "d:/file.json";
        FileReader file = new FileReader(url);
        JSONParser parser = new JSONParser();
        try {
            JSONObject object = (JSONObject) parser.parse(file);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(object.toJSONString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
