package org.learningredis.chapter.eight;

import redis.clients.jedis.Jedis;

/**
 * Created by lj1218.
 * Date: 2019/12/9
 */
public class TestSentinel {

    public static void main(String[] args) {
        new TestSentinel().evaluate();
    }

    private void evaluate() {
        System.out.println("-- start the test -----------");
        writeToMaster("a", "apple");
        readFromMaster("a");
        writeToSlave("b", "ball");
        stopMaster();

        sentinelKicks();
        try {
            readFromMaster("a");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        readFromSlave("a");
        sentinelKicks();
        sentinelKicks();
        writeToSlave("b", "ball");
        readFromSlave("b");
        System.out.println("-- end of test -----------");
    }

    private void sentinelKicks() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void writeToMaster(String key, String value) {
        Jedis jedis = ConnectionUtil.getJedisConnection("localhost", 6381);
        System.out.println(jedis.set(key, value));
        jedis.close();
    }

    private void readFromMaster(String key) {
        Jedis jedis = ConnectionUtil.getJedisConnection("localhost", 6381);
        String value = jedis.get(key);
        jedis.close();
        System.out.println("reading value of '" + key + "' from master is :" + value);
    }

    private void stopMaster() {
        Jedis jedis = ConnectionUtil.getJedisConnection("localhost", 6381);
        jedis.shutdown();
        jedis.close();
    }

    private void writeToSlave(String key, String value) {
        Jedis jedis = ConnectionUtil.getJedisConnection("localhost", 6382);
        try {
            System.out.println(jedis.set(key, value));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        jedis.close();
    }

    private void readFromSlave(String key) {
        Jedis jedis = ConnectionUtil.getJedisConnection("localhost", 6382);
        String value = jedis.get(key);
        jedis.close();
        System.out.println("reading value of '" + key + "' from slave is :" + value);
    }

    private static class ConnectionUtil {
        static Jedis getJedisConnection(String host, int port) {
            return new Jedis(host, port);
        }
    }
}
// Redis version: 5.0.7
/* configurations:
// Master
$ cat redis.conf
bind 127.0.0.1
port 6381
loglevel verbose

// Slave
$ cat redis.conf
slaveof 127.0.0.1 6381
bind 127.0.0.1
port 6382
loglevel verbose

// Sentinel
$ cat sentinel.conf.bak
protected-mode yes
sentinel deny-scripts-reconfig yes
sentinel monitor slave2master 127.0.0.1 6382 1
sentinel down-after-milliseconds slave2master 10000
sentinel failover-timeout slave2master 900000
sentinel parallel-syncs slave2master 1
 */

/* output:
-- start the test -----------
OK
reading value of 'a' from master is :apple
READONLY You can't write against a read only replica.
Failed connecting to host localhost:6381
reading value of 'a' from slave is :apple
OK
reading value of 'b' from slave is :ball
-- end of test -----------
 */