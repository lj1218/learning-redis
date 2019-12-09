package org.learningredis.chapter.four.pipelineandtx;

import org.learningredis.common.JedisWrapper;

/**
 * Created by lj1218.
 * Date: 2019/11/29
 */
public class MultiThreadedPipelineCommandTest {

    public static void main(String[] args) throws InterruptedException {
        flashDB();
        Thread pipelineClient = new Thread(new PipelineCommand());
        Thread singleCommandClient = new Thread(new SingleCommand());
        pipelineClient.start();
        Thread.sleep(50);
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
