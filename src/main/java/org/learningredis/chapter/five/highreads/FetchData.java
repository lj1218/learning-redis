package org.learningredis.chapter.five.highreads;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by lj1218.
 * Date: 2019/12/2
 * - Responsible for fetching the data from the Redis nodes
 * - This class is called in a multi-threaded mode
 * - This class is passed at start time so the last result returned will
 * indicate the total time the execution has taken place
 */
public class FetchData implements Runnable {
    private int endNumber;
    private int startNumber;
    private long startTime;
    private JedisPool jedisPool;

    public FetchData(int number, long startTime, String localhost, int port) {
        endNumber = number * 100000;
        startNumber = endNumber - 100000;
        this.startTime = startTime;
        jedisPool = new JedisPool(localhost, port);
    }

    @Override
    public void run() {
        Jedis jedis = jedisPool.getResource();
        for (int index = startNumber; index < endNumber; ++index) {
            System.out.println("printing values for index = message" + index + " = "
                    + jedis.get("message-" + index));
            long endTime = System.currentTimeMillis();
            System.out.println("TOTAL TIME " + (endTime - startTime));
        }
    }
}
