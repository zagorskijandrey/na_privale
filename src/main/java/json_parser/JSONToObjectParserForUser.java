package json_parser;

import model.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by AZagorskyi on 11.09.2017.
 */
public class JSONToObjectParserForUser {

    public User getUserFromJSON(HttpServletRequest httpRequest) throws ServletException, IOException {
        StringBuilder stringBuffer = new StringBuilder();

        BufferedReader reader = httpRequest.getReader();
        while(reader.ready()){
            stringBuffer.append(reader.readLine());
        }
        JSONParser parser = new JSONParser();
        JSONObject joUser = null;
        User user = null;
        try {
            joUser = (JSONObject) parser.parse(stringBuffer.toString());
            user = new User();

            if (joUser.get("username") != null)
                user.setUsername(joUser.get("username").toString());
            if (joUser.get("password") != null)
                user.setPassword(joUser.get("password").toString());
            if (joUser.get("email") != null)
                user.setEmail(joUser.get("email").toString());
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (NullPointerException ne) {
            ne.printStackTrace();
        }
        return user;
    }
}
