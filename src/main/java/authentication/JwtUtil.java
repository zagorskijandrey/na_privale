package authentication;

import constant.Constant;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import redis_connection.RedisService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by andrey on 23.04.2017.
 */
public class JwtUtil {

    public static String generateToken(String signingKey, String subject) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        JwtBuilder builder = Jwts.builder().setSubject(subject).setIssuedAt(now).signWith(SignatureAlgorithm.HS256, signingKey);
        String token = builder.compact();
        RedisService redisService = new RedisService();
        redisService.putFieldToRedis(subject, "username", subject);
        return token;
    }

    public static String getSubject(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("Authorization").substring(Constant.TOKEN_PREFIX.length());
        if (token == null)
            return null;

        String subject = Jwts.parser().setSigningKey(Constant.SIGNING_KEY).parseClaimsJws(token).getBody().getSubject();
        RedisService redisService = new RedisService();
        if (redisService.getFieldWithRedis(subject, "username") == null){
            return null;
        }
        return subject;
    }
}
