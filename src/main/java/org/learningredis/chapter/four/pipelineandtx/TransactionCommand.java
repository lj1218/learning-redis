package org.learningredis.chapter.four.pipelineandtx;

import org.learningredis.common.JedisWrapper;
import redis.clients.jedis.Transaction;

import java.util.Set;

/**
 * Created by lj1218.
 * Date: 2019/11/29
 */
public class TransactionCommand implements Runnable {

    private JedisWrapper jedis = new JedisWrapper();

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        Transaction transactionableCommands = jedis.multi();
        for (int nv = 0; nv < 300000; ++nv) {
            transactionableCommands.sadd("keys-1", "name" + nv);
        }
        transactionableCommands.exec();
        Set<String> data = jedis.smembers("keys-1");
        System.out.println("The return value of nv1 after tx [ "
                + data.size() + " ]");
        System.out.println("The time taken for executing client("
                + Thread.currentThread().getName() + ") "
                + (System.currentTimeMillis() - start));
        jedis.destroy();
    }
}
