package org.learningredis.ch07.gossipserver.util;

import redis.clients.jedis.Jedis;

/**
 * Created by lj1218.
 * Date: 2019/12/5
 */
public class ConnectionManager {

    public static Jedis get() {
        return new Jedis();
    }

}
