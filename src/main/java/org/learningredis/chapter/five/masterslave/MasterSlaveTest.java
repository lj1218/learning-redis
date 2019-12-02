package org.learningredis.chapter.five.masterslave;

import redis.clients.jedis.Jedis;

/**
 * Created by lj1218.
 * Date: 2019/12/2
 */
public class MasterSlaveTest {

    private void masterSlave() throws InterruptedException {
        Jedis master = new Jedis("localhost", 6379);
        Jedis slave = new Jedis("localhost", 6380);
        String key = "msg";
        master.append(key, "Learning Redis");
        System.out.println("Getting message from master: " + master.get(key));
        System.out.println("Getting message from slave : " + slave.get(key));

        master.shutdown();
        slave.slaveofNoOne();
        slave.append(key, " slave becomes the master");
        System.out.println("Getting message from slave turned master : " + slave.get(key));
        Thread.sleep(20000);
        master = new Jedis("localhost", 6379);

        // 使用 localhost master 报错：
        // # Error condition on socket for SYNC: Connection refused
        // * Connecting to MASTER localhost:6380
//        master.slaveof("localhost", 6380);
        master.slaveof("127.0.0.1", 6380);

        Thread.sleep(20000);
        System.out.println("Getting message from master turned slave : " + master.get(key));
        master.append(key, " throw some exceptions!!");
    }

    public static void main(String[] args) throws InterruptedException {
        new MasterSlaveTest().masterSlave();
    }
}
