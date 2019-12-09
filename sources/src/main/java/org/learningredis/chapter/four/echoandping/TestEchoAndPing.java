package org.learningredis.chapter.four.echoandping;

import redis.clients.jedis.Jedis;

/**
 * Created by lj1218.
 * Date: 2019/11/29
 */
public class TestEchoAndPing {

    public static void main(String[] args) throws InterruptedException {
        TestEchoAndPing echoAndPing = new TestEchoAndPing();
//        Thread thread = new Thread(new LoadGenerator(0));
        Thread thread = new Thread(new LoadGenerator(50));
//        Thread thread = new Thread(new LoadGenerator(200));
        thread.start();
        while (true) {
            Thread.sleep(1000);
            echoAndPing.testPing();
            echoAndPing.testEcho();
        }
    }

    private void testPing() {
        long start = System.currentTimeMillis();
        Jedis jedis = new Jedis("localhost");
        System.out.println(jedis.ping() + " in "
                + (System.currentTimeMillis() - start) + " milliseconds");
        jedis.close();
    }

    private void testEcho() {
        long start = System.currentTimeMillis();
        Jedis jedis = new Jedis("localhost");
        System.out.println(jedis.echo("hi Redis") + " in "
                + (System.currentTimeMillis() - start) + " milliseconds");
        jedis.close();
    }
}
