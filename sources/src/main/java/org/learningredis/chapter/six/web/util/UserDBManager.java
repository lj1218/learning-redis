package org.learningredis.chapter.six.web.util;

import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lj1218.
 * Date: 2019/12/3
 * This class is responsible for user related functional calls to the data base.
 */
public class UserDBManager extends RedisDBManager {

    public static final UserDBManager singleton = new UserDBManager();

    private UserDBManager() {
        super();
    }

    public String getUserName(String sessionID) {
        Jedis jedis = getConnection();
        String name = jedis.hget(getKeyOfSessionData(sessionID), "name");
        returnConnection(jedis);
        return name;
    }

    public void createUser(Map<String, String> attributeMap) {
        Jedis jedis = getConnection();
        attributeMap.put("creation-time", new Date().toString());
        attributeMap.put("sessionID", "null");
        jedis.hmset(getKeyOfUserData(attributeMap.get("name")), attributeMap);
        returnConnection(jedis);
    }

    public void setRegistrationMap(String name, Map<String, String> attributeMap) {
        Jedis jedis = getConnection();
        jedis.hmset(getKeyOfUserData(name), attributeMap);
        returnConnection(jedis);
    }

    public Map<String, String> getRegistrationMap(String name) {
        Jedis jedis = getConnection();
        Map<String, String> attributeMap = jedis.hgetAll(getKeyOfUserData(name));
        returnConnection(jedis);
        return attributeMap;
    }

    public boolean doesUserExist(String name) {
        Jedis jedis = getConnection();
        String value = jedis.hget(getKeyOfUserData(name), "name");
        returnConnection(jedis);
        if (value == null) {
            return false;
        }
        return value.equals(name);
    }

    public String getUserPassword(String name) {
        Jedis jedis = getConnection();
        String password = jedis.hget(getKeyOfUserData(name), "password");
        returnConnection(jedis);
        return password;
    }

    public String getUserSessionId(String name) {
        Jedis jedis = getConnection();
        String sessionID = jedis.hget(getKeyOfUserData(name), "sessionID");
        returnConnection(jedis);
        return sessionID;
    }

    public void login(String sessionID, String name) {
        Jedis jedis = getConnection();
        Map<String, String> loginMap = new HashMap<>();
        loginMap.put("LastLogin", new Date().toString());
        loginMap.put("loginStatus", "LoggedIn");
        loginMap.put("sessionID", sessionID);
        loginMap.put("name", name);
        jedis.hmset(getKeyOfSessionData(sessionID), loginMap);
        returnConnection(jedis);
    }

    public boolean editRegistrationMap(Map<String, String> editMap) {
        Jedis jedis = getConnection();
        String key = getKeyOfUserData(editMap.get("name"));
        if (jedis.hget(key, "sessionID")
                .equals(editMap.get("sessionid"))) {
            jedis.hmset(key, editMap);
            returnConnection(jedis);
            return true;
        }
        returnConnection(jedis);
        return false;
    }

    public boolean doesSessionExist(String sessionID) {
        Jedis jedis = getConnection();
        if (jedis.hexists(getKeyOfSessionData(sessionID), "name")) {
            returnConnection(jedis);
            return true;
        }
        returnConnection(jedis);
        return false;
    }

    public boolean expireSession(String sessionID) {
        // Get name from session data structure
        Jedis jedis = getConnection();
        String name = jedis.hget(getKeyOfSessionData(sessionID), "name");
        // remove session id from userdata
        if (name != null) {
            long sessionValue = Long.parseLong(jedis.hget(getKeyOfUserData(name), "sessionID"));
            jedis.hset(getKeyOfUserData(name), "sessionID", "null");
            // remove session data : use TTL
            if (jedis.exists(getKeyOfSessionData(sessionID))) {
                jedis.expire(getKeyOfSessionData(sessionID), 1);
            }
            // remove browsing history : use TTL
            if (jedis.exists(sessionID + "@browsinghistory")) {
                jedis.expire(sessionID + "@browsinghistory", 1);
            }
            // remove shopping cart : use TTL
            if (jedis.exists(sessionID + "@shoppingcart")) {
                jedis.expire(sessionID + "@shoppingcart", 1);
            }
            // make the value at offset as '0'
            jedis.setbit("sessionIdTracker", sessionValue, false);
            returnConnection(jedis);
            return true;
        }
        returnConnection(jedis);
        return false;
    }

    private String getKeyOfSessionData(String sessionID) {
        return sessionID + "@sessiondata";
    }

    private String getKeyOfUserData(String name) {
        return name + "@userdata";
    }

}
