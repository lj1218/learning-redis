package org.learningredis.chapter.four.selectdb;

import redis.clients.jedis.Jedis;

/**
 * Created by lj1218.
 * Date: 2019/11/29
 */
public class TestSelectingDB {

    private void commandSelect() {
        Jedis jedis = new Jedis("localhost");
        String key = "msg";

        jedis.select(1);
        jedis.set(key, "Hello world");
        System.out.println(jedis.get(key));

        jedis.select(2);
        System.out.println(jedis.get(key));
    }

    public static void main(String[] args) {
        new TestSelectingDB().commandSelect();
    }
}
