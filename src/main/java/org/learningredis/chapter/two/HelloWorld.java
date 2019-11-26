package org.learningredis.chapter.two;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by lj1218.
 * Date: 2019/11/26
 */
public class HelloWorld extends JedisPool {

    public HelloWorld() {
        super(new JedisPoolConfig(), "localhost");
    }

    private void test() {
        try {
            Jedis jedis = getResource();
            jedis.set("MSG", "Hello World");
            String result = jedis.get("MSG");
            System.out.println(" MSG : " + result);
            returnResource(jedis);
        } catch (Exception e) {
            System.err.println(e.toString());
        } finally {
            destroy();
        }
    }

    public static void main(String[] args) {
        HelloWorld helloWorld = new HelloWorld();
        helloWorld.test();
    }

}
