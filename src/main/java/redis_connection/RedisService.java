package redis_connection;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

/**
 * Created by AZagorskyi on 08.08.2017.
 */
public class RedisService {
    private Jedis jedis;
    public RedisService(){
        try{
            jedis = new Jedis("127.0.0.1", 6379);
        } catch (JedisException je){
            jedis = null;
            je.getStackTrace();
        }
    }

    public void putFieldToRedis(String username, String field, String value){
        if (jedis != null){
            jedis.hset(username, field, value);
            jedis.expire(username, 1000);
        }
    }

    public String getFieldWithRedis(String username, String field){
        if (jedis == null){
            return null;
        }
        return jedis.hget(username, field);
    }
}
