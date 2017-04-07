package servlet;

import mysql_connection.DataBaseConnection;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/fishingStory")
public class FishingStoryServlet extends HttpServlet {
    public FishingStoryServlet(){}
    Logger logger = Logger.getLogger(FishingStoryServlet.class.getName());

    @Override
    public void doGet(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException, IOException{
        JSONObject jsonObject = new JSONObject();
        String base_id = httpRequest.getParameter("id");
        logger.log(Level.INFO, base_id);
        String sqlQuery = "SELECT * FROM fishing_story where id_fishing_story=?";
        try{
            PreparedStatement statement = DataBaseConnection.getConnection().prepareStatement(sqlQuery);
            statement.setString(1,base_id);
            logger.log(Level.INFO, "statement");
            ResultSet result = statement.executeQuery();
            logger.log(Level.INFO, result.toString());
            while (result.next()){
                int id = Integer.parseInt(result.getString("id_fishing_story"));
                String name = result.getString("fishing_story_name");
                String history = result.getString("fishing_story");

                jsonObject.put("id", id);
                jsonObject.put("name", name);
                jsonObject.put("history", history);
            }
            result.close();
            statement.close();
        } catch (SQLException sql){
            sql.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("application/json");
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.getWriter().write(jsonObject.toJSONString());
    }

    @Override
    public void doPost(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException,IOException{
        doGet(httpRequest, httpResponse);
    }
}