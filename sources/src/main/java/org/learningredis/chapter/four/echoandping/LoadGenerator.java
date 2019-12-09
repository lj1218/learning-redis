package org.learningredis.chapter.four.echoandping;

import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lj1218.
 * Date: 2019/11/29
 */
public class LoadGenerator implements Runnable {

    private List<Thread> clients = new ArrayList<>();

    public LoadGenerator(int threadNum) {
        for (int i = 0; i < threadNum; i++) {
            clients.add(new Thread(new Sample()));
        }
    }

    @Override
    public void run() {
        for (Thread thread : clients) {
            thread.start();
        }
    }

    class Sample implements Runnable {

        @Override
        public void run() {
            Jedis jedis = new Jedis("localhost");
            int i = 0;
            while (!Thread.currentThread().isInterrupted()) {
                jedis.sadd(Thread.currentThread().getName(), "Some text " + i);
                ++i;
            }
            jedis.close();
        }
    }
}
