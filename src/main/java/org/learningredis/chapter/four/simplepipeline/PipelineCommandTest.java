package org.learningredis.chapter.four.simplepipeline;

import org.learningredis.common.JedisWrapper;
import redis.clients.jedis.Pipeline;

import java.util.List;

/**
 * Created by lj1218.
 * Date: 2019/11/28
 */
public class PipelineCommandTest {

    private static final int KEY_NUM = 10;
    private static final int VAL_NUM_IN_EACH_KEY = 1000;

    private long startTimeWithoutPipeline = 0;
    private long startTimeWithPipeline = 0;
    private long endTimeWithoutPipeline = 0;
    private long endTimeWithPipeline = 0;
    private JedisWrapper jedis = new JedisWrapper();

    private void getStats() {
        System.out.println(" time taken for test without pipeline "
                + (endTimeWithoutPipeline - startTimeWithoutPipeline) + "ms");
        System.out.println(" time taken for test with    pipeline "
                + (endTimeWithPipeline - startTimeWithPipeline) + "ms");
    }

    private void checkWithoutPipeline() {
        clear();
        startTimeWithoutPipeline = System.currentTimeMillis();
        for (int keys = 0; keys < KEY_NUM; ++keys) {
            for (int nv = 0; nv < VAL_NUM_IN_EACH_KEY; ++nv) {
                jedis.hset("keys-" + keys, "name" + nv, "value" + nv);
            }
            for (int nv = 0; nv < VAL_NUM_IN_EACH_KEY; ++nv) {
                jedis.hget("keys-" + keys, "name" + nv);
            }
        }
        endTimeWithoutPipeline = System.currentTimeMillis();
    }

    private void checkWithPipeline() {
        clear();
        startTimeWithPipeline = System.currentTimeMillis();
        for (int keys = 0; keys < KEY_NUM; ++keys) {
            Pipeline pipeline = jedis.pipelined();
            for (int nv = 0; nv < VAL_NUM_IN_EACH_KEY; ++nv) {
                pipeline.hset("keys-" + keys, "name" + nv, "value" + nv);
            }
            List<Object> results = pipeline.syncAndReturnAll();
            for (Object result : results) {
            }
        }
        endTimeWithPipeline = System.currentTimeMillis();
        jedis.flushDB();
    }

    private void clear() {
        Pipeline pipeline = jedis.pipelined();
        for (int keys = 0; keys < KEY_NUM; ++keys) {
            pipeline.del("keys-" + keys);
        }
        pipeline.syncAndReturnAll();
        jedis.flushDB();
    }

    public void destroy() {
        clear();
        jedis.destroy();
    }

    public static void main(String[] args) throws InterruptedException {
        PipelineCommandTest test = new PipelineCommandTest();
        test.checkWithoutPipeline();
        Thread.sleep(1000);
        test.checkWithPipeline();
        Thread.sleep(1000);
        test.getStats();
        test.destroy();
    }
}
