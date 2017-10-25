package dao.story;

import model.Story;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AZagorskyi on 20.10.2017.
 */
public interface StoryDao {
    Story getStory();
    List<Story> getStoriesList(int start, int total);
    int getCountStories();
}
