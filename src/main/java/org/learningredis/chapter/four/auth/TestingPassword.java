package org.learningredis.chapter.four.auth;

import redis.clients.jedis.Jedis;

/**
 * Created by lj1218.
 * Date: 2019/11/29
 */
public class TestingPassword {

    private void authentication() {
        Jedis jedis = new Jedis("localhost");
        jedis.auth("Learning Redis");
        String key = "foo";
        jedis.set(key, "bar");
        System.out.println(jedis.get(key));
        jedis.close();
    }

    public static void main(String[] args) {
        new TestingPassword().authentication();
    }
}
