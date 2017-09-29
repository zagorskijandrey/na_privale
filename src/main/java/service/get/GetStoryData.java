package service.get;

import constant.Constant;
import model.Story;
import mysql_connection.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by AZagorskyi on 14.04.2017.
 */
public class GetStoryData {
    private String sqlQuery = null;
    private String story_id = null;

    public GetStoryData(String sqlQuery){
        this.sqlQuery = sqlQuery;
    }

    public GetStoryData(String sqlQuery, String story_id){
        this.sqlQuery = sqlQuery;
        this.story_id = story_id;
    }

    public Story getStory(){
        Story story = null;
        try{
            Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, story_id);
            ResultSet result = statement.executeQuery();
            story = new Story();
            while (result.next()){
                setStory(story, result);
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

    public ArrayList<Story> getStoriesList(int start,int total){
        ArrayList<Story> storiesList = null;
        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, start);
            statement.setInt(2, total);
            ResultSet result = statement.executeQuery();
            storiesList = new ArrayList<Story>();
            while (result.next()){
                Story story = new Story();
                setStory(story, result);
                storiesList.add(story);
            }
            result.close();
            statement.close();
            DataBaseConnection.disconnect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return storiesList;
    }

    private void setStory(Story story, ResultSet result){
        try{
            story.setId(Integer.parseInt(result.getString("id_story")));
            story.setName(result.getString("name"));
            story.setText(result.getString("story"));
            story.setAuthor(result.getString("author"));
//            if (this.sqlQuery.equals(Constant.SQL_QUERY_GET_FISHING_STORY_BY_ID) || this.sqlQuery.equals(Constant.SQL_QUERY_GET_FISHING_STORIES)) {
//                story.setId(Integer.parseInt(result.getString("id_fishing_story")));
//                story.setName(result.getString("fishing_story_name"));
//                story.setText(result.getString("fishing_story"));
//
//            } else if (this.sqlQuery.equals(Constant.SQL_QUERY_GET_HUNTER_STORY_BY_ID) || this.sqlQuery.equals(Constant.SQL_QUERY_GET_HUNTER_STORIES)) {
//                story.setId(Integer.parseInt(result.getString("id_fish_hunter_story")));
//                story.setName(result.getString("fish_hunter_story_name"));
//                story.setText(result.getString("fish_hunter_story"));
//
//            }
        } catch (SQLException e){
            e.getMessage();
        }
    }
}