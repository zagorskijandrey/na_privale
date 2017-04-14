package archiv.get;

import constant.Constant;
import model.Story;
import mysql_connection.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by AZagorskyi on 14.04.2017.
 */
public class GetStoryData {
    private String sqlQuery = null;

    public GetStoryData(String sqlQuery){
        this.sqlQuery = sqlQuery;
    }

    public Story getStory(){
        Story story = null;
        try{
            Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            ResultSet result = statement.executeQuery();
            story = new Story();
            while (result.next()){
                story.setId(Integer.parseInt(result.getString("id")));
                story.setName(result.getString("story_name"));
                story.setText(result.getString("story"));
                story.setImageName(result.getString("image_name"));
            }
            result.close();
            statement.close();
            connection.close();

        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return story;
    }
}
