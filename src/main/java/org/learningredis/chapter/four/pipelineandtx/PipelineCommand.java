package org.learningredis.chapter.four.pipelineandtx;

import org.learningredis.common.JedisWrapper;
import redis.clients.jedis.Pipeline;

import java.util.Set;

/**
 * Created by lj1218.
 * Date: 2019/11/29
 */
public class PipelineCommand implements Runnable {

    private JedisWrapper jedis = new JedisWrapper();

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        Pipeline commandPipe = jedis.pipelined();
        for (int nv = 0; nv < 300000; ++nv) {
            commandPipe.sadd("keys-1", "name" + nv);
        }
        commandPipe.sync();
        Set<String> data = jedis.smembers("keys-1");
        System.out.println("The return value of nv1 after pipeline [ "
                + data.size() + " ]");
        System.out.println("The time taken for executing client("
                + Thread.currentThread().getName() + ") "
                + (System.currentTimeMillis() - start));
        jedis.destroy();
    }
}
