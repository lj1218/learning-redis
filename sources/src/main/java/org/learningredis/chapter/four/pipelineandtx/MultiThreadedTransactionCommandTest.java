package org.learningredis.chapter.four.pipelineandtx;

import org.learningredis.common.JedisWrapper;

/**
 * Created by lj1218.
 * Date: 2019/11/29
 */
public class MultiThreadedTransactionCommandTest {

    public static void main(String[] args) throws InterruptedException {
        flashDB();
        Thread transactionClient = new Thread(new TransactionCommand());
        Thread singleCommandClient = new Thread(new SingleCommand());
        transactionClient.start();
        Thread.sleep(30); // The return value of nv1 is [ 0 ]
//        Thread.sleep(5000); // The return value of nv1 is [ 300000 ]
        // The return value of nv1 不会出现介于 0~300000 之间的值
        singleCommandClient.start();
    }

    private static void flashDB() {
        // Fire FLUSHDB command every time you run the test, otherwise you
        // end up seeing the value of the previous test run, that is 300,000
        JedisWrapper jedis = new JedisWrapper();
//        jedis.flushDB(); // flushDB will flush all data in Redis
        jedis.del("keys-1");
        jedis.destroy();
    }
}
