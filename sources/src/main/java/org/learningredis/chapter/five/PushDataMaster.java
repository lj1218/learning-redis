package org.learningredis.chapter.five;

import org.learningredis.common.JedisWrapper;
import redis.clients.jedis.Pipeline;

/**
 * Created by lj1218.
 * Date: 2019/12/2
 */
public class PushDataMaster {

    private void pushData() {
        JedisWrapper jedis = new JedisWrapper();
        String key = "MSG";
        jedis.del(key);
        Pipeline pipeline = jedis.pipelined();
        for (int nv = 0; nv < 900000; ++nv) {
            pipeline.sadd(key, "data-" + nv);
        }
        pipeline.sync();
        jedis.destroy();
    }

    public static void main(String[] args) {
        new PushDataMaster().pushData();
    }
}
