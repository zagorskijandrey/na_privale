package dao.story;

import model.Story;
import mysql_connection.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AZagorskyi on 14.04.2017.
 */
public class StoryDaoImpl implements StoryDao {
    private String sqlQuery = null;
    private String story_id = null;
    private int countStories = -1;

    public StoryDaoImpl(String sqlQuery) {
        this.sqlQuery = sqlQuery;
    }

    public StoryDaoImpl(String sqlQuery, String story_id) {
        this.sqlQuery = sqlQuery;
        this.story_id = story_id;
    }

    public Story getStory() {
        Story story = null;
        try {
            Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, story_id);
            ResultSet result = statement.executeQuery();
            story = new Story();
            while (result.next()) {
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

    public List<Story> getStoriesList(int start, int total) {
        List<Story> storiesList = null;
        Connection connection = null;
        try {
            connection = DataBaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, start);
            preparedStatement.setInt(2, total);
            ResultSet result = preparedStatement.executeQuery();
            storiesList = new ArrayList<Story>();
            while (result.next()) {
                Story story = new Story();
                setStory(story, result);
                storiesList.add(story);
            }
            Statement statement = connection.createStatement();
            ResultSet resultCount = statement.executeQuery("SELECT FOUND_ROWS()");
            if (resultCount.next()) {
                this.countStories = resultCount.getInt(1);
            }
            result.close();
            resultCount.close();
            preparedStatement.close();
            statement.close();
            DataBaseConnection.disconnect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return storiesList;
    }

    private void setStory(Story story, ResultSet result) {
        try {
            story.setId(Integer.parseInt(result.getString("id_story")));
            story.setName(result.getString("name"));
            story.setText(result.getString("story"));
            story.setAuthor(result.getString("author"));
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public int getCountStories() {
        return this.countStories;
    }
}