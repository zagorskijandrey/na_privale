package dao;

import model.User;

import java.sql.SQLException;

/**
 * Created by andrey on 25.06.2017.
 */
public interface UserDao {
    User getUser(String name, String password) throws ClassNotFoundException, SQLException;
    boolean saveUser(User user);
    User getUserByEmail(String email) throws ClassNotFoundException, SQLException;
}
