package org.learningredis.chapter.five;

import redis.clients.jedis.Jedis;

/**
 * Created by lj1218.
 * Date: 2019/12/3
 */
public class PushLotsOfData {

    public static void main(String[] args) {
        PushLotsOfData test = new PushLotsOfData();
        test.pushData();
    }

    private void pushData() {
        Jedis jedis = new Jedis("localhost", 6379);
        for (int nv = 0; nv < 300000; ++nv) {
            jedis.sadd("MSG-0", "data-" + nv);
        }
        jedis.close();
    }
}
