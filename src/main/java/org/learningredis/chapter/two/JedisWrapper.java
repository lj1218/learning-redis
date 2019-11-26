package org.learningredis.chapter.two;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by lj1218.
 * Date: 2019/11/26
 */
public class JedisWrapper extends JedisPool {

    public JedisWrapper() {
        super(new JedisPoolConfig(), "localhost");
    }

    public void set(String key, String value) {
        Jedis jedis = getResource();
        jedis.set(key, value);
        returnResource(jedis);
    }

    public String get(String key) {
        Jedis jedis = getResource();
        String result = jedis.get(key);
        returnResource(jedis);
        return result;
    }

    public void destroy() {
        super.destroy();
    }

}
