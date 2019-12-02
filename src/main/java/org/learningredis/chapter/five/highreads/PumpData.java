package org.learningredis.chapter.five.highreads;

import redis.clients.jedis.Jedis;

/**
 * Created by lj1218.
 * Date: 2019/12/2
 * - Responsible for pushing the data into the main node
 * - The data pushing is single threaded
 */
public class PumpData implements Runnable {

    @Override
    public void run() {
        Jedis jedis = new Jedis("localhost", 6379);
        for (int index = 1; index < 1000000; ++index) {
            jedis.append("message-" + index, "my dumb value " + index);
        }
    }
}
