package dao.user;

import model.Hamlet;
import model.User;

import java.sql.SQLException;
import java.util.Map;

/**
 * Created by andrey on 25.06.2017.
 */
public interface UserDao {
    User getUser(String name, String password) throws ClassNotFoundException, SQLException;
    boolean saveUser(User user);
    User getUserByEmail(String email) throws ClassNotFoundException, SQLException;
    Map<Hamlet, Integer> getPastFishingLocationByUser(String name);
}
