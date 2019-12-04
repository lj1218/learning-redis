package org.learningredis.chapter.six.web.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Protocol;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lj1218.
 * Date: 2019/12/3
 * This class is the backbone of this application; it is responsible for connecting with the
 * database and managing the connection pool. It also has some utility functions.
 */
public class RedisDBManager extends JedisPool {
    private static Date date = new Date();
    private static int minimum = 1;
    private static int maximum = 100000000;
    // going with the default pool.
//    private static JedisPool connectionPool = new JedisPool(Protocol.DEFAULT_HOST, Protocol.DEFAULT_PORT);

    public RedisDBManager() {
        super(Protocol.DEFAULT_HOST, Protocol.DEFAULT_PORT);
    }

    public Jedis getConnection() {
        return getResource();
    }

    public void returnConnection(Jedis jedis) {
        returnResource(jedis);
    }

    public static String getDate() {
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }

    public static String getRandomSessionID() {
        return Integer.toString(minimum + (int) (Math.random() * maximum));
    }

    String getKeyOfUserData(String name) {
        return name + "@userdata";
    }

    String getKeyOfShoppingCart(String sessionID) {
        return sessionID + "@shoppingcart";
    }

    String getKeyOfSessionData(String sessionID) {
        return sessionID + "@sessiondata";
    }

    String getKeyOfPurchaseHistory(String username) {
        return username + "@purchasehistory";
    }

    String getKeyOfVisit(String productName) {
        return productName + "@visit:" + getDate();
    }

    String getKeyOfPurchase(String productName) {
        return productName + "@purchase:" + getDate();
    }

    String getKeyOfBrowsingHistory(String sessionID) {
        return sessionID + "@browsinghistory";
    }

    String getKeyOfSessionIdTracker() {
        return "sessionIdTracker";
    }
}
